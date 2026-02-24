package com.sqvizers.forgeborn.common.machine.multiblock.primitive;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.ninni.twigs.registry.TwigsBlocks;
import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.gtbridge.FBRecipeTypes;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableCasingMachineModel;
import static com.sqvizers.forgeborn.common.registry.FBRegistration.REGISTRATE;

public class PrimitiveKilnMultiblock {

    public static final MultiblockMachineDefinition PRIMITIVE_KILN = REGISTRATE
            .multiblock("primitive_kiln", PrimitiveKiln::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(FBRecipeTypes.PRIMITIVE_KILN_RECIPES)
            .langValue("Primitive Kiln")
            .model(createWorkableCasingMachineModel(ForgeBorn.id("block/casings/twigs_cobblestone_bricks"),
                    GTCEu.id("block/multiblock/primitive_blast_furnace")))
            .hasBER(true)
            .appearanceBlock(TwigsBlocks.COBBLESTONE_BRICKS)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "XXX", "XXX", "#X#")
                    .aisle("XXX", "X&X", "X#X", "X#X")
                    .aisle("XXX", "XYX", "XXX", "#X#")
                    .where('X', blocks(TwigsBlocks.COBBLESTONE_BRICKS.get()))
                    .where('#', Predicates.air())
                    .where('&', Predicates.air()
                            .or(Predicates.custom(bws -> GTUtil.isBlockSnow(bws.getBlockState()), null)))
                    .where('Y', Predicates.controller(blocks(definition.getBlock())))
                    .build())
            .register();

    public static void init() {}
}
