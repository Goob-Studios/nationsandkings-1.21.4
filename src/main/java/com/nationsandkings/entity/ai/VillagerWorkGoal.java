package com.nationsandkings.entity.ai;

import net.minecraft.entity.ai.goal.Goal;

public class VillagerWorkGoal extends Goal {
    @Override
    public boolean canStart() {
        return false;
    }
}
