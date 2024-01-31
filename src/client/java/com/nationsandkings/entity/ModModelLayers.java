package com.nationsandkings.entity;

import com.nationsandkings.NationsAndKings;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer GENERIC_VILLAGER =
            new EntityModelLayer(new Identifier(NationsAndKings.MOD_ID, "genericvillager"), "main");
}
