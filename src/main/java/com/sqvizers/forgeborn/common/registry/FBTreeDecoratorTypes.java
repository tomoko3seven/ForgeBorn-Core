package com.sqvizers.forgeborn.common.registry;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.worldgen.tree.custom.LivingTreeRootDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FBTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> DECORATOR_TYPES =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, ForgeBorn.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<LivingTreeRootDecorator>> LIVING_TREE_ROOT =
            DECORATOR_TYPES.register("living_tree_root", () -> new TreeDecoratorType<>(LivingTreeRootDecorator.CODEC));

    public static void register(IEventBus eventBus) {
        DECORATOR_TYPES.register(eventBus);
    }
}