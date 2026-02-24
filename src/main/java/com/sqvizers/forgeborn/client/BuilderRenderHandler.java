package com.sqvizers.forgeborn.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sqvizers.forgeborn.api.item.ManaBuilderItem;

import java.awt.*;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BuilderRenderHandler {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof ManaBuilderItem)) return;

        HitResult ray = mc.hitResult;
        if (ray == null || ray.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockRay = (BlockHitResult) ray;

        List<BlockPos> positions = ManaBuilderItem.getBuildPositions(
                blockRay.getBlockPos(),
                blockRay.getDirection(),
                ManaBuilderItem.getSize(stack),
                ManaBuilderItem.getMode(stack));

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();

        Vec3 cameraPos = event.getCamera().getPosition();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        VertexConsumer buffer = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());

        for (BlockPos pos : positions) {
            LevelRenderer.renderLineBox(
                    poseStack,
                    buffer,
                    pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                    1.0F, 1.0F, 1.0F, 0.8F);
        }

        poseStack.popPose();
    }
}
