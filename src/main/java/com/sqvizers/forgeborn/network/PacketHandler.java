package com.sqvizers.forgeborn.network;

import com.sqvizers.forgeborn.api.item.ManaBuilderItem;
import com.sqvizers.forgeborn.api.item.curio.TemplateArmItem;
import com.sqvizers.forgeborn.network.item.arms.ArmAbilityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

        INSTANCE.registerMessage(packetId++, PacketSyncSize.class,
                PacketSyncSize::encode,
                PacketSyncSize::decode,
                PacketSyncSize::handle);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class InputHandler {

        @SubscribeEvent
        public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
            Player player = Minecraft.getInstance().player;
            if (player == null || player.isShiftKeyDown()) return;

            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof ManaBuilderItem) {
                event.setCanceled(true);


                int direction = event.getScrollDelta() > 0 ? 1 : -1;
                int currentSize = ManaBuilderItem.getSize(stack);
                int newSize = Math.max(0, Math.min(currentSize + direction, 10));


                ManaBuilderItem.setSize(stack, newSize);


                INSTANCE.sendToServer(new PacketSyncSize(newSize));

                player.displayClientMessage(Component.literal("Radius: " + newSize), true);
            }
        }
    }
}