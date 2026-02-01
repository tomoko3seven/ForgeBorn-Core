package com.sqvizers.forgeborn.api.item.curio;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class TemplateArmItem extends Item implements ICurioItem {
    public TemplateArmItem() {
        super(new Item.Properties().stacksTo(1));
    }
}