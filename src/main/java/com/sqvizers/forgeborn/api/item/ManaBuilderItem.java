package com.sqvizers.forgeborn.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ManaBuilderItem extends Item {

    public static final int MAX_MANA = 500_000;
    private static final int COST_PER_BLOCK = 10;
    private static final int MAX_RADIUS = 25;

    public ManaBuilderItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack wandStack, ItemStack otherStack, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY && !otherStack.isEmpty() && otherStack.getItem() instanceof BlockItem blockItem) {
            CompoundTag tag = wandStack.getOrCreateTag();
            tag.put("TargetBlock", NbtUtils.writeBlockState(blockItem.getBlock().defaultBlockState()));
            player.playSound(SoundEvents.BUNDLE_INSERT, 0.8f, 1.0f);
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                int mode = (getMode(stack) + 1) % 2;
                setMode(stack, mode);
                player.displayClientMessage(Component.literal("Mode: " + (mode == 0 ? "Square" : "Circle")).withStyle(ChatFormatting.GREEN), true);
            }
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide) {
            BlockState targetState = getTargetBlock(stack);
            if (targetState == null || targetState.isAir()) {
                player.displayClientMessage(Component.literal("No block selected! (Right-click with block in GUI)").withStyle(ChatFormatting.RED), true);
                return InteractionResult.FAIL;
            }

            Item blockItemRequired = targetState.getBlock().asItem();
            List<BlockPos> positions = getBuildPositions(context.getClickedPos(), context.getClickedFace(), getSize(stack), getMode(stack));

            int placedCount = 0;
            for (BlockPos pos : positions) {
                BlockState currentState = level.getBlockState(pos);
                if (!currentState.isAir() && !currentState.canBeReplaced()) continue;

                if (!ManaItemHandler.instance().requestManaExactForTool(stack, player, COST_PER_BLOCK, false)) break;

                if (!player.getAbilities().instabuild) {
                    if (!consumeBlockFromInventory(player, blockItemRequired)) break;
                }

                ManaItemHandler.instance().requestManaExactForTool(stack, player, COST_PER_BLOCK, true);
                level.setBlock(pos, targetState, 3);
                placedCount++;
            }

            if (placedCount > 0) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), targetState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private boolean consumeBlockFromInventory(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (!invStack.isEmpty() && invStack.is(item)) {
                invStack.shrink(1);
                return true;
            }
        }
        return false;
    }

    public static List<BlockPos> getBuildPositions(BlockPos center, Direction face, int radius, int mode) {
        List<BlockPos> list = new ArrayList<>();
        BlockPos start = center.relative(face);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos targetPos;
                if (face.getAxis() == Direction.Axis.Y) targetPos = start.offset(x, 0, z);
                else if (face.getAxis() == Direction.Axis.X) targetPos = start.offset(0, x, z);
                else targetPos = start.offset(x, z, 0);

                if (mode == 0 || (x * x + z * z <= radius * radius)) {
                    list.add(targetPos);
                }
            }
        }
        return list;
    }

    public static int getMode(ItemStack stack) { return stack.getOrCreateTag().getInt("BuildMode"); }
    public static void setMode(ItemStack stack, int mode) { stack.getOrCreateTag().putInt("BuildMode", mode); }

    public static int getSize(ItemStack stack) {
        return stack.getOrCreateTag().getInt("BuildSize");
    }
    public static void setSize(ItemStack stack, int size) {
        stack.getOrCreateTag().putInt("BuildSize", Math.max(0, Math.min(size, MAX_RADIUS)));
    }

    @Nullable
    public static BlockState getTargetBlock(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("TargetBlock")) {
            return NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), stack.getTag().getCompound("TargetBlock"));
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        BlockState state = getTargetBlock(stack);
        String blockName = state == null ? "None" : state.getBlock().getName().getString();
        tooltip.add(Component.literal("Block: " + blockName).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Mode: " + (getMode(stack) == 0 ? "Square" : "Circle")).withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.literal("Radius: " + getSize(stack)).withStyle(ChatFormatting.AQUA));
    }
}