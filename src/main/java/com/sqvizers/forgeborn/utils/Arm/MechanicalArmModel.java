package com.sqvizers.forgeborn.utils.Arm;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class MechanicalArmModel extends EntityModel<AbstractClientPlayer> {

    private final ModelPart arm;

    public MechanicalArmModel(ModelPart root) {
        this.arm = root.getChild("main_arm");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(AbstractClientPlayer entity, float f1, float f2, float f3, float f4, float f5) {
        this.arm.xRot = f4;
        this.arm.yRot = f5;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float r,
                               float g, float b, float a) {
        arm.render(poseStack, buffer, packedLight, packedOverlay, r, g, b, a);
    }
}
