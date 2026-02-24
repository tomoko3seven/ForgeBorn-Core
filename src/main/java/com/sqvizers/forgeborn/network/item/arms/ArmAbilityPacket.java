package com.sqvizers.forgeborn.network.item.arms;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import com.sqvizers.forgeborn.api.item.curio.TemplateArmItem;
import com.sqvizers.forgeborn.common.data.FBItems;
import com.sqvizers.forgeborn.common.entities.HookEntity;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;

import java.util.function.Supplier;

public class ArmAbilityPacket {

    public ArmAbilityPacket(int id) {}

    public static void encode(ArmAbilityPacket pkt, FriendlyByteBuf buf) {}

    public static ArmAbilityPacket decode(FriendlyByteBuf buf) {
        return new ArmAbilityPacket(0);
    }

    public static void handle(ArmAbilityPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            ItemStack equippedArm = ArmUtils.getEquippedArm(player);
            if (equippedArm.isEmpty()) return;

            if (equippedArm.is(FBItems.SCULK_ARM.get())) {
                if (!player.getCooldowns().isOnCooldown(FBItems.SCULK_ARM.get())) {
                    Vec3 start = player.getEyePosition();
                    Vec3 look = player.getLookAngle();
                    int range = 10;

                    for (int i = 1; i <= range; i++) {
                        Vec3 point = start.add(look.scale(i));
                        if (player.level() instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,
                                    point.x, point.y, point.z, 1, 0, 0, 0, 0);
                        }

                        player.level()
                                .getEntities(player,
                                        new net.minecraft.world.phys.AABB(point.x - 1, point.y - 1, point.z - 1,
                                                point.x + 1, point.y + 1, point.z + 1),
                                        e -> e instanceof LivingEntity)
                                .forEach(target -> {
                                    target.hurt(player.damageSources().sonicBoom(player), 15.0F);
                                    target.push(look.x * 0.5, 0.2, look.z * 0.5);
                                });
                    }

                    player.level().playSound(null, player.blockPosition(),
                            SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.0F);
                    player.getCooldowns().addCooldown(FBItems.SCULK_ARM.get(), 200);
                }
            }

            else if (equippedArm.is(FBItems.HOOK_ARM.get())) {
                if (!player.getCooldowns().isOnCooldown(FBItems.HOOK_ARM.get())) {
                    player.level().getEntitiesOfClass(HookEntity.class, player.getBoundingBox().inflate(64),
                            h -> h.getOwner() == player).forEach(net.minecraft.world.entity.Entity::discard);

                    HookEntity hook = new HookEntity(player, player.level());
                    player.level().addFreshEntity(hook);
                    player.getCooldowns().addCooldown(FBItems.HOOK_ARM.get(), 40);
                }
            }

            else if (equippedArm.is(FBItems.TEMPLATE_ARM.get())) {
                if (equippedArm.getItem() instanceof TemplateArmItem templateArm) {
                    templateArm.armVerticalDash(player.level(), player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
