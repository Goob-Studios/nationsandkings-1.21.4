package com.nationsandkings.entity.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.Entities;
import com.nationsandkings.entity.ai.tasks.*;
import com.nationsandkings.items.ModItems;
import com.nationsandkings.tags.NKTags;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;



import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class GenericVillagerBrain implements EmploymentHaver{

    public static final Item BARTERING_ITEM;




    protected GenericVillagerBrain(){
    }

    static {
        BARTERING_ITEM = ModItems.COPPER_COINS;
    }

    protected static Brain<?> create(Brain<GenericVillagerEntity> brain) {

//        addCoreActivities(brain);
//        addIdleActivities(brain);
//        brain.setDefaultActivity(Activity.CORE);
//        brain.resetPossibleActivities();
////        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
//        return brain;

        addCoreActivities(brain);
        addIdleActivities(brain);
        addRestActivities(brain);
        addFightActivities(brain);
        addAdmireItemActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new VillagerFloatTask<>(0.8F),
                new VillagerUpdateLookControlTask(45, 90),
                new VillagerMoveToTargetTask(150, 250),
                new LookAroundTask(UniformIntProvider.create(0, 20), 1.0F, 1.0F, 1.0F),
                new FleeTask<>(0.5f),
                VillagerRemoveOffHandItemTask.create(),
                VillagerAdmireCoinTask.create(119)
        ));


        // new UpdateLookControlTask(45, 90), new MoveToTargetTask())
        //StrollTask.create(0.5f, 5, 5)
    }


    private static void addIdleActivities(Brain<GenericVillagerEntity> brain) {
//        brain.setTaskList(Activity.IDLE, 0, ImmutableList.of(StrollTask.create(0.3f)));
//        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60)))));
//        brain.setTaskList(Activity.IDLE, ImmutableList.of(UpdateAttackTargetTask.create(world, target), ));

        brain.setTaskList(Activity.IDLE, ImmutableList.of(
//                Pair.of(0, StrollTask.create(0.5f, 7, 7)),
                Pair.of(0, makeRandomWanderTask()),
                Pair.of(1, makeRandomLookTask()),
                Pair.of(2, FindInteractionTargetTask.create(EntityType.PLAYER, 4))
        ));
    }

    private static void addPlayActivities(Brain<GenericVillagerEntity> brain){
        brain.setTaskList(Activity.PLAY, ImmutableList.of(

        ));
    }

    private static void addAdmireItemActivities(Brain<GenericVillagerEntity> brain) {
        brain.setTaskList(Activity.ADMIRE_ITEM, 10, ImmutableList.of(
                WalkTowardsNearestVisibleWantedItemTask.create(GenericVillagerBrain::doesNotHaveCurrencyInOffHand, 0.5F, true, 9),
                WantNewItemTask.create(9),
                AdmireItemTimeLimitTask.create(200, 200)),
                MemoryModuleType.ADMIRING_ITEM);
    }



    private static void addFightActivities(Brain<GenericVillagerEntity> brain){
        brain.setTaskList(Activity.FIGHT, 0, ImmutableList.of(
                AttackTask.create(5, 1.5f)), MemoryModuleType.HURT_BY_ENTITY);
    }

    private static void addRestActivities(Brain<GenericVillagerEntity> brain){

        //For the sleep task to work properly we'd need the memory module for home (and to store that.)
        // MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LAST_WOKEN, MemoryModuleState.REGISTERED
        brain.setTaskList(Activity.REST, 0, ImmutableList.of(new SleepTask()));
    }

    //Working on making bartering work
