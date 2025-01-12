package com.nationsandkings.blocks;

import com.nationsandkings.NationsAndKings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = new Identifier(NationsAndKings.MOD_ID, name);


        // Only used if we want to register the item - I think most of the time we will but who knows
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    //Registering blocks here
    public static final Block TOWN_HALL = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL)),
            "town_hall",
            true
    );

    public static void initialize() {}
}
