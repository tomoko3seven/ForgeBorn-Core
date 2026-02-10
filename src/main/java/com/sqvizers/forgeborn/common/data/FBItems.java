package com.sqvizers.forgeborn.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.registry.GTRegistration;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.api.item.curio.HookArmItem;
import com.sqvizers.forgeborn.api.item.curio.SculkArmItem;
import com.sqvizers.forgeborn.api.item.curio.TemplateArmItem;
import com.sqvizers.forgeborn.api.item.weapon.AncientSwordItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

import static com.sqvizers.forgeborn.common.registry.FBRegistration.REGISTRATE;

public class FBItems {

    static {
        GTRegistration.REGISTRATE.creativeModeTab(() -> FBCreativeModeTabs.FORGEBORN);
    }

    //Arms
    public static final ItemEntry<TemplateArmItem> TEMPLATE_ARM = REGISTRATE
            .item("template_arm", p -> new TemplateArmItem())
            .lang("Template Arm")
            .properties(p -> p.stacksTo(1)) // Теперь должно работать
            .register();
    public static final ItemEntry<SculkArmItem> SCULK_ARM = REGISTRATE
            .item("sculk_arm", p -> new SculkArmItem())
            .lang("Echo-Blaster Arm")
            .properties(p -> p.stacksTo(1)) // Теперь должно работать
            .register();
    public static final ItemEntry<HookArmItem> HOOK_ARM = REGISTRATE
            .item("hook_arm", HookArmItem::new)
            .lang("Hook Arm")
            .properties(p -> p.stacksTo(1))
            .tag(TagKey.create(Registries.ITEM, new ResourceLocation("curios", "left_arm")))
            .register();
    //Swords
    public static final ItemEntry<AncientSwordItem> ANCIENT_SWORD = REGISTRATE
            .item("ancient_sword", AncientSwordItem::new)
            .lang("Ancient Sword")
            .properties(p -> p.stacksTo(1).durability(1150))
            .register();

    public static <T extends ComponentItem> NonNullConsumer<T> attach(IItemComponent... components) {
        return item -> item.attachComponents(components);
    }

    public static <T extends Item> NonNullConsumer<T> modelPredicate(ResourceLocation predicate,
                                                                     Function<ItemStack, Float> property) {
        return item -> {
            if (GTCEu.isClientSide()) {
                ItemProperties.register(item, predicate, (itemStack, c, l, i) -> property.apply(itemStack));
            }
        };
    }

    public static void init() {}
}
