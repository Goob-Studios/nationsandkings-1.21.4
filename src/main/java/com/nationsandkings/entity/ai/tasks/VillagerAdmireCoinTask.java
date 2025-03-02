package com.nationsandkings.entity.ai.tasks;

import com.nationsandkings.entity.custom.GenericVillagerBrain;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

public class VillagerAdmireCoinTask {

    public VillagerAdmireCoinTask() {

    }

    public static Task<LivingEntity> create(int duration) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), context.queryMemoryAbsent(MemoryModuleType.ADMIRING_ITEM), context.queryMemoryAbsent(MemoryModuleType.ADMIRING_DISABLED), context.queryMemoryAbsent(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM)).apply(context, (nearestVisibleWantedItem, admiringItem, admiringDisabled, disableWalkToAdmireItem) -> {
                return (world, entity, time) -> {
                    ItemEntity itemEntity = (ItemEntity)context.getValue(nearestVisibleWantedItem);
                    if (!GenericVillagerBrain.isVillagerCurrency(itemEntity.getStack())) {
                        return false;
                    } else {
                        admiringItem.remember(true, (long)duration);
                        return true;
                    }
                };
            });
        });
    }

}
