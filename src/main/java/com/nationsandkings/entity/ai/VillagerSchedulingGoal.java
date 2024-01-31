package com.nationsandkings.entity.ai;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.Goal;

//this is the goal for scheduling.
// Villagers will have a random timing to complete tasks, but
// for now I'm doing specific timings for wake up on each entity

public class VillagerSchedulingGoal extends Goal {

    private final GenericVillagerEntity entity;

    public VillagerSchedulingGoal(GenericVillagerEntity entity){
        this.entity = entity;
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void tick() {
        if(entity.getWorld().getTimeOfDay() == entity.getSleepTime()){
            System.out.println("Time for Sleep");
        }
    }
}
