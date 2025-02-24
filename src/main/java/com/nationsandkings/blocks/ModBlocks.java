package com.nationsandkings.blocks;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;


public class ModBlocks {



    //Fabric Block Documentation: https://docs.fabricmc.net/develop/blocks/first-block
    // This is just for basic blocks, and not block entities. For now I'm implementing the work stations as a basic blocks

    //The block name and shouldRegisterItem are all null for some reason, despite them being passed in below.
    //String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean shouldRegisterItem
    private static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem) {
//        NationsAndKings.LOGGER.info("BLOCK: " + block);
//        NationsAndKings.LOGGER.info("Name: " + name);
//        NationsAndKings.LOGGER.info("ShouldRegisterItem: " + shouldRegisterItem);
//        //They have a factory instead of an Identifier
//        Identifier id = Identifier.of(NationsAndKings.MOD_ID, name);


        // Only used if we want to register the item - I think most of the time we will but who knows
        //This is different than the blocks class but that's because I think they do it in the items class
        if (shouldRegisterItem) {
            NationsAndKings.LOGGER.info("Should be registering item here");
//            BlockItem blockItem = new BlockItem(block, new Item.Settings());
//            Registry.register(Registries.ITEM, id, blockItem);
//            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
//                    .register((itemGroup) -> itemGroup.add(blockItem));
//            final RegistryKey<BlockItem> blockItem = RegistryKey.of()
//            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
//                entries.add(TOWN_HALL.asItem());
//            });
//            final RegistryKey<BlockItem> = RegistryKey.of()
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);

        }
        //This is the same as the built-in class
//        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(NationsAndKings.MOD_ID, name));
//        return Blocks.register(registryKey, factory, settings);
        return Registry.register(Registries.BLOCK, blockKey, block);
    }


    //Registering blocks here

    //First key
    public static final RegistryKey<Block> TOWN_HALL_KEY = RegistryKey.of(
            RegistryKeys.BLOCK,
            Identifier.of(NationsAndKings.MOD_ID, "town_hall")
    );

    public static final Block TOWN_HALL = register(
            new Block(AbstractBlock.Settings.create().registryKey(TOWN_HALL_KEY).sounds(BlockSoundGroup.METAL)
                    .hardness(1.5f)),
            TOWN_HALL_KEY,
            true
    );

//    public static final Block TOWN_HALL = register(
//            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS)),
//            "town_hall",
//            true
//    );

//    public static final Block TOWN_HALL = register("town_hall", Block::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL), true);

//    public static final Block BUTCHER_BLOCK = register("butcher_block", Block::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD), true);

//    public static final Block BUTCHER_BLOCK = register(
//            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS)),
//            "butcher_block",
//            true
//    );
    //Example from the built-in Blocks class
//    public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
//        Block block = (Block)factory.apply(settings.registryKey(key));
//        return (Block)Registry.register(Registries.BLOCK, key, block);
//    }



    public static void initialize() {

//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
//            entries.add(ModBlocks.TOWN_HALL.asItem());
//            entries.add(ModBlocks.BUTCHER_BLOCK);
//        });


        ItemGroupEvents.modifyEntriesEvent(ModItems.WORKSTATIONS_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.TOWN_HALL.asItem());
        });
    }
}
