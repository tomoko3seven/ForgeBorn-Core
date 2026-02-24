package com.sqvizers.forgeborn.common.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.worldgen.FBPlacedFeatures;

public class FBBiomes {

    public static final ResourceKey<Biome> LIVINGWOOD_FOREST = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(ForgeBorn.MOD_ID, "livingwood_forest"));
    public static final ResourceKey<Biome> DREAMWOOD_PLAINS = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(ForgeBorn.MOD_ID, "dreamwood_plains"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(LIVINGWOOD_FOREST, livingwoodForest(context));
        context.register(DREAMWOOD_PLAINS, dreamwoodPlains(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome livingwoodForest(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FBPlacedFeatures.MYSTICAL_FLOWERS_PLACED_KEY);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FBPlacedFeatures.LIVINGTREE_PLACED_KEY);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x61f3ff)
                        .waterFogColor(0x021333)
                        .skyColor(0xffd175)
                        .grassColorOverride(0xbfff30)
                        .foliageColorOverride(0xffa1cc)
                        .fogColor(0xffe3b3)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        /*
                         * .backgroundMusic(Musics.createGameMusic(
                         * context.lookup(Registries.SOUND_EVENT)
                         * .getOrThrow(ResourceKey.create(Registries.SOUND_EVENT,
                         * new ResourceLocation(ForgeBorn.MOD_ID, "alfheim_song")))
                         * ))
                         */
                        .build())
                .build();
    }

    public static Biome dreamwoodPlains(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FBPlacedFeatures.MYSTICAL_FLOWERS_PLACED_KEY);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FBPlacedFeatures.DREAMTREE_PLACED_KEY);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.1f)
                .temperature(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x61f3ff)
                        .waterFogColor(0x021333)
                        .skyColor(0x75a3ff)
                        .grassColorOverride(0x30b0ff)
                        .foliageColorOverride(0x30b0ff)
                        .fogColor(0xffe3b3)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        /*
                         * .backgroundMusic(Musics.createGameMusic(
                         * context.lookup(Registries.SOUND_EVENT)
                         * .getOrThrow(ResourceKey.create(Registries.SOUND_EVENT,
                         * new ResourceLocation(ForgeBorn.MOD_ID, "alfheim_song")))
                         * ))
                         */
                        .build())
                .build();
    }
}
