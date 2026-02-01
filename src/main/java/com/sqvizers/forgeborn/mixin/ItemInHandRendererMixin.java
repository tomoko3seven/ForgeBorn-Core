package com.sqvizers.forgeborn.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void hideLeftArmFirstPerson(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, net.minecraft.world.item.ItemStack stack, float equipProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, CallbackInfo ci) {
        if (hand == InteractionHand.OFF_HAND && !ArmUtils.hasLeftArm(player)) {
            ci.cancel();
        }
    }
}