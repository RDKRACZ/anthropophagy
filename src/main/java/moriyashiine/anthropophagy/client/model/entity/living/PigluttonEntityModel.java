package moriyashiine.anthropophagy.client.model.entity.living;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class PigluttonEntityModel<T extends LivingEntity> extends EntityModel<T> {
	private final ModelPart neck;
	private final ModelPart body;
	private final ModelPart bipedLeftArm;
	private final ModelPart bipedRightArm;
	private final ModelPart bipedLeftLeg;
	private final ModelPart bipedRightLeft;
	
	public PigluttonEntityModel() {
		textureWidth = 128;
		textureHeight = 64;
		body = new ModelPart(this);
		body.setPivot(0.0F, 7.0F, 4.0F);
		setRotationAngle(body, 0.9163F, 0.0F, 0.0F);
		body.setTextureOffset(0, 18).addCuboid(-5.0F, -7.2F, -3.2F, 10.0F, 11.0F, 7.0F, 0.0F, false);
		body.setTextureOffset(0, 37).addCuboid(-5.5F, -8.5F, -7.5F, 11.0F, 11.0F, 5.0F, 0.0F, false);
		
		ModelPart tail_r1 = new ModelPart(this);
		tail_r1.setPivot(0.0F, 1.0F, 4.0F);
		body.addChild(tail_r1);
		setRotationAngle(tail_r1, -1.0472F, 0.0F, 0.0F);
		tail_r1.setTextureOffset(71, 40).addCuboid(-2.0F, -4.0F, -1.3F, 4.0F, 4.0F, 5.0F, 0.0F, false);
		
		ModelPart chest = new ModelPart(this);
		chest.setPivot(0.0F, -5.5979F, -0.1846F);
		body.addChild(chest);
		setRotationAngle(chest, 0.48F, 0.0F, 0.0F);
		chest.setTextureOffset(0, 0).addCuboid(-6.0F, -8.6F, -4.0F, 12.0F, 9.0F, 8.0F, 0.0F, false);
		
		ModelPart rightPec_r1 = new ModelPart(this);
		rightPec_r1.setPivot(-2.0F, -2.4021F, -4.8154F);
		chest.addChild(rightPec_r1);
		setRotationAngle(rightPec_r1, -0.2182F, 0.1047F, -0.0873F);
		rightPec_r1.setTextureOffset(41, 38).addCuboid(-4.5F, -7.0F, -2.0F, 7.0F, 7.0F, 4.0F, 0.0F, true);
		
		ModelPart leftPec_r1 = new ModelPart(this);
		leftPec_r1.setPivot(2.0F, -2.4021F, -4.8154F);
		chest.addChild(leftPec_r1);
		setRotationAngle(leftPec_r1, -0.2182F, -0.1047F, 0.0873F);
		leftPec_r1.setTextureOffset(41, 38).addCuboid(-2.5F, -7.0F, -2.0F, 7.0F, 7.0F, 4.0F, 0.0F, false);
		
		neck = new ModelPart(this);
		neck.setPivot(0.0F, -6.6F, 1.3F);
		chest.addChild(neck);
		setRotationAngle(neck, -1.9635F, 0.0F, 0.0F);
		neck.setTextureOffset(32, 50).addCuboid(-3.5F, -2.5F, -6.0F, 7.0F, 5.0F, 6.0F, 0.0F, false);
		
		ModelPart head = new ModelPart(this);
		head.setPivot(0.0F, -2.5746F, -3.3132F);
		neck.addChild(head);
		setRotationAngle(head, 0.6545F, 0.0F, 0.0F);
		head.setTextureOffset(36, 19).addCuboid(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 6.0F, 0.0F, false);
		
		ModelPart upperJaw = new ModelPart(this);
		upperJaw.setPivot(0.0F, 1.3F, -6.0F);
		head.addChild(upperJaw);
		upperJaw.setTextureOffset(42, 1).addCuboid(-1.99F, -0.5F, -4.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
		
		ModelPart snout = new ModelPart(this);
		snout.setPivot(0.0F, -2.1F, 1.5F);
		upperJaw.addChild(snout);
		setRotationAngle(snout, 0.1745F, 0.0F, 0.0F);
		snout.setTextureOffset(41, 0).addCuboid(-2.0F, -1.5F, -6.1F, 4.0F, 3.0F, 6.0F, 0.0F, false);
		
		ModelPart nose = new ModelPart(this);
		nose.setPivot(0.0F, -0.1F, -5.5F);
		snout.addChild(nose);
		setRotationAngle(nose, -0.0873F, 0.0F, 0.0F);
		nose.setTextureOffset(58, 0).addCuboid(-2.5F, -1.5F, -1.0F, 5.0F, 3.0F, 1.0F, 0.0F, false);
		
		ModelPart lowerJaw = new ModelPart(this);
		lowerJaw.setPivot(0.0F, 1.8F, -5.4F);
		head.addChild(lowerJaw);
		lowerJaw.setTextureOffset(41, 11).addCuboid(-2.0F, -0.0275F, -4.4022F, 4.0F, 1.0F, 5.0F, 0.0F, false);
		
		ModelPart rTusk = new ModelPart(this);
		rTusk.setPivot(-1.9F, 0.1F, -3.4F);
		lowerJaw.addChild(rTusk);
		setRotationAngle(rTusk, 0.0F, 0.0F, -0.3491F);
		rTusk.setTextureOffset(0, 0).addCuboid(-0.5F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		
		ModelPart rTuskTip = new ModelPart(this);
		rTuskTip.setPivot(0.0F, -2.3F, 0.0F);
		rTusk.addChild(rTuskTip);
		setRotationAngle(rTuskTip, 0.0F, 0.0F, 0.3491F);
		rTuskTip.setTextureOffset(0, 0).addCuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		
		ModelPart lTusk = new ModelPart(this);
		lTusk.setPivot(1.9F, 0.1F, -3.4F);
		lowerJaw.addChild(lTusk);
		setRotationAngle(lTusk, 0.0F, 0.0F, 0.3491F);
		lTusk.setTextureOffset(0, 0).addCuboid(-0.5F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		
		ModelPart lTuskTip = new ModelPart(this);
		lTuskTip.setPivot(0.0F, -2.3F, 0.0F);
		lTusk.addChild(lTuskTip);
		setRotationAngle(lTuskTip, 0.0F, 0.0F, -0.3491F);
		lTuskTip.setTextureOffset(0, 0).addCuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		
		ModelPart rEar = new ModelPart(this);
		rEar.setPivot(-3.4F, -2.0F, -2.6F);
		head.addChild(rEar);
		setRotationAngle(rEar, 0.0F, 0.0F, -1.0472F);
		rEar.setTextureOffset(35, 32).addCuboid(-5.0F, -0.5F, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F, false);
		
		ModelPart lEar = new ModelPart(this);
		lEar.setPivot(3.4F, -2.0F, -2.6F);
		head.addChild(lEar);
		setRotationAngle(lEar, 0.0F, 0.0F, 1.0472F);
		lEar.setTextureOffset(35, 32).addCuboid(0.0F, -0.5F, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F, false);
		
		bipedLeftArm = new ModelPart(this);
		bipedLeftArm.setPivot(6.0F, -3.4021F, -1.8154F);
		chest.addChild(bipedLeftArm);
		setRotationAngle(bipedLeftArm, -1.7017F, 0.0F, -0.0524F);
		bipedLeftArm.setTextureOffset(70, 0).addCuboid(-1.0F, -2.0F, -2.0F, 4.0F, 20.0F, 5.0F, 0.0F, false);
		bipedLeftArm.setTextureOffset(69, 26).addCuboid(-0.5F, -3.0F, -2.5F, 4.0F, 7.0F, 6.0F, 0.0F, false);
		
		bipedRightArm = new ModelPart(this);
		bipedRightArm.setPivot(-6.0F, -3.4021F, -2.8154F);
		chest.addChild(bipedRightArm);
		setRotationAngle(bipedRightArm, -1.7017F, 0.0F, 0.0524F);
		bipedRightArm.setTextureOffset(70, 0).addCuboid(-3.0F, -3.0F, -1.0F, 4.0F, 20.0F, 5.0F, 0.0F, true);
		bipedRightArm.setTextureOffset(69, 26).addCuboid(-3.5F, -4.0F, -1.5F, 4.0F, 7.0F, 6.0F, 0.0F, true);
		
		bipedRightLeft = new ModelPart(this);
		bipedRightLeft.setPivot(-2.6F, -0.0263F, 2.3801F);
		body.addChild(bipedRightLeft);
		setRotationAngle(bipedRightLeft, -1.2217F, 0.1222F, 0.0698F);
		bipedRightLeft.setTextureOffset(89, 0).addCuboid(-3.0F, 1.1F, -2.0F, 6.0F, 12.0F, 6.0F, 0.0F, true);
		
		ModelPart rightLeg02 = new ModelPart(this);
		rightLeg02.setPivot(-0.1388F, 8.7572F, 3.6969F);
		bipedRightLeft.addChild(rightLeg02);
		rightLeg02.setTextureOffset(95, 19).addCuboid(-2.0F, -0.5F, -0.1F, 4.0F, 9.0F, 4.0F, 0.0F, true);
		
		ModelPart rightTrotter = new ModelPart(this);
		rightTrotter.setPivot(0.0F, 8.1F, 1.3F);
		rightLeg02.addChild(rightTrotter);
		setRotationAngle(rightTrotter, 0.3142F, 0.0F, -0.0873F);
		rightTrotter.setTextureOffset(112, 15).addCuboid(-1.9F, -0.1F, -1.6F, 4.0F, 3.0F, 3.0F, 0.0F, false);
		
		ModelPart rTrotterClaw01 = new ModelPart(this);
		rTrotterClaw01.setPivot(-1.1F, 1.5F, -1.1F);
		rightTrotter.addChild(rTrotterClaw01);
		setRotationAngle(rTrotterClaw01, 0.6981F, 0.1047F, 0.0349F);
		rTrotterClaw01.setTextureOffset(115, 0).addCuboid(-1.0F, -1.3F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, true);
		
		ModelPart rTrotterClaw02 = new ModelPart(this);
		rTrotterClaw02.setPivot(1.0F, 1.5F, -1.1F);
		rightTrotter.addChild(rTrotterClaw02);
		setRotationAngle(rTrotterClaw02, 0.6981F, -0.1047F, -0.0349F);
		rTrotterClaw02.setTextureOffset(115, 0).addCuboid(-1.0F, -1.3F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		
		bipedLeftLeg = new ModelPart(this);
		bipedLeftLeg.setPivot(2.6F, -0.0263F, 2.3801F);
		body.addChild(bipedLeftLeg);
		setRotationAngle(bipedLeftLeg, -1.2217F, -0.1222F, -0.0698F);
		bipedLeftLeg.setTextureOffset(89, 0).addCuboid(-3.0F, 1.1F, -2.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		
		ModelPart leftLeg02 = new ModelPart(this);
		leftLeg02.setPivot(0.1388F, 8.7572F, 3.6969F);
		bipedLeftLeg.addChild(leftLeg02);
		leftLeg02.setTextureOffset(95, 19).addCuboid(-2.0F, -0.5F, -0.1F, 4.0F, 9.0F, 4.0F, 0.0F, false);
		
		ModelPart leftTrotter = new ModelPart(this);
		leftTrotter.setPivot(0.0F, 8.1F, 1.3F);
		leftLeg02.addChild(leftTrotter);
		setRotationAngle(leftTrotter, 0.3142F, 0.0F, 0.0873F);
		leftTrotter.setTextureOffset(112, 15).addCuboid(-1.9F, -0.1F, -1.6F, 4.0F, 3.0F, 3.0F, 0.0F, false);
		
		ModelPart lTrotterClaw01 = new ModelPart(this);
		lTrotterClaw01.setPivot(1.1F, 1.5F, -1.1F);
		leftTrotter.addChild(lTrotterClaw01);
		setRotationAngle(lTrotterClaw01, 0.6981F, -0.1047F, -0.0349F);
		lTrotterClaw01.setTextureOffset(115, 0).addCuboid(-1.0F, -1.3F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		
		ModelPart lTrotterClaw02 = new ModelPart(this);
		lTrotterClaw02.setPivot(-1.0F, 1.5F, -1.1F);
		leftTrotter.addChild(lTrotterClaw02);
		setRotationAngle(lTrotterClaw02, 0.6981F, 0.1047F, 0.0349F);
		lTrotterClaw02.setTextureOffset(115, 0).addCuboid(-1.0F, -1.3F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, true);
	}
	
	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		neck.yaw = headYaw * 0.01f;
		neck.pitch = headPitch * 0.01f - 1.5f;
		bipedLeftArm.pitch = (MathHelper.cos(limbAngle * 0.6662f) * limbDistance * 0.5f) - 5 / 3f;
		bipedRightArm.pitch = (MathHelper.cos(limbAngle * 0.6662f + (float) Math.PI) * limbDistance * 0.5f) - 5 / 3f - MathHelper.sin((float) (handSwingProgress * Math.PI));
		bipedRightArm.yaw = MathHelper.sin((float) (handSwingProgress * Math.PI));
		bipedRightArm.roll = MathHelper.sin((float) (handSwingProgress * Math.PI));
		bipedLeftLeg.pitch = (MathHelper.cos(limbAngle * 0.6662f + (float) Math.PI) * limbDistance) - 4 / 5f;
		bipedRightLeft.pitch = (MathHelper.cos(limbAngle * 0.6662f) * limbDistance) - 4 / 5f;
	}
	
	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}
}
