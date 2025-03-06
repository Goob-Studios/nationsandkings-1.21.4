package com.nationsandkings.entity.custom;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;

public interface EmploymentHaver {
    
    String job = "farmer";
    
    public default String getJob(){
        
        return job;
    }

    public default void setJob(String newName){

        newName = job;
    }

    public default LootTable getJobLoot(String jobName, GenericVillagerEntity villager){
        LootTable lootTable = null;
        switch (jobName){
            case "farmer":
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.VILLAGE_PLAINS_CHEST);
                break;
            case "woodcutter":
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.SPAWN_BONUS_CHEST);
                break;
            case "mason":
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.VILLAGE_MASON_CHEST);
                break;
            case "butcher":
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.VILLAGE_BUTCHER_CHEST);
                break;
            case "fisherman":
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.VILLAGE_FISHER_CHEST);
                break;
            case "builder":
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT_GAMEPLAY);
                break;
            default:
                lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.UNDERWATER_RUIN_SMALL_CHEST);
        }
        return lootTable;
    }
    
}
