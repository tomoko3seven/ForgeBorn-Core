package com.sqvizers.forgeborn.common.worldgen.tree.custom;

import com.mojang.serialization.Codec;
import com.sqvizers.forgeborn.common.registry.FBTreeDecoratorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.List;

public class LivingTreeRootDecorator extends TreeDecorator {
    public static final LivingTreeRootDecorator INSTANCE = new LivingTreeRootDecorator();
    public static final Codec<LivingTreeRootDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected TreeDecoratorType<?> type() {
        return FBTreeDecoratorTypes.LIVING_TREE_ROOT.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        if (logs.isEmpty()) return;

        BlockPos basePos = logs.get(0);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos sidePos = basePos.relative(direction);

            if (context.isAir(sidePos) || context.level().isStateAtPosition(sidePos, state -> state.canBeReplaced())) {
                placeLog(context, sidePos);
            }
            if (context.isAir(sidePos.above()) || context.level().isStateAtPosition(sidePos.above(), state -> state.canBeReplaced())) {
                if (random.nextFloat() < 0.7f) {
                    placeLog(context, sidePos.above());
                }
            }

            placeRootDown(context, sidePos.below());
        }
    }

    private void placeRootDown(Context context, BlockPos pos) {
        LevelSimulatedReader level = context.level();
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        int maxDepth = 3 + context.random().nextInt(3);

        for (int i = 0; i < maxDepth; i++) {
            if (!level.isStateAtPosition(mutablePos, state -> state.canBeReplaced() || state.is(BlockTags.FLOWERS))) {
                break;
            }

            placeLog(context, mutablePos);
            mutablePos.move(Direction.DOWN);
        }
    }

    private void placeLog(Context context, BlockPos pos) {
        context.setBlock(pos, BotaniaBlocks.livingwoodLog.defaultBlockState());
    }
}