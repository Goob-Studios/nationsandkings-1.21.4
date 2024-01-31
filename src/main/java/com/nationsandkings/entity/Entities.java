package com.nationsandkings.entity;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {

    public static final EntityType<GenericVillagerEntity> GENERIC_VILLAGER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(NationsAndKings.MOD_ID, "genericvillager"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GenericVillagerEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 2f)).build());

    public static void RegisterModEntities(){
        NationsAndKings.LOGGER.info("Registering entities for " + NationsAndKings.MOD_ID);
        FabricDefaultAttributeRegistry.register(Entities.GENERIC_VILLAGER, GenericVillagerEntity.createGenericVillagerAttributes());
    }
}
