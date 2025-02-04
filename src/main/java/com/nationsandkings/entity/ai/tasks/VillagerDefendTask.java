package com.nationsandkings.entity.ai.tasks;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.Map;

public class VillagerDefendTask <E extends PathAwareEntity> extends MultiTickTask<E> {
    public VillagerDefendTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
        super(requiredMemoryState);
    }
}
