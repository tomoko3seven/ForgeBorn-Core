package com.sqvizers.forgeborn.api.item.curio;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.item.ManaDissolvable;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.api.mana.ManaPool;

import java.util.List;

public class ManaTotemItem extends Item implements ManaDissolvable {

    public static final int MAX_MANA = 100_000;
    public static final String TAG_MANA = "mana";

    public ManaTotemItem() {
        super(new Properties().stacksTo(1).fireResistant());
    }

    @Override
    public void onDissolveTick(ManaPool pool, ItemEntity itemEntity) {
        System.out.println("Бассейн видит тотем!");

        if (!itemEntity.level().isClientSide && pool.getCurrentMana() > 0) {
            itemEntity.getItem().getCapability(BotaniaForgeCapabilities.MANA_ITEM).ifPresent(mana -> {
                if (mana.getMana() < mana.getMaxMana()) {
                    int transfer = Math.min(pool.getCurrentMana(), 1000);
                    mana.addMana(transfer);
                    pool.receiveMana(-transfer);
                }
            });
        }
    }

    public static int getManaStatic(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(TAG_MANA) : 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int mana = getManaStatic(stack);
        tooltip.add(Component.translatable("Mana: %s / %s",
                String.format("%,d", mana),
                String.format("%,d", MAX_MANA))
                .withStyle(ChatFormatting.BLUE));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * (float) getManaStatic(stack) / (float) MAX_MANA);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x00AAFF;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ManaProvider(stack);
    }

    private static class ManaProvider implements ICapabilityProvider {

        private final ItemStack stack;
        private final LazyOptional<ManaItem> handler;

        public ManaProvider(ItemStack stack) {
            this.stack = stack;
            this.handler = LazyOptional.of(() -> new ManaItemImpl(stack));
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == BotaniaForgeCapabilities.MANA_ITEM) {
                return handler.cast();
            }
            return LazyOptional.empty();
        }
    }

    private static class ManaItemImpl implements ManaItem {

        private final ItemStack stack;

        public ManaItemImpl(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int getMana() {
            return getManaStatic(stack);
        }

        @Override
        public int getMaxMana() {
            return MAX_MANA;
        }

        @Override
        public void addMana(int mana) {
            if (mana == 0) return;
            int current = getMana();
            stack.getOrCreateTag().putInt(TAG_MANA, Mth.clamp(current + mana, 0, MAX_MANA));
        }

        @Override
        public boolean canReceiveManaFromPool(BlockEntity pool) {
            return true;
        }

        @Override
        public boolean canReceiveManaFromItem(ItemStack other) {
            return true;
        }

        @Override
        public boolean canExportManaToPool(BlockEntity pool) {
            return false;
        }

        @Override
        public boolean canExportManaToItem(ItemStack other) {
            return false;
        }

        @Override
        public boolean isNoExport() {
            return true;
        }
    }
}
