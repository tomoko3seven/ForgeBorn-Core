package com.sqvizers.forgeborn.client;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.network.PacketHandler;
import com.sqvizers.forgeborn.network.item.arms.ArmAbilityPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// This part handles GAMEPLAY LOGIC (Ticks) - MUST use Bus.FORGE
@Mod.EventBusSubscriber(modid = ForgeBorn.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (ClientModEvents.ARM_ABILITY_KEY.consumeClick()) {
                ForgeBorn.LOGGER.info("Hook Key Pressed! Sending Packet...");
                PacketHandler.INSTANCE.sendToServer(new ArmAbilityPacket(0));
            }
        }
    }
}
