package com.sqvizers.forgeborn.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Redirect(method = "renderHand",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType changeTemplateArmRenderType(ResourceLocation texture, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pArm, ModelPart pArmSleeve) {
        // Проверяем, надета ли Template Arm (мана-рука)
        if (ArmUtils.allowsOffhand(pPlayer)) {
            // entityTranslucent позволит видеть прозрачность, если она есть в текстуре
            return RenderType.entityTranslucent(texture);
        }
        return RenderType.entitySolid(texture);
    }
}