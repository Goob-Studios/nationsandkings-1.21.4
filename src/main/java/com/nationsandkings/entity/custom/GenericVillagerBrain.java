package com.nationsandkings.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.nationsandkings.NationsAndKings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class GenericVillagerBrain  {


    protected GenericVillagerBrain() {
    }

    protected static Brain<?> create(Brain<GenericVillagerEntity> brain) {
//        addCoreActivities(brain);
//        addIdleActivities(brain);
//        brain.setDefaultActivity(Activity.CORE);
//        brain.resetPossibleActivities();
////        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
//        NationsAndKings.LOGGER.info("Created the brain");
//        return brain;
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        NationsAndKings.LOGGER.info("Creating the brain for a villager");
        return brain;
    }

    private static void addCoreActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new UpdateLookControlTask(45, 90), new MoveToTargetTask()));
        //StrollTask.create(0.5f, 5, 5)
    }

    private static void addIdleActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.IDLE, 1, ImmutableList.of(StrollTask.create(0.3f)));
    }

    static void updateActivities(GenericVillagerEntity villager) {
        NationsAndKings.LOGGER.info("Attempting to update the activities.");
        villager.getBrain().resetPossibleActivities(ImmutableList.of(Activity.CORE, Activity.IDLE));
    }



}
