package com.sqvizers.forgeborn.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBEntities;
import com.sqvizers.forgeborn.network.PacketHandler;
import com.sqvizers.forgeborn.network.item.arms.ArmAbilityPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import com.sqvizers.forgeborn.client.renderer.HookRenderer;

@Mod.EventBusSubscriber(modid = ForgeBorn.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    public static final KeyMapping ARM_ABILITY_KEY = new KeyMapping(
            "key.forgeborn.arm_ability",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.forgeborn"
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ARM_ABILITY_KEY);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FBEntities.HOOK_ENTITY.get(), HookRenderer::new);
    }
}

