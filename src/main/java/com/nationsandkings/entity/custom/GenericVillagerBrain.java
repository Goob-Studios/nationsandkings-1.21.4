package com.nationsandkings.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class GenericVillagerBrain extends LivingEntity {


    protected GenericVillagerBrain(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    protected static Brain<?> create(Brain<GenericVillagerEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));

        return brain;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    private static void addCoreActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new UpdateLookControlTask(45, 90), new MoveToTargetTask()));
    }

    private static void addIdleActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60))), Pair.of(4, new CompositeTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(Pair.of(StrollTask.createDynamicRadius(0.5F), 2), Pair.of(StrollTask.create(0.15F, false), 2))))));
    }

    @Override
    public Arm getMainArm() {
        return null;
    }

    public static void updateActivities(GenericVillagerEntity villager) {
        Brain<GenericVillagerBrain> brain = villager.getBrain();
        Activity activity = (Activity)brain.getFirstPossibleNonCoreActivity().orElse((Activity) null);
        if (activity != Activity.PLAY_DEAD) {
            brain.resetPossibleActivities(ImmutableList.of(Activity.PLAY_DEAD, Activity.FIGHT, Activity.IDLE));
            if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse((Activity) null) != Activity.FIGHT) {
                brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
            }
        }

    }
}
