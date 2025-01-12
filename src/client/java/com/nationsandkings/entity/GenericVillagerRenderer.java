package com.nationsandkings.entity;

import com.nationsandkings.NationsAndKings;
import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GenericVillagerRenderer extends MobEntityRenderer<GenericVillagerEntity, generic_villager_v2<GenericVillagerEntity>> {

    public GenericVillagerRenderer(EntityRendererFactory.Context context) {
        super(context, new generic_villager_v2<>(context.getPart(ModModelLayers.GENERIC_VILLAGER)), 0.6f);
    }

    @Override
    public Identifier getTexture(GenericVillagerEntity entity) {
        return Identifier.of(NationsAndKings.MOD_ID, "textures/entities/generic_villager/villager_guard.png");
    }

    //Override is here to prepare for children in the future - for now it is unused.
    @Override
    public void render(GenericVillagerEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
