package com.nationsandkings.mixin;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public class CreeperAttackGenericVillagerMixin {
    @Inject(at = @At("HEAD"), method = "initGoals")
    private void init(CallbackInfo info) {
        CreeperEntity creeper = (CreeperEntity) (Object) this;
        GoalSelector goalSelector = ((goalSelectorAccessor) creeper).getGoalSelector();
        goalSelector.add(1, new ActiveTargetGoal(creeper, GenericVillagerEntity.class, true));
    }
}
