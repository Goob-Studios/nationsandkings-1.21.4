package com.nationsandkings.datagen;

import com.nationsandkings.items.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.Model;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class NKModelProvider extends FabricModelProvider {
    public NKModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(net.minecraft.client.data.BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(net.minecraft.client.data.ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.GENERIC_VILLAGER_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("minecraft", "item/template_spawn_egg")), Optional.empty()));

    }
}
