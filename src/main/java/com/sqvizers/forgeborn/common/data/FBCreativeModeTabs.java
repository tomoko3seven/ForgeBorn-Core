package com.sqvizers.forgeborn.common.data;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;

import net.minecraft.world.item.CreativeModeTab;

import com.sqvizers.forgeborn.ForgeBorn;
import com.tterrag.registrate.util.entry.RegistryEntry;

import static com.sqvizers.forgeborn.common.registry.FBRegistration.REGISTRATE;

public class FBCreativeModeTabs {

    public static RegistryEntry<CreativeModeTab> FORGEBORN = REGISTRATE.defaultCreativeTab(ForgeBorn.MOD_ID,
            builder -> builder
                    .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(ForgeBorn.MOD_ID, REGISTRATE))
                    .title(REGISTRATE.addLang("itemGroup", ForgeBorn.id("creative_tab"), "ForgeBorn: Core"))
                    .icon(FBItems.HOOK_ARM::asStack)
                    .build())
            .register();

    public static void init() {}
}
