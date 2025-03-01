package com.nationsandkings.entity.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;

public class VillagerFloatTask<T extends PathAwareEntity> extends MultiTickTask<T> {
    private final float chance;

    public VillagerFloatTask(float chance) {
        super(ImmutableMap.of());
        this.chance = chance;
    }

    public static <T extends PathAwareEntity> boolean isUnderwater(T entity) {
        return entity.isTouchingWater() && entity.getFluidHeight(FluidTags.WATER) > entity.getSwimHeight() || entity.isInLava();
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        return this.shouldRun(serverWorld, pathAwareEntity);
    }

    protected boolean shouldRun(ServerWorld serverWorld, PathAwareEntity pathAwareEntity) {
        return isUnderwater(pathAwareEntity);
    }

    protected void keepRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        if (pathAwareEntity.getRandom().nextFloat() < this.chance) {
            pathAwareEntity.getJumpControl().setActive();
        }

    }

}

