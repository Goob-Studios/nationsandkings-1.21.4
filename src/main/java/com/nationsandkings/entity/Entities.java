package com.nationsandkings.entity;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Entities {

//    public static final EntityType<GenericVillagerEntity> GENERIC_VILLAGER = Registry.register(Registries.ENTITY_TYPE,
//            Identifier.of(NationsAndKings.MOD_ID, "genericvillager"),
//            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GenericVillagerEntity::new)
//                    .dimensions(EntityDimensions.fixed(1f, 2f)).build());

    public static final EntityType<GenericVillagerEntity> GENERIC_VILLAGER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(NationsAndKings.MOD_ID, "genericvillager"),
            EntityType.Builder.create(GenericVillagerEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 2f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(NationsAndKings.MOD_ID, "genericvillager"))));

    public static void RegisterModEntities(){
        NationsAndKings.LOGGER.info("Registering entities for " + NationsAndKings.MOD_ID);
        FabricDefaultAttributeRegistry.register(Entities.GENERIC_VILLAGER, GenericVillagerEntity.createGenericVillagerAttributes());
    }
}
