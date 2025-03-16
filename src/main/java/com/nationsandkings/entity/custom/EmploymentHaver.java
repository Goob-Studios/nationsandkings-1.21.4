package com.nationsandkings.entity.custom;

import com.nationsandkings.NationsAndKings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

public interface EmploymentHaver {
    

    default String getJob(GenericVillagerEntity villager){
        
        return villager.job;
    }

    default void setJob(GenericVillagerEntity villager, String newName){

        villager.job = newName;
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

    default GlobalPos getWorkStation(GenericVillagerEntity villager){
        BlockPos villagerPos = new BlockPos((int) villager.getX(), (int) villager.getY(), (int) villager.getZ());
        if (villager.getWorld() instanceof ServerWorld serverWorld) {
            GlobalPos workStationPos = GlobalPos.create(serverWorld.getRegistryKey(), villagerPos);
            return workStationPos;
        }
        return null;
    }

    default GlobalPos getHome(GenericVillagerEntity villager){
        if(villager.getHomeLocation() == null){
            BlockPos villagerPos = new BlockPos((int) villager.getX(), (int) villager.getY(), (int) villager.getZ());

            for (int x = -5; x <= 5; x++) {
                for (int y = -5; y <= 5; y++) {
                    for (int z = -5; z <= 5; z++) {
                        BlockPos checkPos = villagerPos.add(x, y, z);
                        BlockState state = villager.getWorld().getBlockState(checkPos);
                        if (state.getBlock() instanceof BedBlock){
                            if (villager.getWorld() instanceof ServerWorld serverWorld) {
                                GlobalPos homePos = GlobalPos.create(serverWorld.getRegistryKey(), checkPos);
                                villager.setHomeLocation(homePos);
                                return homePos;
                            }
                            NationsAndKings.LOGGER.info("Found Bed");
                            x = y = z = 21;
                        }

                    }
                }
            }
        }
    return null;
    }
    
}
