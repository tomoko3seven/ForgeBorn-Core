package com.sqvizers.forgeborn.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sqvizers.forgeborn.common.entities.MeteorEntity;

public class MeteorRenderer extends EntityRenderer<MeteorEntity> {

    public MeteorRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(MeteorEntity entity, float yaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(5.0F, 5.0F, 5.0F);

        poseStack.mulPose(Axis.XP.rotationDegrees(entity.tickCount * 5));

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Blocks.MAGMA_BLOCK.defaultBlockState(),
                poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MeteorEntity entity) {
        return null;
    }
}
