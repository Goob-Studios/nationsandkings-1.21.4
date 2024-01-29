package com.nationsandkings.entity.ai;

import net.minecraft.entity.ai.goal.Goal;

//this is the goal for scheduling.
//Villagers will have a random timing to complete tasks, but

public class VillagerSchedulingGoal extends Goal {
    @Override
    public boolean canStart() {
        return false;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void start() {
        super.start();
    }

}
