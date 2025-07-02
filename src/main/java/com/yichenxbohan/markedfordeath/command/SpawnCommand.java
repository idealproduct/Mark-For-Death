package com.yichenxbohan.markedfordeath.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

public class SpawnCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("spawn")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            BlockPos spawnPos = player.getLevel().getSharedSpawnPos();

                            player.teleportTo(player.getLevel(), spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYRot(), player.getXRot());
                            player.sendSystemMessage(Component.literal("傳送回重生點"));

                            return 1;
                        })
        );
    }
}
