package com.nationsandkings.entity.ai;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;



public class VillagerGenericSleep extends Goal {
    private final GenericVillagerEntity entity;

    public VillagerGenericSleep(GenericVillagerEntity entity){
        this.entity = entity;
    }

    //check the sleep timing before starting
    @Override
    public boolean canStart() {
        long time = entity.getWorld().getTimeOfDay(); // Get the total world time in ticks
        long timeOfDay = time % 24000;

        return timeOfDay >= 13000;
    }

    @Override
    public boolean canStop() {
        long time = entity.getWorld().getTimeOfDay(); // Get the total world time in ticks
        long timeOfDay = time % 24000;
        return timeOfDay < 23000;
    }

    @Override
    public void start() {
        if (entity.getHomeLocation() != null){
            Path path = entity.getNavigation().findPathTo(entity.getHomeLocation(), 50);
            if (path != null){
                entity.getNavigation().startMovingAlong(path, 1.0);
                System.out.println("Starting pathing");
                if (entity.getBlockPos().equals(entity.getHomeLocation())) {
                    entity.getNavigation().stop(); // Stop moving
                    entity.clearGoalsAndTasks();
                }
            }
            else{
                entity.decreaseHappiness(5);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        wakeUp();
    }

    public void wakeUp(){
        entity.initGoals();
        entity.setIsAsleep(false);
        entity.initGoals();
    }




}
