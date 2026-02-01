package com.sqvizers.forgeborn.network;

import com.sqvizers.forgeborn.network.item.arms.ArmAbilityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("forgeborn", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(packetId++, ArmAbilityPacket.class,
                ArmAbilityPacket::encode,
                ArmAbilityPacket::decode,
                ArmAbilityPacket::handle);
    }
}