package com.yichenxbohan.markedfordeath.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.yichenxbohan.markedfordeath.util.TargetUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Random;

public class TargetCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mark")

                // /mark <player>
                .then(Commands.argument("player", StringArgumentType.word())
                        .executes(context -> {
                            String playerName = StringArgumentType.getString(context, "player");
                            MinecraftServer server = context.getSource().getServer();
                            ServerPlayer target = server.getPlayerList().getPlayerByName(playerName);

                            if (target != null) {
                                context.getSource().sendSuccess(Component.literal("☠ 已指定 " + playerName + " 為目標！"), true);
                                target.sendSystemMessage(Component.literal("⚠ 你已被指定為追殺目標！"));
                                TargetUtils.markPlayerWithRedName(target);
                                TargetUtils.setTarget(target);
                                TargetUtils.teleportNearPlayer(context.getSource().getPlayerOrException(), target, 500);
                            } else {
                                context.getSource().sendFailure(Component.literal("❌ 找不到玩家：" + playerName));
                            }

                            return 1;
                        }))

                // /mark random
                .then(Commands.literal("random")
                        .executes(context -> {
                            MinecraftServer server = context.getSource().getServer();
                            List<ServerPlayer> players = server.getPlayerList().getPlayers();

                            if (players.isEmpty()) {
                                context.getSource().sendFailure(Component.literal("❌ 沒有在線玩家！"));
                                return 0;
                            }

                            ServerPlayer chosen = players.get(new Random().nextInt(players.size()));
                            context.getSource().sendSuccess(Component.literal("☠ 隨機目標是：" + chosen.getName().getString()), true);
                            chosen.sendSystemMessage(Component.literal("⚠ 你已被隨機選為追殺目標！"));
                            TargetUtils.markPlayerWithRedName(chosen);
                            TargetUtils.setTarget(chosen);
                            TargetUtils.teleportNearPlayer(context.getSource().getPlayerOrException(), chosen, 500);

                            return 1;
                        }))
                .then(Commands.literal("clear")
                        .then(Commands.argument("player", StringArgumentType.word())
                                .executes(context -> {
                                    String playerName = StringArgumentType.getString(context, "player");
                                    MinecraftServer server = context.getSource().getServer();
                                    ServerPlayer target = server.getPlayerList().getPlayerByName(playerName);

                                    if (target != null) {
                                        // 移除隊伍與紅色ID
                                        context.getSource().sendSuccess(Component.literal(" 已取消對 " + playerName + " 的追殺！"), true);
                                        target.sendSystemMessage(Component.literal(" 你不再是追殺目標了，暫時安全..."));
                                        TargetUtils.teleportNearPlayer(context.getSource().getPlayerOrException(), target, 500);
                                        TargetUtils.clearRedName(target);
                                        TargetUtils.clearTarget();
                                    } else {
                                        context.getSource().sendFailure(Component.literal("❌ 找不到玩家：" + playerName));
                                    }

                                    return 1;
                                })))

        );
    }
}
