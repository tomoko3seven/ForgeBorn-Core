package com.sqvizers.forgeborn.utils.Arm;

import com.sqvizers.forgeborn.api.item.curio.HookArmItem;
import com.sqvizers.forgeborn.api.item.curio.SculkArmItem;
import com.sqvizers.forgeborn.api.item.curio.TemplateArmItem;
import com.sqvizers.forgeborn.common.data.FBItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class ArmUtils {

    public static boolean isArmEquipped(LivingEntity entity, Item armItem) {
        return CuriosApi.getCuriosInventory(entity).map(handler -> {
            for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    if (stackHandler.getStackInSlot(i).is(armItem)) {
                        return true;
                    }
                }
            }
            return false;
        }).orElse(false);
    }

    public static ItemStack getEquippedArm(LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity).map(handler -> {
            for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (stack.getItem() instanceof TemplateArmItem ||
                            stack.getItem() instanceof HookArmItem ||
                            stack.getItem() instanceof SculkArmItem) {
                        return stack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    public static boolean allowsOffhand(LivingEntity entity) {
        ItemStack arm = getEquippedArm(entity);

        if (arm.isEmpty()) return true;

        Item item = arm.getItem();
        return item == FBItems.TEMPLATE_ARM.get();
    }

    public static ResourceLocation getArmTexture(ItemStack stack) {
        if (stack.isEmpty()) return null;

        Item item = stack.getItem();
        String prefix = "textures/entity/arm/";

        if (item == FBItems.HOOK_ARM.get()) {
            return new ResourceLocation("forgeborn", prefix + "hook_arm.png");
        }

        if (item == FBItems.SCULK_ARM.get()) {
            return new ResourceLocation("forgeborn", prefix + "sculk_arm.png");
        }

        if (item instanceof TemplateArmItem) {
            return new ResourceLocation("forgeborn", prefix + "mana_arm.png");
        }

        return null;
    }
}