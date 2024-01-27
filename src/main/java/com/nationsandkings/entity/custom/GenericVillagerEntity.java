package com.nationsandkings.entity.custom;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.ai.VillagerGenericSleep;
import com.nationsandkings.entity.ai.VillagerWorkGoal;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.StopFollowingCustomerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

import java.util.Set;

public class GenericVillagerEntity extends PathAwareEntity {
    
    public static final Set<Block> INTERACT_BLOCKS = null;

    private boolean hasJob;
    private int timeout = 300;

    //max is 20, lowest is 0
    //rimworld style mood breaks?
    //If the happiness is low enough, villagers will refuse to trade. Essentially, say goodbye to
    //trading halls, at least in their current form.
    private double happiness;
    public GenericVillagerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        hasJob = false;
        happiness = 20;
    }

    @Override
    public void tick() {
        super.tick();


        //particle stuff
        // 20 is temporary to test if the logic works
        if(checkHappiness() == 0 && timeout == 0){
                this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
                        this.getX()+0.5,getY() , getZ() + 0.5, 0.5, 0.5, 0.5);
            this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
                    this.getX()-0.5,getY() , getZ() - 0.5, 0.5, 0.5, 0.5);
            this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
                    this.getX(),getY()+0.5, getZ(), 0.5, 0.5, 0.5);
            timeout = 300;
        }
        else{
            timeout--;
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new VillagerGenericSleep(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.5d));
        this.goalSelector.add(3, new VillagerWorkGoal(this));
        this.goalSelector.add(4, new LookAroundGoal(this));


    }

    public static DefaultAttributeContainer.Builder createGenericVillagerAttributes(){
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    public boolean getHasJob(){
        return hasJob;
    }


    private void attack(){

    }

    private double checkHappiness(){
        return happiness;
    }


}
