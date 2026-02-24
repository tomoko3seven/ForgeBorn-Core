package com.sqvizers.forgeborn.common.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBBlocks;
import com.sqvizers.forgeborn.common.worldgen.tree.custom.DreamtreeTrunkPlacer;
import com.sqvizers.forgeborn.common.worldgen.tree.custom.LivingTreeRootDecorator;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.List;

public class FBConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LIVINGTREE_KEY = registerKey("living_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAM_TREE_KEY = registerKey("dream_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYSTICAL_FLOWERS_KEY = registerKey("mystical_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFURIC_MUSHROOM_KEY = registerKey("sulfur_mushroom_key");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, LIVINGTREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BotaniaBlocks.livingwoodLog),
                new GiantTrunkPlacer(20, 10, 15),
                BlockStateProvider.simple(FBBlocks.LIVINGTREE_LEAVES.get()),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 20)),
                new TwoLayersFeatureSize(1, 1, 2))
                .decorators(List.of(LivingTreeRootDecorator.INSTANCE))
                .build());

        register(context, DREAM_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BotaniaBlocks.dreamwoodLog),
                new DreamtreeTrunkPlacer(15, 10, 5),
                BlockStateProvider.simple(FBBlocks.DREAMTREE_LEAVES.get()),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
                new TwoLayersFeatureSize(1, 1, 2)).build());

        SimpleWeightedRandomList.Builder<BlockState> flowers = SimpleWeightedRandomList.builder();
        flowers.add(BotaniaBlocks.whiteFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.orangeFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.magentaFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.lightBlueFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.yellowFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.limeFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.pinkFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.grayFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.lightGrayFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.cyanFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.purpleFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.blueFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.brownFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.greenFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.redFlower.defaultBlockState(), 1);
        flowers.add(BotaniaBlocks.blackFlower.defaultBlockState(), 1);

        register(context, MYSTICAL_FLOWERS_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(
                        64,
                        7,
                        3,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(new WeightedStateProvider(flowers.build())))));

        register(context, SULFURIC_MUSHROOM_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(
                        32, 7, 3,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        BlockStateProvider.simple(FBBlocks.SULFURIC_MUSHROOM.get())))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ForgeBorn.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration,
            F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                 ResourceKey<ConfiguredFeature<?, ?>> key, F feature,
                                                 FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
