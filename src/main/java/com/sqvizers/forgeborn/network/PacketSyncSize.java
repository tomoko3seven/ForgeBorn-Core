package com.sqvizers.forgeborn.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import com.sqvizers.forgeborn.api.item.ManaBuilderItem;

import java.util.function.Supplier;

public class PacketSyncSize {

    private final int size;

    public PacketSyncSize(int size) {
        this.size = size;
    }

    public static void encode(PacketSyncSize msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.size);
    }

    public static PacketSyncSize decode(FriendlyByteBuf buf) {
        return new PacketSyncSize(buf.readInt());
    }

    public static void handle(PacketSyncSize msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof ManaBuilderItem) {
                    ManaBuilderItem.setSize(stack, msg.size);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
