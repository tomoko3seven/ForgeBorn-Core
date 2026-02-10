package com.sqvizers.forgeborn.common.worldgen.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import vazkii.botania.common.block.BotaniaBlocks;

public class FBTrunkPlacerDirtUtil {
    public static boolean isAlfheimForestGrass(LevelSimulatedReader levelSimulatedReader, BlockPos blockPos) {
        return levelSimulatedReader.isStateAtPosition(blockPos, (blockState) -> blockState.is(BotaniaBlocks.vividGrass));
    }
}
