package com.sqvizers.forgeborn.common.data;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.entities.HookEntity;
import com.sqvizers.forgeborn.common.entities.MeteorEntity;
import com.sqvizers.forgeborn.common.entities.VampireBoss;

public class FBEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            ForgeBorn.MOD_ID);

    public static final RegistryObject<EntityType<HookEntity>> HOOK_ENTITY = REGISTRY.register("hook_entity",
            () -> EntityType.Builder.<HookEntity>of(HookEntity::new, MobCategory.MISC)
                    .sized(0.50F, 0.50F)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("hook_entity"));

    public static final RegistryObject<EntityType<MeteorEntity>> METEOR = REGISTRY.register("meteor",
            () -> EntityType.Builder.<MeteorEntity>of(MeteorEntity::new, MobCategory.MISC)
                    .sized(2.0F, 2.0F)
                    .clientTrackingRange(256)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("meteor"));
    // Bosses
    public static final RegistryObject<EntityType<VampireBoss>> VAMPIRE_BOSS = REGISTRY.register("vampire_boss",
            () -> EntityType.Builder.of(VampireBoss::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F) // Размеры как у игрока
                    .build("vampire_boss"));
}
