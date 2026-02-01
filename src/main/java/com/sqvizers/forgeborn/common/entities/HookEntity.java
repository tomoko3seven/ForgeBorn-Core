package com.sqvizers.forgeborn.common.entities;

import com.sqvizers.forgeborn.common.data.FBEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class HookEntity extends Projectile {
    private boolean attached = false;
    private boolean returning = false;
    private Entity caughtEntity = null;

    public HookEntity(EntityType<? extends HookEntity> type, Level level) {
        super(type, level);
        this.noPhysics = false;
    }

    public HookEntity(LivingEntity owner, Level level) {
        this(FBEntities.HOOK_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY(), owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        Entity owner = this.getOwner();
        if (owner == null || !owner.isAlive()) {
            this.discard();
            return;
        }

        double distance = this.distanceTo(owner);

        if (!attached && !returning && distance > 15.0) {
            this.returning = true;
        }

        Vec3 movement = this.getDeltaMovement();
        this.setPos(this.getX() + movement.x, this.getY() + movement.y, this.getZ() + movement.z);

        if (attached) {
            this.setDeltaMovement(Vec3.ZERO);

            if (owner instanceof Player player) {
                Vec3 dir = this.position().subtract(player.position()).normalize();


                double pullStrength = 0.20;
                Vec3 currentMovement = player.getDeltaMovement();

                double newX = currentMovement.x + dir.x * pullStrength;
                double newY = currentMovement.y + (dir.y * pullStrength * 0.5);
                double newZ = currentMovement.z + dir.z * pullStrength;


                Vec3 finalVel = new Vec3(newX, newY, newZ);
                if (finalVel.length() > 0.6) {
                    finalVel = finalVel.normalize().scale(0.9);
                }

                player.setDeltaMovement(finalVel);
                player.hurtMarked = true;
                player.fallDistance = 0;

                if (distance < 3.0) this.discard();
            }
        }
        else if (returning) {

            Vec3 returnVec = owner.position().add(0, owner.getBbHeight() * 0.5, 0).subtract(this.position()).normalize().scale(1.2);
            this.setDeltaMovement(returnVec);

            if (distance < 1.9) this.discard();
        }
        else {

            if (!this.level().isClientSide) {
                HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS) {
                    this.onHit(hitresult);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.attached = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof ItemEntity item) {
            this.caughtEntity = item;
            this.returning = true;
        } else if (result.getEntity() != this.getOwner()) {
            this.returning = true;
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {}
}