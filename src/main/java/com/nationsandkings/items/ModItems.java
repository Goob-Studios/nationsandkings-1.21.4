package com.nationsandkings.items;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.blocks.ModBlocks;
import com.nationsandkings.entity.Entities;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;


public class ModItems {

    //From the fabric wiki, we can change later
    public static Item register(Item item, RegistryKey<Item> registryKey) {

        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

        return registeredItem;

    }

    // Start registering items here

    public static final RegistryKey<Item> COPPER_COIN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NationsAndKings.MOD_ID, "copper_coin"));
    public static final Item COPPER_COINS = register(
            new Item(new Item.Settings().registryKey(COPPER_COIN_KEY)),
            COPPER_COIN_KEY
    );

    //0xffffff
    //0xffdd00


    // Spawn Eggs


    public static final RegistryKey<Item> GENERIC_VILLAGER_SPAWN_EGG_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NationsAndKings.MOD_ID, "generic_villager_spawn_egg"));

    public static final SpawnEggItem GENERIC_VILLAGER_SPAWN_EGG = (SpawnEggItem) register(
        new SpawnEggItem(Entities.GENERIC_VILLAGER ,new Item.Settings().registryKey(GENERIC_VILLAGER_SPAWN_EGG_KEY)),
            GENERIC_VILLAGER_SPAWN_EGG_KEY
    );

    //Group Keys

    public static final RegistryKey<ItemGroup> WORKSTATIONS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(NationsAndKings.MOD_ID, "nak_workstations"));
    public static final ItemGroup WORKSTATIONS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.TOWN_HALL.asItem()))
            .displayName(Text.translatable("nak_workstations.nationsandkings"))
            .build();

    public static final RegistryKey<ItemGroup> DECORATIVE_ITEMS_BLOCKS_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(NationsAndKings.MOD_ID, "nak_decorations"));
    public static final ItemGroup DECORATIVE_ITEMS_BLOCKS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.OAK_LEAVES))
            .displayName(Text.translatable("nak_decorations.nationsandkings"))
            .build();

    public static final RegistryKey<ItemGroup> COINS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(NationsAndKings.MOD_ID, "nak_coins"));
    public static final ItemGroup COINS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.EMERALD))
            .displayName(Text.translatable("nak_coins"))
            .build();

    //Item Tags



    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, WORKSTATIONS_GROUP_KEY, WORKSTATIONS_GROUP);
        Registry.register(Registries.ITEM_GROUP, DECORATIVE_ITEMS_BLOCKS_KEY, DECORATIVE_ITEMS_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, COINS_GROUP_KEY, COINS_GROUP);

        //Adding Items to the decorative group
        ItemGroupEvents.modifyEntriesEvent(DECORATIVE_ITEMS_BLOCKS_KEY).register(itemGroup -> {
            itemGroup.add(Items.VINE);
        });

        //Adding Items to the COINS_GROUP
        ItemGroupEvents.modifyEntriesEvent(COINS_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.COPPER_COINS);
//            itemGroup.add(ModItems.GENERIC_VILLAGER_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(itemGroup -> {
            itemGroup.add(ModItems.GENERIC_VILLAGER_SPAWN_EGG);
        });








    }
}
