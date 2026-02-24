package com.sqvizers.forgeborn.utils.Arm;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sqvizers.forgeborn.common.data.FBItems;

public class MechanicalArmLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public MechanicalArmLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
                       float headPitch) {
        ItemStack armStack = ArmUtils.getEquippedArm(player);
        if (armStack.isEmpty()) return;

        ResourceLocation texture = ArmUtils.getArmTexture(armStack);
        if (texture != null) {
            RenderType type;
            Item item = armStack.getItem();

            if (item == FBItems.SCULK_ARM.get()) {
                type = RenderType.entityCutoutNoCull(texture);
            } else if (item == FBItems.HOOK_ARM.get()) {
                type = RenderType.entityCutoutNoCull(texture);
            } else {
                type = RenderType.entityTranslucent(texture);
            }

            VertexConsumer vertexConsumer = buffer.getBuffer(type);
            this.getParentModel().leftArm.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            this.getParentModel().leftSleeve.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
