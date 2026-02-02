package com.sqvizers.forgeborn.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ArmCooldownOverlay {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("forgeborn", "textures/gui/arm_overlay.png");

    public static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        ItemStack arm = ArmUtils.getEquippedArm(mc.player);

        if (arm.isEmpty()) return;

        int x = width - 50;
        int y = height - 50;


        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, 32, 32, 32, 32);

        if (!arm.isEmpty()) {
            guiGraphics.renderItem(arm, x + 8, y + 8);

            float cooldown = mc.player.getCooldowns().getCooldownPercent(arm.getItem(), partialTick);
            if (cooldown > 0) {
                String text = (int)(cooldown * 100) + "%";
                guiGraphics.drawString(mc.font, text, x + 8, y + 34, 0xFFFFFF, true);
            }
        }
    }
}