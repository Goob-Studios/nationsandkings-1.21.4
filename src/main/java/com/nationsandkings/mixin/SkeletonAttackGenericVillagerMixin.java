package com.nationsandkings.mixin;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.AbstractSkeletonEntity;

import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)

public class SkeletonAttackGenericVillagerMixin {
    @Inject(at = @At("HEAD"), method = "initGoals")
    private void init(CallbackInfo info) {
        SkeletonEntity skeleton = (SkeletonEntity) (Object) this;
        GoalSelector goalSelector = ((goalSelectorAccessor) skeleton).getGoalSelector();
        goalSelector.add(3, new ActiveTargetGoal(skeleton, GenericVillagerEntity.class, true));
    }
}
