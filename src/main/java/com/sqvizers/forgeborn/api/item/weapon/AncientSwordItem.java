package com.sqvizers.forgeborn.api.item.weapon;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AncientSwordItem extends SwordItem {
    public AncientSwordItem(Properties props) {
        super(Tiers.IRON, 3, -2.4f, props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                boolean newState = !stack.getOrCreateTag().getBoolean("IsOpen");
                stack.getOrCreateTag().putBoolean("IsOpen", newState);
                level.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.IRON_TRAPDOOR_OPEN, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.5f);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        } else {
            if (stack.getOrCreateTag().getBoolean("IsOpen")) {
                if (!player.getCooldowns().isOnCooldown(this)) {
                    performAnimeSlash(level, player);
                    player.getCooldowns().addCooldown(this, 300);
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }
            }
        }
        return super.use(level, player, hand);
    }

    private void performAnimeSlash(Level level, Player player) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        int[] delays = {0, 6, 12, 18};

        for (int step = 0; step < delays.length; step++) {
            final int currentStep = step;

            serverLevel.getServer().tell(new net.minecraft.server.TickTask(serverLevel.getServer().getTickCount() + delays[step], () -> {
                if (player.isAlive()) {
                    spawnSlashParticles(serverLevel, player, currentStep);
                    applySlashDamage(serverLevel, player);
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_SWEEP,
                            net.minecraft.sounds.SoundSource.PLAYERS, 1.5f, 0.5f + (currentStep * 0.2f));
                }
            }));
        }
    }

    private void spawnSlashParticles(ServerLevel level, Player player, int step) {
        Vec3 pos = player.position().add(0, 1.0, 0);
        int points = 40;
        double radius = 3.5;

        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(i * (360.0 / points));
            double x = Math.cos(angle) * radius;
            double tempY = Math.sin(angle) * radius;

            Vec3 particlePos;

            switch (step) {
                case 0 ->
                        particlePos = new Vec3(pos.x + x, pos.y, pos.z + tempY);
                case 1 ->
                        particlePos = new Vec3(pos.x, pos.y + x, pos.z + tempY);
                case 2 ->
                        particlePos = new Vec3(pos.x + x, pos.y + x, pos.z + tempY);
                default ->
                        particlePos = new Vec3(pos.x + x, pos.y - x, pos.z + tempY);
            }

            level.sendParticles(new net.minecraft.core.particles.BlockParticleOption(
                            net.minecraft.core.particles.ParticleTypes.BLOCK, net.minecraft.world.level.block.Blocks.SAND.defaultBlockState()),
                    particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0.02);
        }
    }

    private void applySlashDamage(ServerLevel level, Player player) {
        AABB area = player.getBoundingBox().inflate(4.0);
        level.getEntities(player, area, e -> e instanceof LivingEntity).forEach(entity -> {
            entity.hurt(level.damageSources().playerAttack(player), 4.0f);
            entity.invulnerableTime = 0;
        });
    }
}