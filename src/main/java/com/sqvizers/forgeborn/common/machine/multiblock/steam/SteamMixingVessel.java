package com.sqvizers.forgeborn.common.machine.multiblock.steam;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.gregtechceu.gtceu.common.registry.GTRegistration;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;

public class SteamMixingVessel {

    public static final MultiblockMachineDefinition STEAM_MIXING_VESSEL = GTRegistration.REGISTRATE
            .multiblock("steam_mixing_vessel", SteamParallelMultiblockMachine::new)
            .langValue("Steam Mixing Vessel")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.MIXER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .appearanceBlock(GCYMBlocks.CASING_INDUSTRIAL_STEAM)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("B   B", "B   B", "CCCCC", " CCC ", " CCC ", " CCC ", "     ")
                    .aisle("     ", "     ", "CDDDC", "C  EC", "C   C", "C   C", " CCC ")
                    .aisle("     ", "     ", "CDDDC", "C F C", "C B C", "C B C", " CCC ")
                    .aisle("     ", "     ", "CDDDC", "CE  C", "C   C", "C   C", " CCC ")
                    .aisle("B   B", "B   B", "CCCCC", " CYC ", " CCC ", " CCC ", "     ")
                    .where(" ", any())
                    .where('C', blocks(GCYMBlocks.CASING_INDUSTRIAL_STEAM.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1)
                                    .setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)
                                    .setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1).setExactLimit(1)))
                    .where("D", blocks(BRONZE_BRICKS_HULL.get()))
                    .where('B', Predicates.frames(GTMaterials.Bronze))
                    .where("E", blocks(CASING_BRONZE_GEARBOX.get()))
                    .where("F", blocks(CASING_BRONZE_PIPE.get()))
                    .where("Y", Predicates.controller(blocks(definition.getBlock())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/gcym/industrial_steam_casing"),
                    GTCEu.id("block/multiblock/large_mixer"))
            /*
             * .tooltips(Component.translatable("forgeborncore.multiblock.smv.tooltip.1"),
             * Component.translatable("forgeborncore.multiblock.smv.tooltip.2"))
             */
            .register();

    public static void init() {}
}
