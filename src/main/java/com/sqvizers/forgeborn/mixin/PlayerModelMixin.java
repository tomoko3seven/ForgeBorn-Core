package com.sqvizers.forgeborn.mixin;

import com.sqvizers.forgeborn.utils.Arm.ArmUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {

    public PlayerModelMixin(ModelPart pRoot) {
        super(pRoot);
    }

    @Shadow @Final public ModelPart leftSleeve;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void hideLeftArmAndSleeve(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        boolean hasArm = !ArmUtils.getEquippedArm(entity).isEmpty();

        this.leftArm.visible = hasArm;
        this.leftSleeve.visible = hasArm;
    }
}