package com.nationsandkings.entity.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;

public class VillagerUpdateLookControlTask extends MultiTickTask<PathAwareEntity> {
    public VillagerUpdateLookControlTask(int minRunTime, int maxRunTime) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_PRESENT), minRunTime, maxRunTime);
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        return pathAwareEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LOOK_TARGET).filter((lookTarget) -> {
            return lookTarget.isSeenBy(pathAwareEntity);
        }).isPresent();
    }

    protected void finishRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        pathAwareEntity.getBrain().forget(MemoryModuleType.LOOK_TARGET);
    }

    protected void keepRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        pathAwareEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LOOK_TARGET).ifPresent((lookTarget) -> {
            pathAwareEntity.getLookControl().lookAt(lookTarget.getPos());
        });
    }
}
