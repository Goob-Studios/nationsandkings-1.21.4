package com.nationsandkings.entity.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class VillagerMoveToTargetTask extends MultiTickTask<PathAwareEntity> {
    private static final int MAX_UPDATE_COUNTDOWN = 40;
    private int pathUpdateCountdownTicks;
    @Nullable
    private Path path;
    @Nullable
    private BlockPos lookTargetPos;
    private float speed;

    public VillagerMoveToTargetTask() {
        this(150, 250);
    }

    public VillagerMoveToTargetTask(int minRunTime, int maxRunTime) {
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED, MemoryModuleType.PATH, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT), minRunTime, maxRunTime);
    }

    protected boolean shouldRun(ServerWorld serverWorld, PathAwareEntity pathAwareEntity) {
        if (this.pathUpdateCountdownTicks > 0) {
            --this.pathUpdateCountdownTicks;
            return false;
        } else {
            Brain<?> brain = pathAwareEntity.getBrain();
            WalkTarget walkTarget = (WalkTarget)brain.getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).get();
            boolean bl = this.hasReached(pathAwareEntity, walkTarget);
            if (!bl && this.hasFinishedPath(pathAwareEntity, walkTarget, serverWorld.getTime())) {
                this.lookTargetPos = walkTarget.getLookTarget().getBlockPos();
                return true;
            } else {
                brain.forget(MemoryModuleType.WALK_TARGET);
                if (bl) {
                    brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
                }

                return false;
            }
        }
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        if (this.path != null && this.lookTargetPos != null) {
            Optional<WalkTarget> optional = pathAwareEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET);
            boolean bl = (Boolean)optional.map(VillagerMoveToTargetTask::isTargetSpectator).orElse(false);
            EntityNavigation entityNavigation = pathAwareEntity.getNavigation();
            return !entityNavigation.isIdle() && optional.isPresent() && !this.hasReached(pathAwareEntity, (WalkTarget)optional.get()) && !bl;
        } else {
            return false;
        }
    }

    protected void finishRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        if (pathAwareEntity.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET) && !this.hasReached(pathAwareEntity, (WalkTarget)pathAwareEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).get()) && pathAwareEntity.getNavigation().isNearPathStartPos()) {
            this.pathUpdateCountdownTicks = serverWorld.getRandom().nextInt(40);
        }

        pathAwareEntity.getNavigation().stop();
        pathAwareEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        pathAwareEntity.getBrain().forget(MemoryModuleType.PATH);
        this.path = null;
    }

    protected void run(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        pathAwareEntity.getBrain().remember(MemoryModuleType.PATH, this.path);
        pathAwareEntity.getNavigation().startMovingAlong(this.path, (double)this.speed);
    }

    protected void keepRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        Path path = pathAwareEntity.getNavigation().getCurrentPath();
        Brain<?> brain = pathAwareEntity.getBrain();
        if (this.path != path) {
            this.path = path;
            brain.remember(MemoryModuleType.PATH, path);
        }

        if (path != null && this.lookTargetPos != null) {
            WalkTarget walkTarget = (WalkTarget)brain.getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).get();
            if (walkTarget.getLookTarget().getBlockPos().getSquaredDistance(this.lookTargetPos) > 4.0 && this.hasFinishedPath(pathAwareEntity, walkTarget, serverWorld.getTime())) {
                this.lookTargetPos = walkTarget.getLookTarget().getBlockPos();
                this.run(serverWorld, pathAwareEntity, l);
            }

        }
    }

    private boolean hasFinishedPath(PathAwareEntity entity, WalkTarget walkTarget, long time) {
        BlockPos blockPos = walkTarget.getLookTarget().getBlockPos();
        this.path = entity.getNavigation().findPathTo(blockPos, 0);
        this.speed = walkTarget.getSpeed();
        Brain<?> brain = entity.getBrain();
        if (this.hasReached(entity, walkTarget)) {
            brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        } else {
            boolean bl = this.path != null && this.path.reachesTarget();
            if (bl) {
                brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            } else if (!brain.hasMemoryModule(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
            }

            if (this.path != null) {
                return true;
            }

            Vec3d vec3d = NoPenaltyTargeting.findTo((PathAwareEntity)entity, 10, 7, Vec3d.ofBottomCenter(blockPos), 1.5707963705062866);
            if (vec3d != null) {
                this.path = entity.getNavigation().findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.path != null;
            }
        }

        return false;
    }

    private boolean hasReached(PathAwareEntity entity, WalkTarget walkTarget) {
        return walkTarget.getLookTarget().getBlockPos().getManhattanDistance(entity.getBlockPos()) <= walkTarget.getCompletionRange();
    }

    private static boolean isTargetSpectator(WalkTarget target) {
        LookTarget lookTarget = target.getLookTarget();
        if (lookTarget instanceof EntityLookTarget entityLookTarget) {
            return entityLookTarget.getEntity().isSpectator();
        } else {
            return false;
        }
    }
}
