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
import net.mcreator.gts.entity.TamedAraEntity;
import net.mcreator.gts.GtsMod;

public class TamedAraGrab1Procedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof TamedAraEntity _datEntSetS)
			_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "Ara.LowAtk2");
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
		GtsMod.queueServerWork(25, () -> {
			if (sourceentity.isAlive()) {
				entity.setShiftKeyDown(false);
				entity.stopRiding();
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
		GtsMod.queueServerWork(65, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (3 * (sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
			}
		});
		GtsMod.queueServerWork(95, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (3 * (sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
			}
		});
		GtsMod.queueServerWork(125, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (3 * (sourceentity instanceof LivingEntity _livingEntity46 && _livingEntity46.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity46.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
			}
		});
		GtsMod.queueServerWork(155, () -> {
			if (sourceentity.isAlive() && entity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (3 * (sourceentity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity54.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
				sourceentity.getPersistentData().putBoolean("ThrowingTarget", true);
				entity.stopRiding();
			}
		});
		GtsMod.queueServerWork(175, () -> {
			if (sourceentity.isAlive() && sourceentity.getPersistentData()
					.getDouble("TargetStruggleCount") < (sourceentity instanceof LivingEntity _livingEntity62 && _livingEntity62.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity62.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)) {
				sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
				if (sourceentity instanceof Mob _mobSetNoAi) {
					_mobSetNoAi.setNoAi(false);
				}
				sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				if (sourceentity instanceof TamedAraEntity _datEntSetS)
					_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "empty");
			}
		});
	}
}









