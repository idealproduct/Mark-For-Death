package com.yichenxbohan.markedfordeath;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = markedfordeath.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec CONFIG;

    public static final ForgeConfigSpec.BooleanValue WhetherMeteorSummonedByPlayerCanHurtPlayerConfig;
    public static final ForgeConfigSpec.BooleanValue WhetherMeteorCanHurtCreativeAndSpectatorConfig;

    static{
        BUILDER.push("General Settings");

        WhetherMeteorSummonedByPlayerCanHurtPlayerConfig = BUILDER
                .comment("Whether Meteor Summoned By Player Can Hurt Player")
                .define("Player's meteor can hurt player", true);

        WhetherMeteorCanHurtCreativeAndSpectatorConfig = BUILDER
                .comment("Whether Meteor Can Hurt Creative And Spectator")
                .define("Meteor can hurt Creative and Spectator", true);


        BUILDER.pop();

        CONFIG = BUILDER.build();
    }

    public static boolean WhetherMeteorSummonedByPlayerCanHurtPlayer;
    public static boolean WhetherMeteorCanHurtCreativeAndSpectator;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        WhetherMeteorSummonedByPlayerCanHurtPlayer = WhetherMeteorSummonedByPlayerCanHurtPlayerConfig.get();
        WhetherMeteorCanHurtCreativeAndSpectator = WhetherMeteorCanHurtCreativeAndSpectatorConfig.get();
    }
}
