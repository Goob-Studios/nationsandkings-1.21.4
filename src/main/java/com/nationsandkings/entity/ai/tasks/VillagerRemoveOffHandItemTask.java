package com.nationsandkings.entity.ai.tasks;

import com.nationsandkings.entity.custom.GenericVillagerBrain;
import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.item.Items;

public class VillagerRemoveOffHandItemTask {

    public VillagerRemoveOffHandItemTask(){
    }

        public static Task<GenericVillagerEntity> create() {
            return TaskTriggerer.task((context) -> {
                return context.group(context.queryMemoryAbsent(MemoryModuleType.ADMIRING_ITEM)).apply(context, (admiringItem) -> {
                    return (world, entity, time) -> {
                        if (!entity.getOffHandStack().isEmpty() && !entity.getOffHandStack().isOf(Items.SHIELD)) {
                            GenericVillagerBrain.consumeOffHandItem(world, entity, true);
                            return true;
                        } else {
                            return false;
                        }
                    };
                });
            });
        }


}
