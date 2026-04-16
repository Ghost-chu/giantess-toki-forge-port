package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.GtsMod;

public class AraGrab2Procedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof AraEntity _datEntSetS)
			_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "Ara.LowAtk4");
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER.get(), 180, 1, false, false));
		sourceentity.getPersistentData().putDouble("TargetStruggleCount", 0);
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
		GtsMod.queueServerWork(24, () -> {
			if (sourceentity.isAlive()) {
				entity.setShiftKeyDown(false);
				entity.startRiding(sourceentity);
				{
					Entity _ent = sourceentity;
					_ent.setYRot(sourceentity.getYRot());
					_ent.setXRot(0);
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
		});
		GtsMod.queueServerWork(30, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Press <Space> Repeatedly To Escape!"), true);
			}
		});
		GtsMod.queueServerWork(70, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (3 * (sourceentity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity31.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
			}
		});
		GtsMod.queueServerWork(100, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (3 * (sourceentity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity38.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
			}
		});
		GtsMod.queueServerWork(125, () -> {
			if (sourceentity.isAlive() && entity.isAlive() && (entity.getVehicle()) == sourceentity) {
				sourceentity.getPersistentData().putBoolean("ThrowingTarget", true);
				entity.stopRiding();
				entity.fallDistance = 30;
				entity.setDeltaMovement(new Vec3(
						(0.1 * sourceentity.getLookAngle().x
								* (sourceentity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity50.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)),
						((-0.5) * (sourceentity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity51.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)),
						(0.1 * sourceentity.getLookAngle().z
								* (sourceentity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity53.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0))));
				GtsMod.queueServerWork(1, () -> {
					entity.hurtMarked = true;
				});
			}
		});
		GtsMod.queueServerWork(175, () -> {
			if (sourceentity.isAlive() && sourceentity.getPersistentData()
					.getDouble("TargetStruggleCount") < (sourceentity instanceof LivingEntity _livingEntity59 && _livingEntity59.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity59.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof AraEntity _datEntSetS)
					_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}









