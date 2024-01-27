package com.nationsandkings.entity.ai;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.Goal;

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

        return timeOfDay > 1300 || timeOfDay < 0;
    }

    @Override
    public boolean canStop() {
        long time = entity.getWorld().getTimeOfDay(); // Get the total world time in ticks
        long timeOfDay = time % 24000;
        return timeOfDay <= 1300 && timeOfDay >= 1000;
    }
}
