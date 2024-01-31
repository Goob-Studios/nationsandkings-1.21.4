package com.nationsandkings;

import com.nationsandkings.entity.Entities;
import com.nationsandkings.entity.GenericVillagerRenderer;
import com.nationsandkings.entity.ModModelLayers;
import com.nationsandkings.entity.villager_pillager_generic_model;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class NationsAndKingsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		EntityRendererRegistry.register(Entities.GENERIC_VILLAGER, GenericVillagerRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GENERIC_VILLAGER, villager_pillager_generic_model::getTexturedModelData);
	}
}