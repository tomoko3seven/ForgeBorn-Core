package com.sqvizers.forgeborn.gtbridge;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class FBRecipeTypes {

    // Simple Machines (Steam)
    public static final GTRecipeType PRESSURIZER_RECIPES = GTRecipeTypes
            .register("pressurizer_recipes", STEAM)
            .setSound(WIREMILL_RECIPES.getSound())
            .setMaxIOSize(1, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT);

    public final static GTRecipeType PRIMITIVE_KILN_RECIPES = register("primitive_kiln", MULTIBLOCK)
            .setMaxIOSize(3, 3, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setMaxTooltips(1)
            .setSound(GTSoundEntries.FIRE);

    public static void init() {}
}
