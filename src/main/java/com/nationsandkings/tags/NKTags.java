package com.nationsandkings.tags;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class NKTags {
    public static final TagKey<Item> VILLAGER_CURRENCY = TagKey.of(RegistryKeys.ITEM, Identifier.of("nationsandkings", "villager_currency"));
}
