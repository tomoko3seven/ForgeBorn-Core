package com.sqvizers.forgeborn.api.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.sqvizers.forgeborn.api.item.curio.ManaTotemItem;
import vazkii.botania.api.BotaniaForgeCapabilities;

// ЗАМЕНИ "forgeborn" на свой реальный MODID из mods.toml!
@Mod.EventBusSubscriber(modid = "forgeborn")
public class TotemEventHandler {

    private static final int COST = 50_000; // Цена спасения

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide) return;

        ItemStack totem = findTotem(player);

        if (!totem.isEmpty()) {
            boolean saved = totem.getCapability(BotaniaForgeCapabilities.MANA_ITEM).map(mana -> {
                if (mana.getMana() >= COST) {
                    mana.addMana(-COST);
                    return true;
                }
                return false;
            }).orElse(false);

            if (saved) {
                event.setCanceled(true);

                if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                    player.level().broadcastEntityEvent(player, (byte) 35);
                }

                // Восстановление здоровья
                player.setHealth(2.0F);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    private static ItemStack findTotem(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaTotemItem)
            return player.getItemInHand(InteractionHand.MAIN_HAND);
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaTotemItem)
            return player.getItemInHand(InteractionHand.OFF_HAND);
        return ItemStack.EMPTY;
    }
}
