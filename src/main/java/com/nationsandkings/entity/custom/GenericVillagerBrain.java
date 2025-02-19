package com.nationsandkings.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.nationsandkings.entity.ai.tasks.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Optional;
import java.util.function.Predicate;


public class GenericVillagerBrain  {


    protected GenericVillagerBrain(){
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
        addRestActivities(brain);
        addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new VillagerFloatTask<>(0.8F),
                new VillagerUpdateLookControlTask(45, 90),
                new VillagerMoveToTargetTask(150, 250),
                new LookAroundTask(UniformIntProvider.create(0, 20), 1.0F, 1.0F, 1.0F)
//                new FleeTask<>(0.5f)
        ));


        // new UpdateLookControlTask(45, 90), new MoveToTargetTask())
        //StrollTask.create(0.5f, 5, 5)
    }


    private static void addIdleActivities(Brain<GenericVillagerEntity> brain) {
//        brain.setTaskList(Activity.IDLE, 0, ImmutableList.of(StrollTask.create(0.3f)));
//        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60)))));

        brain.setTaskList(Activity.IDLE, ImmutableList.of(
//                Pair.of(0, StrollTask.create(0.5f, 7, 7)),
                Pair.of(1, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0f, UniformIntProvider.create(30, 60))),
                Pair.of(2, LookAtMobWithIntervalTask.follow(EntityType.SHEEP, 4.0f, UniformIntProvider.create(30, 60))),
                Pair.of(3, LookAtMobWithIntervalTask.follow(EntityType.COW, 4.0f, UniformIntProvider.create(30, 60))),
                Pair.of(4, LookAtMobWithIntervalTask.follow(EntityType.PIG, 4.0f, UniformIntProvider.create(30, 60))),
                Pair.of(5, LookAtMobWithIntervalTask.follow(EntityType.CHICKEN, 4.0f, UniformIntProvider.create(30, 60))),
                Pair.of(6, new VillagerLookAroundTask(UniformIntProvider.create(10, 120), 1.0f, 0.0f, 1.0f))
        ));
    }

    private static void addPlayActivities(Brain<GenericVillagerEntity> brain){
        brain.setTaskList(Activity.PLAY, ImmutableList.of(

        ));
    }



    private static void addFightActivities(Brain<GenericVillagerEntity> brain){
        brain.setTaskList(Activity.FIGHT, 0, ImmutableList.of(
                VillagerDefendTask.create(5)), MemoryModuleType.ATTACK_TARGET);
    }

    private static void addRestActivities(Brain<GenericVillagerEntity> brain){

        //For the sleep task to work properly we'd need the memory module for home (and to store that.)
        // MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LAST_WOKEN, MemoryModuleState.REGISTERED
        brain.setTaskList(Activity.REST, 0, ImmutableList.of(new SleepTask()));
    }


    //For attacking, we can most likely use the hurt_by_entity memory





        static void updateActivities(GenericVillagerEntity villager) {
//        NationsAndKings.LOGGER.info("Attempting to update the activities.");
            //Do we need to update whenever the villager is hit? That might be why they're not fighting back.
            // We also need to work on the sleeping activity.
        villager.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.REST));
    }

    public static Optional<LookTarget> getPlayerLookTarget(LivingEntity entity) {
        Optional<PlayerEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        return optional.map(player -> new EntityLookTarget(player, true));
    }



}
