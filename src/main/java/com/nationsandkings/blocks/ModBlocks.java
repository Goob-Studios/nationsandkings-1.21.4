package com.nationsandkings.blocks;

import com.nationsandkings.NationsAndKings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;


//public static final Block TOWN_HALL;

public class ModBlocks {

    //Fabric Block Documentation: https://docs.fabricmc.net/develop/blocks/first-block
    // This is just for basic blocks, and not block entities. For now I'm implementing the work stations as a basic blocks
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        //They have a factory instead of an Identifier
        Identifier id = Identifier.of(NationsAndKings.MOD_ID, name);


        // Only used if we want to register the item - I think most of the time we will but who knows
        //This is differnt than the blocks class but that's because I think they do it in the items class
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                    .register((itemGroup) -> itemGroup.add(blockItem));
        }
        //This is the same as the built-in class
        return Registry.register(Registries.BLOCK, id, block);
    }
    //Registering blocks here
    public static final Block TOWN_HALL = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL)),
            "town_hall",
            true
    );
    //Example from the built-in Blocks class
//    public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
//        Block block = (Block)factory.apply(settings.registryKey(key));
//        return (Block)Registry.register(Registries.BLOCK, key, block);
//    }



    public static void initialize() {

    }
}
