package com.sqvizers.forgeborn.network.item.arms;

import com.sqvizers.forgeborn.common.entities.HookEntity;
import com.sqvizers.forgeborn.common.data.FBItems;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ArmAbilityPacket {
    public ArmAbilityPacket(int id) {}

    public static void encode(ArmAbilityPacket pkt, FriendlyByteBuf buf) {}
    public static ArmAbilityPacket decode(FriendlyByteBuf buf) { return new ArmAbilityPacket(0); }

    public static void handle(ArmAbilityPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            if (ArmUtils.isArmEquipped(player, FBItems.HOOK_ARM.get())) {

                if (!player.getCooldowns().isOnCooldown(FBItems.HOOK_ARM.get())) {

                    player.level().getEntitiesOfClass(HookEntity.class, player.getBoundingBox().inflate(64),
                            h -> h.getOwner() == player).forEach(net.minecraft.world.entity.Entity::discard);

                    HookEntity hook = new HookEntity(player, player.level());
                    player.level().addFreshEntity(hook);

                    player.getCooldowns().addCooldown(FBItems.HOOK_ARM.get(), 40);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}