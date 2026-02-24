package com.sqvizers.forgeborn.common.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBEntities;
import com.sqvizers.forgeborn.common.entities.VampireBoss;

@Mod.EventBusSubscriber(modid = ForgeBorn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FBEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(FBEntities.VAMPIRE_BOSS.get(), VampireBoss.createAttributes().build());
    }
}
