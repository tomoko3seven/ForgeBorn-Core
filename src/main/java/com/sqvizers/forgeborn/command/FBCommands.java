package com.sqvizers.forgeborn.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.brigadier.CommandDispatcher;
import com.sqvizers.forgeborn.common.data.FBEntities;
import com.sqvizers.forgeborn.common.entities.MeteorEntity;

@Mod.EventBusSubscriber(modid = "forgeborn", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FBCommands {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("meteor")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    Level level = player.level();

                    double x = player.getX() + (level.random.nextDouble() - 0.5) * 50;
                    double z = player.getZ() + (level.random.nextDouble() - 0.5) * 50;
                    double y = 250;

                    MeteorEntity meteor = new MeteorEntity(FBEntities.METEOR.get(), level);
                    meteor.setPos(x, y, z);
                    level.addFreshEntity(meteor);

                    context.getSource().sendSuccess(() -> Component.literal("Meteorite is falling!"), true);
                    return 1;
                }));
    }
}
