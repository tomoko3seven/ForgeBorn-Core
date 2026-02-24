package com.sqvizers.forgeborn.common.mob.event;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "forgeborn", bus = Mod.EventBusSubscriber.Bus.MOD)
public class GlobalHealthSetter {

    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            if (type.getCategory() == MobCategory.MONSTER) {
                if (event.has(type, Attributes.MAX_HEALTH)) {
                    event.add(type, Attributes.MAX_HEALTH, 50.0);
                }
            }
        }
    }
}
