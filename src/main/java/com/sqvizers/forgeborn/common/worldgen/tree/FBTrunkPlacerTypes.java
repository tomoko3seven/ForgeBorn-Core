package com.sqvizers.forgeborn.common.worldgen.tree;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.worldgen.tree.custom.DreamtreeTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FBTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, ForgeBorn.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<DreamtreeTrunkPlacer>> DREAM_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("dream_trunk_placer",
                    () -> new TrunkPlacerType<>(DreamtreeTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACER_TYPES.register(eventBus);
    }
}