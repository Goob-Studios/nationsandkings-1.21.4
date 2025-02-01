package com.nationsandkings.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import com.nationsandkings.NationsAndKings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GenericVillagerEntity extends AnimalEntity {
    
    public static final Set<Block> INTERACT_BLOCKS = null;

    //Memory/Brain stuff

    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_MODULES;

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super GenericVillagerEntity>>> SENSORS;

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




    public GenericVillagerEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.2F);

        this.moveControl = new MoveControl(this);
        this.getNavigation().setCanSwim(true);



        this.brain = this.getBrain();
        NationsAndKings.LOGGER.info(brain.toString());
        // This is where the No key memories in MapLike[{}]
        //Probably from the ImmutableMap.of that's empty.

        //Do we need this? The entity correct creates the brain when it spawns, regardless of this line being there or not.
//        this.brain = createBrainProfile().deserialize(new Dynamic<>(NbtOps.INSTANCE, NbtOps.INSTANCE.createMap(ImmutableMap.of())));

    }

    static {
        SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES);
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.PATH);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    //Create the navigation, like the Axolotl

    protected EntityNavigation createNavigation(World world) {

        MobNavigation mobNavigation = new MobNavigation(this, world);

        mobNavigation.setCanPathThroughDoors(true);
        mobNavigation.canSwim();

        return mobNavigation;
    }

    public int getMaxLookPitchChange() {
        return 1;
    }

    public int getMaxHeadRotation() {
        return 1;
    }
    
    //Information on the brain


    //This is doing Brain.createProfile(), and using Brain.class, not GenericVillagerBrain
    @Override
    protected Brain.Profile<GenericVillagerEntity> createBrainProfile() {
        NationsAndKings.LOGGER.info("Creating Brain Profile");
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        NationsAndKings.LOGGER.info("Deserializing Brain");
        return GenericVillagerBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    public Brain<GenericVillagerEntity> getBrain() {
        return (Brain<GenericVillagerEntity>) super.getBrain();
    }

// Data Tracker






//    @Override
//    public void tick() {
//        super.tick();
//
//
//
//
//        //particle stuff
//        // 20 is temporary to test if the logic works
//        if(VillagerArray[1] == 0 && timeout == 0){
//            //There is a better way to do this
//                this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
//                        this.getX()+0.5,getY() , getZ() + 0.5, 0.5, 0.5, 0.5);
//            this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
//                    this.getX()-0.5,getY() , getZ() - 0.5, 0.5, 0.5, 0.5);
//            this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
//                    this.getX(),getY()+0.5, getZ(), 0.5, 0.5, 0.5);
//            timeout = 300;
//        }
//        else{
//            timeout--;
//        }
//
//        if(homeLocation == null){
//            findHome();
//        }
//
//        if(VillagerArray[0] == 0 || this.getWorld().getTimeOfDay() == 0){
//            VillagerArray[0] = (int) ((Math.random() * (13100 - 12900)) + 12900);
//
//        }
//
//        if(VillagerArray[1] == 0.0){
//            VillagerArray[1] = 20;
//        }
//
//
//    }

    //ticking

    @Override
    public void tick(){
        super.tick();
    }

    @Override
    public Arm getMainArm() {
        return null;
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("genericVillagerBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("genericVillagerBrainActivityUpdate");
        GenericVillagerBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

    //NBT Functions

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.SNIFFER_FOOD);
    }


//    @Override
//    public void initGoals() {
//        super.initGoals();
//        this.goalSelector.add(0, new SwimGoal(this));
//        //targeting and the melee attack needs to be above this goal, so it can actively target the thing that
//        //attacked it before it tries to sleep again.
////        sleepGoal = new VillagerGenericSleep(this);
////        this.goalSelector.add(1, sleepGoal);
////        this.goalSelector.add(1, new VillagerSchedulingGoal(this));
//        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.5));
////        this.goalSelector.add(3, new VillagerWorkGoal(this));
//        this.goalSelector.add(4, new LookAroundGoal(this));
//
//
//    }

//    public void keepSleep(){
//        this.goalSelector.add(0, new SwimGoal(this));
//        sleepGoal = new VillagerGenericSleep(this);
//        this.goalSelector.add(1, sleepGoal);
//    }

    //This is where the error comes from
    public static DefaultAttributeContainer.Builder createAttributes(){
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.ATTACK_DAMAGE, 1)
                .add(EntityAttributes.ATTACK_KNOCKBACK, 1);
    }

    public boolean getHasJob(){
        return hasJob;
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

    //On hits and being hit

    private void attack(){

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

    // Sounds


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }




    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }




}
