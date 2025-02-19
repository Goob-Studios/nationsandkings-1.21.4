package com.nationsandkings.entity.ai.tasks;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.Map;

public class VillagerConverseTask extends MultiTickTask<PathAwareEntity> {

    public VillagerConverseTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState, int minRunTime, int maxRunTime) {
        super(requiredMemoryState, minRunTime, maxRunTime);
    }
}
