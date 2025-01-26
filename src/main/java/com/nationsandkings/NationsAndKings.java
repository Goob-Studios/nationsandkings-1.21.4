package com.nationsandkings;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.nationsandkings.blocks.ModBlocks;
import com.nationsandkings.entity.Entities;
import com.nationsandkings.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.network.message.SentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NationsAndKings implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("nationsandkings");

	//just a string to get the mod id from instead of typing it out every time
	public static final String MOD_ID = "nationsandkings";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		LOGGER.info("Registering Mod Items & Item Groups");
		ModItems.initialize();

		LOGGER.info("Registering Mod Entities");
		Entities.RegisterModEntities();

		LOGGER.info("Registering Mod Blocks");
		ModBlocks.initialize();
	}

}