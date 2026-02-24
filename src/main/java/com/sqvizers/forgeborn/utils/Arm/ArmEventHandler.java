package com.sqvizers.forgeborn.utils.Arm;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "forgeborn", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmEventHandler {

    /*
     * @SubscribeEvent
     * public static void onJump(LivingEvent.LivingJumpEvent event) {
     * if (event.getEntity() instanceof Player player) {
     * ItemStack arm = ArmUtils.getEquippedArm(player);
     * 
     * if (!arm.isEmpty() && player.getCooldowns().isOnCooldown(arm.getItem())) {
     * 
     * AABB checkArea = player.getBoundingBox().inflate(0.6, -0.2, 0.6);
     * boolean nearWall = player.level().getBlockStates(checkArea).anyMatch(state -> !state.isAir());
     * 
     * if (nearWall) {
     * Vec3 look = player.getLookAngle();
     * player.setDeltaMovement(look.x * 0.3, 0.65, look.z * 0.3);
     * player.hurtMarked = true;
     * 
     * player.getCooldowns().removeCooldown(arm.getItem());
     * }
     * }
     * }
     * }
     */
}
