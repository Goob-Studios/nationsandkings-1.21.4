package com.nationsandkings.mixin;

import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieAttackGenericVillagerMixin {

    private void init(CallbackInfo info) {
        // This code is injected into the start of MinecraftServer.loadWorld()V
    }
}
