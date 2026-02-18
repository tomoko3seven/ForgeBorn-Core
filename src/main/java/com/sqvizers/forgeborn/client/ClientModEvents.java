package com.sqvizers.forgeborn.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.client.gui.ArmCooldownOverlay;
import com.sqvizers.forgeborn.client.renderer.MeteorRenderer;
import com.sqvizers.forgeborn.common.data.FBEntities;
import com.sqvizers.forgeborn.common.data.FBItems;
import com.sqvizers.forgeborn.utils.Arm.MechanicalArmLayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        event.registerEntityRenderer(FBEntities.METEOR.get(), MeteorRenderer::new);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (String skinType : new String[]{"default", "slim"}) {
            PlayerRenderer renderer = event.getSkin(skinType);
            if (renderer != null) {
                renderer.addLayer(new MechanicalArmLayer(renderer));
            }
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(FBItems.ANCIENT_SWORD.get(),
                    new ResourceLocation("is_open"),
                    (stack, level, entity, seed) ->
                            stack.hasTag() && stack.getTag().getBoolean("IsOpen") ? 1.0F : 0.0F
            );
        });
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "arm_cooldown", ArmCooldownOverlay::render);
    }



}

