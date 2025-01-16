package com.nationsandkings.items;

import com.nationsandkings.NationsAndKings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;


public class ModItems {

    //From the fabric wiki, we can change later
    public static Item register(Item item, RegistryKey<Item> registryKey) {
        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

        // Return the registered item!
        return registeredItem;

    }

    // Start registering items here

    public static final RegistryKey<Item> COPPER_COIN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NationsAndKings.MOD_ID, "copper_coin"));
    public static final Item SUSPICIOUS_SUBSTANCE = register(
            new Item(new Item.Settings().registryKey(COPPER_COIN_KEY)),
            COPPER_COIN_KEY
    );

    public static void initialize() {

    }
}
