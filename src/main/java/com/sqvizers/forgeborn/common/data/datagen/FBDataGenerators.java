package com.sqvizers.forgeborn.common.data.datagen;

import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.sqvizers.forgeborn.ForgeBorn;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public class FBDataGenerators {

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            var registries = event.getLookupProvider();
            if (event.includeClient()) {
                generator.addProvider(true, new SoundEntryBuilder.SoundEntryProvider(packOutput, ForgeBorn.MOD_ID));
            }
            /*generator.addProvider(event.includeServer(), new FBRecipeProvider(packOutput));
            generator.addProvider(event.includeServer(), FBLootTableProvider.create(packOutput));

            generator.addProvider(event.includeClient(), new FBBlockStateProvider(packOutput, existingFileHelper));
            generator.addProvider(event.includeClient(), new FBItemModelProvider(packOutput, existingFileHelper));

            ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                    new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
            generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

            generator.addProvider(event.includeServer(), new ModGlobalLootModifiersProvider(packOutput));
            generator.addProvider(event.includeServer(), new ModPoiTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

            generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));*/
        }
    }