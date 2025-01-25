package com.nationsandkings.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

public class GenericVillagerBrain extends LivingEntity {


    protected GenericVillagerBrain(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    protected static Brain<?> create(Brain<GenericVillagerEntity> brain) {


        return brain;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return null;
    }
}
