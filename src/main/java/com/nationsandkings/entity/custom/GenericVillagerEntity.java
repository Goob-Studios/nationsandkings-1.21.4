package com.nationsandkings.entity.custom;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.ai.VillagerGenericSleep;
import com.nationsandkings.entity.ai.VillagerWorkGoal;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class GenericVillagerEntity extends PathAwareEntity {
    
    public static final Set<Block> INTERACT_BLOCKS = null;

    private boolean hasJob;
    private int timeout = 300;

    private boolean asleep;

    //the location of each villager's home
    //which is mostly just their bed for now
    private BlockPos homeLocation;

    //max is 20, lowest is 0
    //rimworld style mood breaks?
    //If the happiness is low enough, villagers will refuse to trade. Essentially, say goodbye to
    //trading halls, at least in their current form.
    private double happiness;
    public GenericVillagerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        hasJob = false;
        happiness = 20;
        asleep = false;

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

        if(homeLocation == null){
            findHome();
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new VillagerGenericSleep(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
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

    public BlockPos getHomeLocation(){
        return homeLocation;
    }

    public void setHomeLocation(BlockPos pos){
        homeLocation = pos;
    }

    private void findHome(){
        if(getHomeLocation() == null){
            BlockPos villagerPos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());

            for (int x = -5; x <= 5; x++) {
                for (int y = -5; y <= 5; y++) {
                    for (int z = -5; z <= 5; z++) {
                        BlockPos checkPos = villagerPos.add(x, y, z);
                        BlockState state = this.getWorld().getBlockState(checkPos);
                        if (state.getBlock() instanceof BedBlock){
                            this.setHomeLocation(checkPos);
                            System.out.println("Found Pos");
                            x = y = z = 21;
                        }

                    }
                }
            }
        }

    }


}
