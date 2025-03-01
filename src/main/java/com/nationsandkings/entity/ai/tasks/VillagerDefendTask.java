package com.nationsandkings.entity.ai.tasks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;

import java.util.Map;
import java.util.function.Predicate;

public class VillagerDefendTask{

    public VillagerDefendTask(){

    }


    public static <T extends PathAwareEntity> SingleTickTask<T> create(int cooldown) {
        return create((target) -> {
            return true;
        }, cooldown);
    }

    public static <T extends PathAwareEntity> SingleTickTask<T> create(Predicate<T> targetPredicate, int cooldown) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET), context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET), context.queryMemoryAbsent(MemoryModuleType.ATTACK_COOLING_DOWN), context.queryMemoryValue(MemoryModuleType.VISIBLE_MOBS)).apply(context, (lookTarget, attackTarget, attackCoolingDown, visibleMobs) -> {
                return (world, entity, time) -> {
                    LivingEntity livingEntity = (LivingEntity)context.getValue(attackTarget);
                    if (targetPredicate.test(entity) && entity.isInAttackRange(livingEntity) && ((LivingTargetCache)context.getValue(visibleMobs)).contains(livingEntity)) {
                        lookTarget.remember(new EntityLookTarget(livingEntity, true));
                        entity.swingHand(Hand.MAIN_HAND);
                        entity.tryAttack(world, livingEntity);
                        attackCoolingDown.remember(true, (long)cooldown);
                        return true;
                    } else {
                        return false;
                    }
                };
            });
        });
    }

}
