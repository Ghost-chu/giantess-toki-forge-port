package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsAttributes;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.GtsMod;

public class AraTameProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, double WaitTime) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof AraEntity _datEntSetS)
			_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "Tame");
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity.getXRot() < -35) {
			{
				Entity _ent = sourceentity;
				_ent.setYRot(sourceentity.getYRot());
				_ent.setXRot(-35);
				_ent.setYBodyRot(_ent.getYRot());
				_ent.setYHeadRot(_ent.getYRot());
				_ent.yRotO = _ent.getYRot();
				_ent.xRotO = _ent.getXRot();
				if (_ent instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
		} else if (sourceentity.getXRot() > 35) {
			{
				Entity _ent = sourceentity;
				_ent.setYRot(sourceentity.getYRot());
				_ent.setXRot(35);
				_ent.setYBodyRot(_ent.getYRot());
				_ent.setYHeadRot(_ent.getYRot());
				_ent.yRotO = _ent.getYRot();
				_ent.xRotO = _ent.getXRot();
				if (_ent instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
		}
		GtsMod.queueServerWork((int) WaitTime, () -> {
			if (sourceentity.isAlive()) {
				TamedAraSpawnProcedure.execute(world, sourceentity.getX(), sourceentity.getY(), sourceentity.getZ(), entity, sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1,
						sourceentity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity17.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0,
						sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity18.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() : 0,
						sourceentity.getXRot(),
						sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsAttributes.SAFE_FALL_DISTANCE.get()) ? _livingEntity19.getAttribute(GtsAttributes.SAFE_FALL_DISTANCE.get()).getBaseValue() : 0,
						sourceentity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity16.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0, sourceentity.getYRot());
				if (!sourceentity.level().isClientSide())
					sourceentity.discard();
			}
		});
	}
}








