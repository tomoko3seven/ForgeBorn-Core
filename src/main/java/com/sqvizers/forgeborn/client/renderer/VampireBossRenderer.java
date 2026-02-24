package com.sqvizers.forgeborn.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.entities.VampireBoss;

public class VampireBossRenderer extends HumanoidMobRenderer<VampireBoss, HumanoidModel<VampireBoss>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgeBorn.MOD_ID,
            "textures/entity/vampire_boss.png");

    public VampireBossRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(VampireBoss entity) {
        return TEXTURE;
    }
}
