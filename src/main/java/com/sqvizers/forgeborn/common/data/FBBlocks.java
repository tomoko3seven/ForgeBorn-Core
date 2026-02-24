package com.sqvizers.forgeborn.common.data;

import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.worldgen.tree.custom.DreamTreeGrower;
import com.sqvizers.forgeborn.common.worldgen.tree.custom.LivingTreeGrower;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.function.Supplier;

import static com.sqvizers.forgeborn.common.registry.FBRegistration.REGISTRATE;

public class FBBlocks {

    // Mushrooms
    public static final BlockEntry<FlowerBlock> SULFURIC_MUSHROOM = REGISTRATE
            .block("sulfuric_mushroom", p -> new FlowerBlock(() -> MobEffects.WEAKNESS, 0, p))
            .initialProperties(() -> Blocks.BROWN_MUSHROOM)
            .properties(p -> p.noCollission().instabreak())
            .blockstate((c, p) -> p.simpleBlock(c.get(),
                    p.models().cross(c.getName(), p.modLoc("block/" + c.getName())).renderType("cutout")))
            .item()
            .build()
            .register();
    // Saplings
    public static final BlockEntry<SaplingBlock> LIVINGTREE_SAPLING = REGISTRATE.block("livingtree_sapling",
            p -> new SaplingBlock(new LivingTreeGrower(), p))
            .initialProperties(() -> Blocks.OAK_SAPLING)
            .item()
            .build()
            .register();
    public static final BlockEntry<SaplingBlock> DREAMTREE_SAPLING = REGISTRATE.block("dreamtree_sapling",
            p -> new SaplingBlock(new DreamTreeGrower(), p))
            .initialProperties(() -> Blocks.OAK_SAPLING)
            .item()
            .build()
            .register();

    public static final BlockEntry<LeavesBlock> LIVINGTREE_LEAVES = REGISTRATE.block("livingtree_leaves",
            p -> new LeavesBlock(p))
            .initialProperties(() -> Blocks.OAK_LEAVES)
            .item()
            .build()
            .register();
    public static final BlockEntry<LeavesBlock> DREAMTREE_LEAVES = REGISTRATE.block("dreamtree_leaves",
            p -> new LeavesBlock(p))
            .initialProperties(() -> Blocks.OAK_LEAVES)
            .item()
            .build()
            .register();

    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }

    public static final BlockEntry<Block> MANASTEEL_CASING = createCasingBlock("manasteel_casing",
            ForgeBorn.id("block/casings/manasteel_casing"));

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static void init() {}
}
