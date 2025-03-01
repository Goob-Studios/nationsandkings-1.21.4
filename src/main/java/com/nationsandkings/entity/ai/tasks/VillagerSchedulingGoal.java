package com.nationsandkings.entity.ai.tasks;

import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.VillagerEntity;

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

    //The function for the villagers to sleep
    //At a certain time, we'll call the sleep function, and they'll pathfind to their home location
    //then we'll play the sleeping anim

    private void sleep(){

    }


    //Right now, it's just a generic work function.
    //How we'll get the job I don't know - I'd rather not do a bunch of generic functions or do a switch statement
    //but I'd kinda have to I think
    private void work(){

    }

    private void relax(){

    }




}
