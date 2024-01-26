package com.nationsandkings.entity;

import com.nationsandkings.entity.animation.genericVillagerAnim;
import com.nationsandkings.entity.custom.GenericVillagerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class villager_pillager_generic_model<T extends GenericVillagerEntity> extends SinglePartEntityModel<T> {
	private final ModelPart villager_pillager_generic;

	private final ModelPart head;

	//meant to allow the item to be rendered in the villager's hand - not yet implemented
	private final ModelPart right_arm;
	public villager_pillager_generic_model(ModelPart root) {
		this.villager_pillager_generic = root.getChild("villager_pillager_generic");
		this.head = root.getChild("villager_pillager_generic").getChild("torso").getChild("head");
		this.right_arm = root.getChild("villager_pillager_generic").getChild("right_arm");

	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData villager_pillager_generic = modelPartData.addChild("villager_pillager_generic", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData torso = villager_pillager_generic.addChild("torso", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = torso.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.0F, -27.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_arm = villager_pillager_generic.addChild("left_arm", ModelPartBuilder.create().uv(0, 32).cuboid(4.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_arm = villager_pillager_generic.addChild("right_arm", ModelPartBuilder.create().uv(24, 16).cuboid(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_leg = villager_pillager_generic.addChild("left_leg", ModelPartBuilder.create().uv(32, 0).cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_leg = villager_pillager_generic.addChild("right_leg", ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(GenericVillagerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(genericVillagerAnim.GENERIC_WALK, limbSwing, limbSwingAmount, 1f, 1f);

	}
	private void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		villager_pillager_generic.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);


	}

	@Override
	public ModelPart getPart() {
		return villager_pillager_generic;
	}




}