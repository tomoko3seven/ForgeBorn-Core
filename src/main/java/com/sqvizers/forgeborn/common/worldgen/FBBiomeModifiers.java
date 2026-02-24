package com.sqvizers.forgeborn.common.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import com.sqvizers.forgeborn.ForgeBorn;

public class FBBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_SULFUR_MUSHROOM = registerKey("add_sulfur_mushroom");
    public static final ResourceKey<BiomeModifier> ADD_LIVINGTREE = registerKey("add_livingtree");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        /*
         * context.register(ADD_LIVINGTREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
         * biomes.getOrThrow(BiomeTags.IS_FOREST),
         * HolderSet.direct(placedFeatures.getOrThrow(FBPlacedFeatures.LIVINGTREE_PLACED_KEY)),
         * GenerationStep.Decoration.VEGETAL_DECORATION));
         */
        context.register(ADD_SULFUR_MUSHROOM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_CAVE),
                HolderSet.direct(placedFeatures.getOrThrow(FBPlacedFeatures.SULFUR_MUSHROOM_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ForgeBorn.MOD_ID, name));
    }
}
