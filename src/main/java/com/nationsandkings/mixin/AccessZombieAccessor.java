package com.nationsandkings.mixin;


import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin (MobEntity.class)
public interface AccessZombieAccessor {
    @Accessor
    GoalSelector getGoalSelector();
}
