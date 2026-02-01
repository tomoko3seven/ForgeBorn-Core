package com.sqvizers.forgeborn.utils.Arm;

import com.sqvizers.forgeborn.api.item.curio.HookArmItem;
import com.sqvizers.forgeborn.api.item.curio.TemplateArmItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class ArmUtils {

    public static ItemStack getEquippedArm(LivingEntity entity) {
        if (entity == null) return ItemStack.EMPTY;

        return CuriosApi.getCuriosInventory(entity).map(handler -> {
            return handler.getStacksHandler("left_arm")
                    .map(stackHandler -> {
                        for (int i = 0; i < stackHandler.getSlots(); i++) {
                            ItemStack stack = stackHandler.getStacks().getStackInSlot(i);
                            if (stack.getItem() instanceof TemplateArmItem || stack.getItem() instanceof HookArmItem) {
                                return stack;
                            }
                        }
                        return ItemStack.EMPTY;
                    }).orElse(ItemStack.EMPTY);
        }).orElse(ItemStack.EMPTY);
    }

    public static boolean hasLeftArm(LivingEntity entity) {
        return !getEquippedArm(entity).isEmpty();
    }

    public static boolean isArmEquipped(LivingEntity entity, Item armItem) {
        ItemStack equipped = getEquippedArm(entity);
        return !equipped.isEmpty() && equipped.is(armItem);
    }

    public static boolean allowsOffhand(LivingEntity entity) {
        ItemStack arm = getEquippedArm(entity);
        return !arm.isEmpty() && arm.getItem() instanceof TemplateArmItem;
    }
}