//    private static void addBarterActivites(Brain<GenericVillagerEntity> brain){
//        brain.setTaskList();
//    }


    //For attacking, we can most likely use the hurt_by_entity memory





    static void updateActivities(GenericVillagerEntity villager) {
        //Do we need to update whenever the villager is hit? That might be why they're not fighting back.
        // We also need to work on the sleeping activity.
    villager.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.REST, Activity.ADMIRE_ITEM));
    }



    public static Optional<LookTarget> getPlayerLookTarget(LivingEntity entity) {
        Optional<PlayerEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        return optional.map(player -> new EntityLookTarget(player, true));
    }

    //Stop Walking
    private static void stopWalking(GenericVillagerEntity villager) {
        villager.getBrain().forget(MemoryModuleType.WALK_TARGET);
        villager.getNavigation().stop();
    }

    //Random Tasks

    private static RandomTask<GenericVillagerEntity> makeRandomWanderTask() {
        return new RandomTask(ImmutableList.of(Pair.of(StrollTask.create(0.5F), 2), Pair.of(FindEntityTask.create(Entities.GENERIC_VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, 0.5F, 2), 2), Pair.of(TaskTriggerer.runIf(GenericVillagerBrain::canWander, GoToLookTargetTask.create(0.5F, 3)), 2), Pair.of(new WaitTask(60, 120), 1)));
    }

    private static RandomTask<GenericVillagerEntity> makeRandomLookTask() {
        return new RandomTask(ImmutableList.of(Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 1), Pair.of(LookAtMobTask.create(Entities.GENERIC_VILLAGER, 8.0F), 1), Pair.of(LookAtMobTask.create(8.0F), 1)));

    }

    private static boolean canWander(LivingEntity villager) {
        return !hasPlayerHoldingWantedItemNearby(villager);
    }

    //Home

    //This needs to be re-rewritten this would be incredibly taxing on spawn
    public static void findHome(GenericVillagerEntity villager){
        villager.getHome(villager);
    }



    //Memory Stuff

    private static void setAdmiringItem(LivingEntity entity) {
        entity.getBrain().remember(MemoryModuleType.ADMIRING_ITEM, true, 119L);
    }


    //Bartering Stuff

    public static boolean isVillagerCurrency(ItemStack stack) {
        return stack.isIn(NKTags.VILLAGER_CURRENCY);
    }

    private static void barterItem(GenericVillagerEntity villager, ItemStack stack) {

        ItemStack itemStack = villager.addItem(stack);
        dropBarteredItem(villager, Collections.singletonList(itemStack));
    }

    private static void doBarter(GenericVillagerEntity villager, List<ItemStack> items) {
        Optional<PlayerEntity> optional = villager.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            dropBarteredItem(villager, (PlayerEntity)optional.get(), items);
        } else {
            dropBarteredItem(villager, items);
        }

    }

    private static void dropBarteredItem(GenericVillagerEntity villager, List<ItemStack> items) {

        drop(villager, items, findGround(villager));
        //Just calling this here temporarly
        villager.onTrade(villager);
    }

    private static void dropBarteredItem(GenericVillagerEntity villager, PlayerEntity player, List<ItemStack> items) {
        drop(villager, items, player.getPos());
    }

    private static void drop(GenericVillagerEntity villager, List<ItemStack> items, Vec3d pos) {
        if (!items.isEmpty()) {
            villager.swingHand(Hand.OFF_HAND);
            Iterator var3 = items.iterator();

            while(var3.hasNext()) {
                ItemStack itemStack = (ItemStack)var3.next();
                TargetUtil.give(villager, itemStack, pos.add(0.0, 1.0, 0.0));
            }
        }

    }

    protected static void loot(ServerWorld world, GenericVillagerEntity villager, ItemEntity itemEntity) {


        stopWalking(villager);
        ItemStack itemStack;
        if (itemEntity.getStack().isOf(ModItems.COPPER_COINS)) {
            villager.sendPickup(itemEntity, itemEntity.getStack().getCount());
            itemStack = itemEntity.getStack();
            itemEntity.discard();
        } else {
            villager.sendPickup(itemEntity, 1);
            itemStack = getItemFromStack(itemEntity);
        }

        if (isVillagerCurrency(itemStack)) {
            villager.getBrain().forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            swapItemWithOffHand(world, villager, itemStack);
            setAdmiringItem(villager);
        } else {
            boolean bl = !villager.tryEquip(world, itemStack).equals(ItemStack.EMPTY);
            if (!bl) {
                barterItem(villager, itemStack);
            }
        }
    }

    private static ItemStack getItemFromStack(ItemEntity stack) {
        ItemStack itemStack = stack.getStack();
        ItemStack itemStack2 = itemStack.split(1);
        if (itemStack.isEmpty()) {
            stack.discard();
        } else {
            stack.setStack(itemStack);
        }

        return itemStack2;
    }

    private static List<ItemStack> getBarteredItem(GenericVillagerEntity villager) {
//        LootTable lootTable = villager.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.VILLAGE_PLAINS_CHEST);
        LootTable lootTable = villager.getJobLoot(villager.getJob(villager), villager);
        List<ItemStack> list = lootTable.generateLoot((new LootWorldContext.Builder((ServerWorld)villager.getWorld())).add(LootContextParameters.THIS_ENTITY, villager).build(LootContextTypes.BARTER));
        return list;
    }

    public static void consumeOffHandItem(ServerWorld world, GenericVillagerEntity villager, boolean barter) {
        ItemStack itemStack = villager.getStackInHand(Hand.OFF_HAND);
        villager.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
        boolean bl;
        if (villager.isAdult()) {
            bl = acceptsForBarter(itemStack);
            if (barter && bl) {
                doBarter(villager, getBarteredItem(villager));
            } else if (!bl) {
                boolean bl2 = !villager.tryEquip(world, itemStack).isEmpty();
                if (!bl2) {
                    barterItem(villager, itemStack);
                }
            }
        } else {
            bl = !villager.tryEquip(world, itemStack).isEmpty();
            if (!bl) {
                ItemStack itemStack2 = villager.getMainHandStack();
                if (isVillagerCurrency(itemStack2)) {
                    barterItem(villager, itemStack2);
                } else {
                    doBarter(villager, Collections.singletonList(itemStack2));
                }

                villager.equipToMainHand(itemStack);
            }
        }

    }

    protected static void pickupItemWithOffHand(ServerWorld world, GenericVillagerEntity villager) {
        if (isAdmiringItem(villager) && !villager.getOffHandStack().isEmpty()) {
            villager.dropStack(world, villager.getOffHandStack());
            villager.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
        }

    }

    private static boolean acceptsForBarter(ItemStack stack) {
        return stack.isOf(BARTERING_ITEM);
    }

    private static boolean isAdmiringItem(GenericVillagerEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.ADMIRING_ITEM);
    }

    private static Vec3d findGround(GenericVillagerEntity villager) {
        Vec3d vec3d = FuzzyTargeting.find(villager, 4, 2);
        return vec3d == null ? villager.getPos() : vec3d;
    }

    private static boolean doesNotHaveCurrencyInOffHand(GenericVillagerEntity villager) {
        return villager.getOffHandStack().isEmpty() || !isVillagerCurrency(villager.getOffHandStack());
    }

    public static ActionResult playerInteract(ServerWorld world, GenericVillagerEntity villager, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (isWillingToTrade(villager, itemStack)) {
            ItemStack itemStack2 = itemStack.splitUnlessCreative(1, player);
            swapItemWithOffHand(world, villager, itemStack2);
            setAdmiringItem(villager);
            stopWalking(villager);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    protected static boolean isWillingToTrade(GenericVillagerEntity villager, ItemStack itemStack) {
        return acceptsForBarter(itemStack);
    }

    private static boolean hasPlayerHoldingWantedItemNearby(LivingEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    //Holding things

    private static void swapItemWithOffHand(ServerWorld world, GenericVillagerEntity villager, ItemStack stack) {


        if (hasItemInOffHand(villager)) {
            villager.dropStack(world, villager.getStackInHand(Hand.OFF_HAND));
        }

        villager.equipToOffHand(stack);
    }

    private static boolean hasItemInOffHand(GenericVillagerEntity villager) {
        return !villager.getOffHandStack().isEmpty();
    }

    protected static boolean canGather(GenericVillagerEntity villager, ItemStack stack) {
        return !villager.isBaby();
    }










}
