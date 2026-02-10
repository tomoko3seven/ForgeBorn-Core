package com.sqvizers.forgeborn.common.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.worldgen.biome.FBBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.List;
import java.util.OptionalLong;

public class FBDimensions {
    public static final ResourceKey<LevelStem> ALFHEIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(ForgeBorn.MOD_ID, "alfheim"));
    public static final ResourceKey<Level> ALFHEIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(ForgeBorn.MOD_ID, "alfheim"));
    public static final ResourceKey<DimensionType> ALFHEIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(ForgeBorn.MOD_ID, "alfheim_type"));
    public static final ResourceKey<NoiseGeneratorSettings> ALFHEIM_NOISE_GEN = ResourceKey.create(Registries.NOISE_SETTINGS,
            new ResourceLocation(ForgeBorn.MOD_ID, "alfheim_noise_gen"));


    private static final ResourceKey<DensityFunction> MC_SHIFT_X = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "shift_x"));
    private static final ResourceKey<DensityFunction> MC_SHIFT_Z = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "shift_z"));
    private static final ResourceKey<DensityFunction> MC_Y = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "y"));
    private static final ResourceKey<DensityFunction> MC_CONTINENTS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/continents"));
    private static final ResourceKey<DensityFunction> MC_EROSION = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/erosion"));
    private static final ResourceKey<DensityFunction> MC_RIDGES = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/ridges"));

    private static DensityFunction getFunction(HolderGetter<DensityFunction> functions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(functions.getOrThrow(key));
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(ALFHEIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                384, // height
                384, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0f, // Ambient Light
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        DensityFunction xShift = getFunction(functions, MC_SHIFT_X);
        DensityFunction zShift = getFunction(functions, MC_SHIFT_Z);
        DensityFunction yFunc = getFunction(functions, MC_Y);
        DensityFunction base3d = getFunction(functions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/sloped_cheese")));

        // Пещеры
        DensityFunction cavesEntrances = getFunction(functions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/caves/entrances")));
        DensityFunction cavesPillars = getFunction(functions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/caves/pillars")));
        DensityFunction cavesSpaghetti = getFunction(functions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("minecraft", "overworld/caves/spaghetti_2d")));


        DensityFunction finalDensity = DensityFunctions.interpolated(
                DensityFunctions.min(base3d,
                        DensityFunctions.max(
                                DensityFunctions.add(cavesEntrances, cavesSpaghetti),
                                cavesPillars
                        )
                )
        );

        context.register(ALFHEIM_NOISE_GEN, new NoiseGeneratorSettings(
                NoiseSettings.create(-64, 384, 1, 1),

                BotaniaBlocks.livingrock.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),

                new NoiseRouter(
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.shiftedNoise2d(xShift, zShift, 0.1D, noises.getOrThrow(Noises.TEMPERATURE)),
                        DensityFunctions.shiftedNoise2d(xShift, zShift, 0.1D, noises.getOrThrow(Noises.VEGETATION)),
                        getFunction(functions, MC_CONTINENTS),
                        getFunction(functions, MC_EROSION),
                        yFunc,
                        getFunction(functions, MC_RIDGES),
                        base3d,
                        finalDensity,
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero()
                ),

                SurfaceRules.sequence(

                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("minecraft:bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),


                        SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
                                SurfaceRules.sequence(

                                        SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())),
                                        SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                                )
                        ),


                        SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, true, CaveSurface.FLOOR),
                                SurfaceRules.state(Blocks.DIRT.defaultBlockState()))
                ),
                List.of(),
                63,
                false,
                true,
                false,
                true
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(

                                Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(FBBiomes.LIVINGWOOD_FOREST)),

                                Pair.of(Climate.parameters(0.5F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(FBBiomes.DREAMWOOD_PLAINS))
                        ))
                ),
                noiseGenSettings.getOrThrow(ALFHEIM_NOISE_GEN));

        context.register(ALFHEIM_KEY, new LevelStem(dimTypes.getOrThrow(ALFHEIM_TYPE), generator));
    }

    public static void register() {
        ForgeBorn.LOGGER.info("Registering Alfheim Dimension for " + ForgeBorn.MOD_ID);
    }
}