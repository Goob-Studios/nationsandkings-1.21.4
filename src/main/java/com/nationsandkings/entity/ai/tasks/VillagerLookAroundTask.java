package com.nationsandkings.entity.ai.tasks;

import com.google.common.collect.ImmutableMap;
import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;

public class VillagerLookAroundTask  extends MultiTickTask<GenericVillagerEntity> {
    private final IntProvider cooldown;
    private final float maxYaw;
    private final float minPitch;
    private final float pitchRange;

    public VillagerLookAroundTask(IntProvider cooldown, float maxYaw, float minPitch, float maxPitch) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.GAZE_COOLDOWN_TICKS, MemoryModuleState.VALUE_ABSENT));
        if (minPitch > maxPitch) {
            throw new IllegalArgumentException("Minimum pitch is larger than maximum pitch! " + minPitch + " > " + maxPitch);
        } else {
            this.cooldown = cooldown;
            this.maxYaw = maxYaw;
            this.minPitch = minPitch;
            this.pitchRange = maxPitch - minPitch;
        }
    }

    protected void run(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
        Random random = pathAwareEntity.getRandom();
        float f = MathHelper.clamp(random.nextFloat() * this.pitchRange + this.minPitch, -90.0F, 90.0F);
        float g = MathHelper.wrapDegrees(pathAwareEntity.getYaw() + 2.0F * random.nextFloat() * this.maxYaw - this.maxYaw);
        Vec3d vec3d = Vec3d.fromPolar(f, g);
        pathAwareEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(pathAwareEntity.getEyePos().add(vec3d)));
        pathAwareEntity.getBrain().remember(MemoryModuleType.GAZE_COOLDOWN_TICKS, this.cooldown.get(random));
    }
}
