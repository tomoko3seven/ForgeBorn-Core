package com.sqvizers.forgeborn.utils.Arm;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "forgeborn", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmEventHandler {

    /*@SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack arm = ArmUtils.getEquippedArm(player);

            if (!arm.isEmpty() && player.getCooldowns().isOnCooldown(arm.getItem())) {

                AABB checkArea = player.getBoundingBox().inflate(0.6, -0.2, 0.6);
                boolean nearWall = player.level().getBlockStates(checkArea).anyMatch(state -> !state.isAir());

                if (nearWall) {
                    Vec3 look = player.getLookAngle();
                    player.setDeltaMovement(look.x * 0.3, 0.65, look.z * 0.3);
                    player.hurtMarked = true;

                    player.getCooldowns().removeCooldown(arm.getItem());
                }
            }
        }
    }*/
}