package com.sqvizers.forgeborn.common.data.datagen.models;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.model.machine.overlays.WorkableOverlays;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

import com.sqvizers.forgeborn.ForgeBorn;

import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.*;

public class FBMachineModels {

    // Original Code by Phoenix
    public static MachineBuilder.ModelInitializer createOverlayCasingMachineModel(
                                                                                  String overlayName,
                                                                                  String casingTexturePath) {
        return (ctx, prov, builder) -> {
            builder.forAllStatesModels(state -> {
                BlockModelBuilder model = prov.models().nested()
                        .parent(prov.models().getExistingFile(
                                GTCEu.id("block/overlay/2_layer/front_emissive")));
                casingTextures(model, casingTexturePath);

                model.texture("overlay",
                        ForgeBorn.id("block/overlay/machine/" + overlayName + "_base"));

                model.texture("overlay_emissive",
                        ForgeBorn.id("block/overlay/machine/" + overlayName + "_emissive"));

                return model;
            });

            builder.addReplaceableTextures("bottom", "top", "side");
        };
    }

    public static MachineBuilder.ModelInitializer createSeparateControllerCasingMachineModel(ResourceLocation controllerTexture,
                                                                                             ResourceLocation baseCasingTexture,
                                                                                             ResourceLocation overlayDir) {
        return (ctx, prov, builder) -> {
            WorkableOverlays overlays = WorkableOverlays.get(overlayDir, prov.getExistingFileHelper());

            builder.forAllStates(state -> {
                RecipeLogic.Status status = state.getValue(RecipeLogic.STATUS_PROPERTY);

                BlockModelBuilder model = prov.models().nested()
                        .parent(prov.models().getExistingFile(CUBE_ALL_SIDED_OVERLAY_MODEL))
                        .texture("all", controllerTexture);
                return addWorkableOverlays(overlays, status, model);
            });
            builder.addTextureOverride("all", baseCasingTexture);
        };
    }

    public static void casingTextures(BlockModelBuilder model, String casingTexturePath) {
        ResourceLocation casing = ForgeBorn.id("block/" + casingTexturePath);
        model.texture("bottom", casing);
        model.texture("top", casing);
        model.texture("side", casing);
    }
}
