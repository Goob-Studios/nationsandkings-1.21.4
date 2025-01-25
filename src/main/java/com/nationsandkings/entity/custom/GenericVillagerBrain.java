package com.nationsandkings.entity.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;

public class GenericVillagerBrain extends LivingEntity {


    protected static Brain<?> create(Brain<GenericVillagerEntity> brain) {


        return brain;
    }
}
