package com.sqvizers.forgeborn.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sqvizers.forgeborn.common.entities.HookEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;

public class HookRenderer extends EntityRenderer<HookEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("forgeborn", "textures/entity/hook.png");

    public HookRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(HookEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Поворот крюка
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        poseStack.popPose();

        // Отрисовка черного троса
        Entity owner = entity.getOwner();
        if (owner != null) {
            // Теперь мы ПЕРЕДАЕМ packedLight в метод
            renderTether(entity, partialTicks, poseStack, buffer, owner, packedLight);
        }

        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderTether(HookEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, Entity owner, int packedLight) {
        poseStack.pushPose();

        // Позиция "выхода" троса (из груди игрока)
        double renderX = Mth.lerp(partialTicks, owner.xo, owner.getX());
        double renderY = Mth.lerp(partialTicks, owner.yo, owner.getY()) + owner.getEyeHeight() * 0.7;
        double renderZ = Mth.lerp(partialTicks, owner.zo, owner.getZ());

        // Позиция самого крюка
        double hookX = Mth.lerp(partialTicks, entity.xo, entity.getX());
        double hookY = Mth.lerp(partialTicks, entity.yo, entity.getY());
        double hookZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());

        float dx = (float)(renderX - hookX);
        float dy = (float)(renderY - hookY);
        float dz = (float)(renderZ - hookZ);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.leash());
        Matrix4f matrix = poseStack.last().pose();

        // Рисуем трос как черную линию (RGBA: 0, 0, 0, 255)
        for (int i = 0; i <= 24; ++i) {
            float ratio = (float)i / 24.0F;
            vertexConsumer.vertex(matrix, dx * ratio, dy * ratio, dz * ratio)
                    .color(0, 0, 0, 255) // Черный цвет
                    .uv2(packedLight)    // Теперь переменная доступна!
                    .endVertex();
        }

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(HookEntity entity) {
        return TEXTURE;
    }
}