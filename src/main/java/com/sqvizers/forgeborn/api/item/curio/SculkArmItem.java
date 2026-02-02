package com.sqvizers.forgeborn.api.item.curio;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class SculkArmItem extends Item implements ICurioItem {
    public SculkArmItem() {
        super(new Item.Properties().stacksTo(1));
    }
}