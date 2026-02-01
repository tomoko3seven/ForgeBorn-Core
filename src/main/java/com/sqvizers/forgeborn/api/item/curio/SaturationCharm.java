package com.sqvizers.forgeborn.api.item.curio;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class SaturationCharm extends Item implements ICurioItem {

    public SaturationCharm(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        if (entity instanceof Player player && !player.level().isClientSide) {
            if (player.tickCount % 20 == 0) {
                var foodData = player.getFoodData();
                int food = foodData.getFoodLevel();
                float saturation = foodData.getSaturationLevel();

                if (food < 20) {
                    foodData.setFoodLevel(Math.min(20, food + 1));
                } else if (saturation < 5.0F) {
                    foodData.setSaturation(Math.min(5.0F, saturation + 0.5F));
                }
            }
        }
    }
}
