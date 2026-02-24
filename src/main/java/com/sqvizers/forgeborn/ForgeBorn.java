package com.sqvizers.forgeborn;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.sound.SoundEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.sqvizers.forgeborn.common.data.*;
import com.sqvizers.forgeborn.common.data.datagen.FBDatagen;
import com.sqvizers.forgeborn.common.machine.multiblock.MultiblockInit;
import com.sqvizers.forgeborn.common.machine.simple.SimpleMachines;
import com.sqvizers.forgeborn.common.registry.FBRegistration;
import com.sqvizers.forgeborn.common.registry.FBTreeDecoratorTypes;
import com.sqvizers.forgeborn.common.worldgen.dimension.FBDimensions;
import com.sqvizers.forgeborn.common.worldgen.tree.FBTrunkPlacerTypes;
import com.sqvizers.forgeborn.gtbridge.FBRecipeTypes;
import com.sqvizers.forgeborn.network.PacketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ForgeBorn.MOD_ID)
public class ForgeBorn {

    public static final String MOD_ID = "forgeborn", NAME = "ForgeBorn";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static MaterialRegistry MATERIAL_REGISTRY;

    public ForgeBorn() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ForgeBorn.init(bus);

        FBDimensions.register();
        FBTrunkPlacerTypes.register(bus);
        FBTreeDecoratorTypes.DECORATOR_TYPES.register(bus);

        bus.addListener(this::commonSetup);
        bus.register(this);
        bus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        bus.addGenericListener(MachineDefinition.class, this::registerMachines);
    }

    public static void init(IEventBus bus) {
        FBCreativeModeTabs.init();
        FBItems.init();
        FBRegistration.REGISTRATE.registerRegistrate();
        FBDatagen.init();
        FBBlocks.init();

        FBEntities.REGISTRY.register(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @SubscribeEvent
    public void registerMaterialRegistry(MaterialRegistryEvent event) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(ForgeBorn.MOD_ID);
    }

    @SubscribeEvent
    public void registerMaterials(MaterialEvent event) {
        FBMaterials.init();
    }

    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        FBRecipeTypes.init();
    }

    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        MultiblockInit.init();
        SimpleMachines.init();
    }

    public void registerSounds(GTCEuAPI.RegisterEvent<ResourceLocation, SoundEntry> event) {
        // CustomSounds.init();
    }
}
