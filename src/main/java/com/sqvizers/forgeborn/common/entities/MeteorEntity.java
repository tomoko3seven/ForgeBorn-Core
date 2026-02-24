package com.sqvizers.forgeborn.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MeteorEntity extends Entity {

    private static final double FALL_SPEED = -2.5;

    public MeteorEntity(EntityType<? extends MeteorEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 pos = this.position();
        Vec3 nextPos = pos.add(0, FALL_SPEED, 0);

        BlockHitResult hitResult = this.level().clip(new ClipContext(
                pos, nextPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this));

        if (hitResult.getType() != HitResult.Type.MISS) {
            this.setPos(hitResult.getLocation());
            if (!this.level().isClientSide) {
                this.impact();
                this.discard();
            }
            return;
        }

        this.setPos(nextPos.x, nextPos.y, nextPos.z);

        if (this.level().isClientSide) {
            for (int i = 0; i < 15; i++) {
                this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        this.getX() + (random.nextDouble() - 0.5) * 3,
                        this.getY() + 2,
                        this.getZ() + (random.nextDouble() - 0.5) * 3,
                        0, 0.2, 0);

                // Добавим немного искр для красоты
                if (random.nextInt(3) == 0) {
                    this.level().addParticle(ParticleTypes.LAVA,
                            this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                }
            }
        }
    }

    private void impact() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        BlockPos center = this.blockPosition();

        AABB killZone = new AABB(center).inflate(100);
        serverLevel.getEntitiesOfClass(LivingEntity.class, killZone).forEach(entity -> {
            entity.die(serverLevel.damageSources().explosion(this, this));
        });

        int oreRadius = 6;
        int foliageRadius = 30;

        BlockPos.betweenClosedStream(center.offset(-foliageRadius, -oreRadius, -foliageRadius),
                center.offset(foliageRadius, oreRadius, foliageRadius)).forEach(pos -> {
                    double distanceSq = pos.distSqr(center);

                    if (distanceSq <= oreRadius * oreRadius) {
                        serverLevel.setBlock(pos, Blocks.IRON_ORE.defaultBlockState(), 3);
                    } else if (distanceSq <= foliageRadius * foliageRadius) {
                        if (serverLevel.getBlockState(pos).is(BlockTags.LEAVES)) {
                            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                });

        serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 20.0F, false, Level.ExplosionInteraction.NONE);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {}
}
