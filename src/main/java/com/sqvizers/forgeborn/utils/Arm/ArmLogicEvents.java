package com.sqvizers.forgeborn.utils.Arm;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "forgeborn")
public class ArmLogicEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            ItemStack equippedArm = ArmUtils.getEquippedArm(player);

            if (equippedArm.isEmpty()) {
                if (!player.getOffhandItem().isEmpty()) {
                    player.drop(player.getOffhandItem(), true);
                    player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() == InteractionHand.OFF_HAND && ArmUtils.getEquippedArm(event.getEntity()).isEmpty()) {
            event.setCanceled(true);
        }
    }
}