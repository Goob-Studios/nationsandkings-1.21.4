package com.nationsandkings.entity.ai.tasks;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;

public class VillagerWorkGoal extends Goal {
    private final GenericVillagerEntity entity;

    public VillagerWorkGoal(GenericVillagerEntity entity){
        this.entity = entity;

    }
    @Override
    public boolean canStart() {
        if (entity.getHasJob()){
            return true;
        }
        else {
            return false;
        }
    }

}
