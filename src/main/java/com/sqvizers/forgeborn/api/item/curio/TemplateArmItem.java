package com.sqvizers.forgeborn.api.item.curio;

import com.sqvizers.forgeborn.utils.Arm.ArmUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class TemplateArmItem extends Item implements ICurioItem {
    public TemplateArmItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public void armVerticalDash(Level level, Player player) {
        if (level.isClientSide) return;

        if (!player.getCooldowns().isOnCooldown(this)) {
            player.setDeltaMovement(player.getDeltaMovement().x, 0.8, player.getDeltaMovement().z);
            player.fallDistance = 0;
            player.hurtMarked = true;

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.COPPER_BREAK,
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);

            player.getCooldowns().addCooldown(this, 40);
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && !event.player.level().isClientSide) {
            Player player = event.player;
            ItemStack arm = ArmUtils.getEquippedArm(player);

            if (!arm.isEmpty() && player.getCooldowns().isOnCooldown(arm.getItem())) {

                if (!player.onGround()) {
                    player.setDeltaMovement(0, 0.01, 0);
                    player.hurtMarked = true;
                }
            }
        }
    }

}