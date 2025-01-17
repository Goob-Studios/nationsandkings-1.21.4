package com.nationsandkings.mixin;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieAttackGenericVillagerMixin {
    @Inject(at = @At("HEAD"), method = "initCustomGoals")
    private void init(CallbackInfo info) {
        ZombieEntity zombie = (ZombieEntity) (Object) this;
        GoalSelector goalSelector = ((goalSelectorAccessor) zombie).getGoalSelector();
        goalSelector.add(3, new ActiveTargetGoal(zombie, GenericVillagerEntity.class, true));
    }
}
