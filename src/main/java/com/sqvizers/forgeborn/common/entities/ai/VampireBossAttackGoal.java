package com.sqvizers.forgeborn.common.entities.ai; // Проверь свой пакет!

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;

import com.sqvizers.forgeborn.common.entities.VampireBoss;

public class VampireBossAttackGoal extends Goal {

    private final VampireBoss boss;
    private int phase = 0;
    private int timer = 0;

    public VampireBossAttackGoal(VampireBoss boss) {
        this.boss = boss;
    }

    @Override
    public boolean canUse() {
        return boss.getTarget() != null;
    }

    @Override
    public void tick() {
        LivingEntity target = boss.getTarget();
        if (target == null) return;

        timer++;

        switch (phase) {
            case 0 -> {
                boss.getNavigation().moveTo(target, 1.3);
                if (boss.distanceTo(target) < 2.5) {
                    boss.doHurtTarget(target);
                    changePhase(1);
                }
            }
            case 1 -> {
                boss.getNavigation().stop();
                boss.setAggressive(true);

                if (timer % 10 == 0) {
                    double dX = target.getX() - boss.getX();
                    double dY = target.getY(0.5D) - boss.getEyeY();
                    double dZ = target.getZ() - boss.getZ();

                    SmallFireball fireball = new SmallFireball(boss.level(), boss, dX, dY, dZ);
                    fireball.setPos(boss.getX(), boss.getEyeY(), boss.getZ());
                    boss.level().addFreshEntity(fireball);
                }

                if (timer > 40) changePhase(2);
            }
            case 2 -> {
                boss.getNavigation().stop();

                if (boss.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.FLAME,
                            boss.getX(), boss.getY() + 1.0, boss.getZ(),
                            20,
                            1.5, 0.5, 1.5,
                            0.05);
                }

                if (timer > 40) {
                    boss.level().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(4.0))
                            .forEach(e -> {
                                if (e != boss) e.hurt(boss.damageSources().inFire(), 12.0f);
                            });
                    changePhase(3);
                }
            }
            case 3 -> {
                boss.getNavigation().moveTo(target, 1.5);

                if (boss.distanceTo(target) < 3.0) {
                    Vec3 movement = target.getDeltaMovement();

                    target.setDeltaMovement(movement.x, 1.2, movement.z);
                    target.hurtMarked = true;

                    if (boss.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY(), target.getZ(),
                                1, 0, 0, 0, 0);
                    }
                    changePhase(0);
                }

                if (timer > 100) changePhase(0);
            }
        }
    }

    private void changePhase(int newPhase) {
        this.phase = newPhase;
        this.timer = 0;
        boss.setAggressive(false);
    }
}
