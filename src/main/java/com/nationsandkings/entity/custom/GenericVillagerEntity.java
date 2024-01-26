package com.nationsandkings.entity.custom;

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
import net.minecraft.world.World;

import java.util.Set;

public class GenericVillagerEntity extends PathAwareEntity {
    
    public static final Set<Block> INTERACT_BLOCKS = null;

    private boolean hasJob;
    public GenericVillagerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        hasJob = false;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.5d));
        this.goalSelector.add(2, new VillagerWorkGoal(this));
        this.goalSelector.add(3, new LookAroundGoal(this));


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
}
