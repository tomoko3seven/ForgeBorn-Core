package com.sqvizers.forgeborn.common.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBBlocks;

import java.util.List;

public class FBPlacedFeatures {

    public static final ResourceKey<PlacedFeature> LIVINGTREE_PLACED_KEY = registerKey("livingtree_placed");
    public static final ResourceKey<PlacedFeature> DREAMTREE_PLACED_KEY = registerKey("dreamtree_placed");
    public static final ResourceKey<PlacedFeature> SULFUR_MUSHROOM_PLACED_KEY = registerKey(
            "sulfur_mushroom_placed_key");

    public static final ResourceKey<PlacedFeature> MYSTICAL_FLOWERS_PLACED_KEY = registerKey("mystical_flowers_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, LIVINGTREE_PLACED_KEY, configuredFeatures.getOrThrow(FBConfiguredFeatures.LIVINGTREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        FBBlocks.LIVINGTREE_SAPLING.get()));
        register(context, DREAMTREE_PLACED_KEY, configuredFeatures.getOrThrow(FBConfiguredFeatures.DREAM_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        FBBlocks.DREAMTREE_SAPLING.get()));

        register(context, MYSTICAL_FLOWERS_PLACED_KEY,
                configuredFeatures.getOrThrow(FBConfiguredFeatures.MYSTICAL_FLOWERS_KEY),
                List.of(
                        CountPlacement.of(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()));

        register(context, SULFUR_MUSHROOM_PLACED_KEY,
                configuredFeatures.getOrThrow(FBConfiguredFeatures.SULFURIC_MUSHROOM_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256)),
                        BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ForgeBorn.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
