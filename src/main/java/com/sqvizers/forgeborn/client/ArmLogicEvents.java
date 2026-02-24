package com.sqvizers.forgeborn.client;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.sqvizers.forgeborn.api.item.curio.HookArmItem;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;

public class ArmLogicEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack arm = ArmUtils.getEquippedArm(player);

        if (arm.getItem() instanceof HookArmItem) {
            if (!player.getOffhandItem().isEmpty()) {
                player.drop(player.getOffhandItem(), true);
                player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
        }
    }
}
