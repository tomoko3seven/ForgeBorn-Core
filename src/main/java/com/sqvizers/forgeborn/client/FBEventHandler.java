package com.sqvizers.forgeborn.client;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.sqvizers.forgeborn.utils.Arm.ArmUtils;

@Mod.EventBusSubscriber(modid = ForgeBorn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FBEventHandler {

    @SubscribeEvent
    public static void onOffhandUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (!ArmUtils.allowsOffhand(player)) {
            if (event.getHand() == InteractionHand.OFF_HAND) {
                event.setCanceled(true);
            }
        }
    }
}