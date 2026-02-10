package com.sqvizers.forgeborn.common.data.datagen;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.worldgen.FBBiomeModifiers;
import com.sqvizers.forgeborn.common.worldgen.FBConfiguredFeatures;
import com.sqvizers.forgeborn.common.worldgen.FBPlacedFeatures;
import com.sqvizers.forgeborn.common.worldgen.biome.FBBiomes;
import com.sqvizers.forgeborn.common.worldgen.dimension.FBDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FBWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, FBDimensions::bootstrapType)
            .add(Registries.CONFIGURED_FEATURE, FBConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FBPlacedFeatures::bootstrap)
            .add(Registries.BIOME, FBBiomes::boostrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, FBBiomeModifiers::bootstrap)
            .add(Registries.NOISE_SETTINGS, FBDimensions::bootstrapNoise)
            .add(Registries.LEVEL_STEM, FBDimensions::bootstrapStem);

    public FBWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ForgeBorn.MOD_ID));
    }
}