package com.sqvizers.forgeborn.common.worldgen.tree.custom;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import com.sqvizers.forgeborn.common.worldgen.FBConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

public class LivingTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return FBConfiguredFeatures.LIVINGTREE_KEY;
    }
}
