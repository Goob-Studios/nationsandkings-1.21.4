package com.nationsandkings.entity.custom;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.ai.VillagerGenericSleep;
import com.nationsandkings.entity.ai.VillagerSchedulingGoal;
import com.nationsandkings.entity.ai.VillagerWorkGoal;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.StopFollowingCustomerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GenericVillagerEntity extends PathAwareEntity {
    
    public static final Set<Block> INTERACT_BLOCKS = null;

    private boolean hasJob;
    private int timeout = 300;

    private boolean asleep;

    //the location of each villager's home
    //which is mostly just their bed for now
    private BlockPos homeLocation;

    private String[] VillagerJobs = new String[5];


    private final SimpleInventory inventory = new SimpleInventory(30);

    //max is 20, lowest is 0
    //rimworld style mood breaks?
    //If the happiness is low enough, villagers will refuse to trade. Essentially, say goodbye to
    //trading halls, at least in their current form.

    //an integer for what time the villager needs to go to bed

    //Sleeptime is value 0
    //happiness is value 1
    private int[] VillagerArray = new int[5];



    private VillagerGenericSleep sleepGoal;

    public GenericVillagerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.2F);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void tick() {
        super.tick();


        //particle stuff
        // 20 is temporary to test if the logic works
        if(VillagerArray[1] == 0 && timeout == 0){
            //There is a better way to do this
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

        if(VillagerArray[0] == 0 || this.getWorld().getTimeOfDay() == 0){
            VillagerArray[0] = (int) ((Math.random() * (13100 - 12900)) + 12900);

        }

        if(VillagerArray[1] == 0.0){
            VillagerArray[1] = 20;
        }


    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        //targeting and the melee attack needs to be above this goal, so it can actively target the thing that
        //attacked it before it tries to sleep again.
//        sleepGoal = new VillagerGenericSleep(this);
//        this.goalSelector.add(1, sleepGoal);
        this.goalSelector.add(1, new VillagerSchedulingGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.5));
        this.goalSelector.add(3, new VillagerWorkGoal(this));
        this.goalSelector.add(4, new LookAroundGoal(this));


    }

    public void keepSleep(){
        this.goalSelector.add(0, new SwimGoal(this));
        sleepGoal = new VillagerGenericSleep(this);
        this.goalSelector.add(1, sleepGoal);
    }

    //This is where the error comes from
    public static DefaultAttributeContainer.Builder createAttributes(){
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.ATTACK_DAMAGE, 1);
    }

    public boolean getHasJob(){
        return hasJob;
    }


    private void attack(){

    }

    public void increaseHappiness(int amount){
        VillagerArray[1] = VillagerArray[1] + amount;
    }

    public void decreaseHappiness(int amount){
        VillagerArray[1] = VillagerArray[1] - amount;
    }


    public BlockPos getHomeLocation(){
        return homeLocation;
    }

    public void setHomeLocation(BlockPos pos){
        homeLocation = pos;
    }


    //This needs to be re-rewritten this would be incredibly taxing on spawn
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


    public boolean getIsAsleep() {
        return asleep;
    }

    public void setIsAsleep(boolean change){
        asleep = change;
    }

    public int getSleepTime() {
        return VillagerArray[0];
    }

    public void setSleepTime(int sleepTime) {
        VillagerArray[0] = sleepTime;
    }
}
