package com.sqvizers.forgeborn.gtbridge;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;

public class FBRecipeTypes {

    // Simple Machines (Steam)
    public static final GTRecipeType PRESSURIZER_RECIPES = GTRecipeTypes
            .register("pressurizer_recipes", STEAM)
            .setSound(WIREMILL_RECIPES.getSound())
            .setMaxIOSize(1, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static void init() {}
}
