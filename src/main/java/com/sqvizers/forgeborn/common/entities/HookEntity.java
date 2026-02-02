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

    public HookEntity(EntityType<? extends HookEntity> type, Level level) {
        super(type, level);
    }

    public HookEntity(LivingEntity owner, Level level) {
        this(FBEntities.HOOK_ENTITY.get(), level);
        this.setOwner(owner);
        Vec3 look = owner.getLookAngle();
        this.setPos(owner.getX() + look.x, owner.getEyeY() + look.y * 0.5, owner.getZ() + look.z);
        this.setDeltaMovement(look.scale(1.5));
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
                double pullStrength = 0.15;
                Vec3 currentMovement = player.getDeltaMovement();

                player.setDeltaMovement(currentMovement.add(dir.x * pullStrength, dir.y * pullStrength * 0.6, dir.z * pullStrength));
                player.hurtMarked = true;
                player.fallDistance = 0;

                if (distance < 2.5) this.discard();
            }
        }
        else if (returning) {
            Vec3 returnVec = owner.position().add(0, owner.getBbHeight() * 0.5, 0).subtract(this.position()).normalize().scale(1.2);
            this.setDeltaMovement(returnVec);
            if (distance < 1.5) this.discard();
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
        if (!this.returning) {
            this.attached = true;
            this.setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (result.getEntity() != this.getOwner()) {
            if (result.getEntity() instanceof ItemEntity) {
                this.returning = true;
            } else {
                this.returning = true;
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {}
}