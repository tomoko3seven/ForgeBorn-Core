package com.sqvizers.forgeborn.common.worldgen.tree.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sqvizers.forgeborn.common.worldgen.tree.FBTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.tags.BlockTags;

import java.util.List;
import java.util.function.BiConsumer;

public class DreamtreeTrunkPlacer extends TrunkPlacer {
    public static final Codec<DreamtreeTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            trunkPlacerParts(instance).apply(instance, DreamtreeTrunkPlacer::new));

    public DreamtreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return FBTrunkPlacerTypes.DREAM_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter,
                                                            RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                setDirtAt(level, blockSetter, random, pos.offset(x, -1, z), config);
            }
        }

        int height = freeTreeHeight + random.nextInt(heightRandA, heightRandA + 5);
        List<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();

        for (int i = 0; i < height; i++) {

            placeLog(level, blockSetter, random, pos.above(i), config);
            placeLog(level, blockSetter, random, pos.above(i).east(), config);
            placeLog(level, blockSetter, random, pos.above(i).south(), config);
            placeLog(level, blockSetter, random, pos.above(i).east().south(), config);


            if (i < 4) {
                int rootDist = 4 - i;
                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    for (int j = 1; j <= rootDist; j++) {
                        BlockPos rootPos = pos.above(i).relative(dir, j);
                        Direction.Axis axis = dir.getAxis();

                        placeHorizontalLog(level, blockSetter, random, rootPos, config, axis);
                        if (i == 0) placeLog(level, blockSetter, random, rootPos.below(), config);
                    }
                }
            }

            if (i > height / 2 && i < height - 2 && random.nextFloat() < 0.25f) {
                Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                int branchLen = random.nextInt(2, 5);
                BlockPos branchPos = pos.above(i).relative(dir, 2);

                for (int k = 0; k < branchLen; k++) {
                    BlockPos p = branchPos.relative(dir, k).above(k / 2);
                    placeHorizontalLog(level, blockSetter, random, p, config, dir.getAxis());
                    if (k == branchLen - 1) {
                        attachments.add(new FoliagePlacer.FoliageAttachment(p.above(), 0, false));
                    }
                }
            }
        }

        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(height), 2, false));
        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(height).east().south(), 2, false));

        return attachments;
    }

    private void placeHorizontalLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter,
                                    RandomSource random, BlockPos pos, TreeConfiguration config, Direction.Axis axis) {
        if (level.isStateAtPosition(pos, (state) -> state.isAir() || state.is(BlockTags.REPLACEABLE_BY_TREES))) {
            blockSetter.accept(pos, config.trunkProvider.getState(random, pos).setValue(RotatedPillarBlock.AXIS, axis));
        }
    }
}