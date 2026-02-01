package com.sqvizers.forgeborn.common.data;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.client.renderer.HookRenderer;
import com.sqvizers.forgeborn.common.entities.HookEntity;
import com.sqvizers.forgeborn.common.registry.FBRegistration;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FBEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ForgeBorn.MOD_ID);

    public static final RegistryObject<EntityType<HookEntity>> HOOK_ENTITY =
            REGISTRY.register("hook_entity", () -> EntityType.Builder.<HookEntity>of(HookEntity::new, MobCategory.MISC)
                    .sized(0.50F, 0.50F)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("hook_entity"));
}