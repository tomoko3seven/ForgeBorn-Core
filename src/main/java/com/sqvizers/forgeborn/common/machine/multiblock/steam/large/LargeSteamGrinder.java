package com.sqvizers.forgeborn.common.machine.multiblock.steam.large;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GCYMBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.registry.GTRegistration;

import com.sqvizers.forgeborn.api.machines.StrongSteamParallelMultiblockMachine;

import static com.gregtechceu.gtceu.api.pattern.Predicates.any;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;

public class LargeSteamGrinder {

    public static final MultiblockMachineDefinition LARGE_STEAM_GRINDER = GTRegistration.REGISTRATE
            .multiblock("large_steam_grinder", StrongSteamParallelMultiblockMachine::new)
            .langValue("Large Steam Grinder")
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.MACERATOR_RECIPES)
            .recipeModifier(StrongSteamParallelMultiblockMachine::recipeModifier, true)
            .appearanceBlock(GCYMBlocks.CASING_INDUSTRIAL_STEAM)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("B   B", "B   B", "CCCCC", " CCC ", " CCC ", " CCC ", "     ")
                    .aisle("     ", "     ", "CDDDC", "C  EC", "C   C", "C   C", " CCC ")
                    .aisle("     ", "     ", "CDDDC", "C F C", "C B C", "C B C", " CCC ")
                    .aisle("     ", "     ", "CDDDC", "CE  C", "C   C", "C   C", " CCC ")
                    .aisle("B   B", "B   B", "CCCCC", " CYC ", " CCC ", " CCC ", "     ")
                    .where(' ', any())
                    .where('C', blocks(GCYMBlocks.CASING_INDUSTRIAL_STEAM.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1)
                                    .setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)
                                    .setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1).setExactLimit(1)))
                    .where('D', blocks(BRONZE_BRICKS_HULL.get()))
                    .where('B', Predicates.frames(GTMaterials.Bronze))
                    .where('E', blocks(CASING_BRONZE_GEARBOX.get()))
                    .where('F', blocks(CASING_BRONZE_PIPE.get()))
                    .where('Y', Predicates.controller(blocks(definition.getBlock())))
                    .build())
            .workableCasingModel(GTCEu.id("block/casings/gcym/industrial_steam_casing"),
                    GTCEu.id("block/multiblock/gcym/large_mixer"))
            .register();

    public static void init() {}
}
