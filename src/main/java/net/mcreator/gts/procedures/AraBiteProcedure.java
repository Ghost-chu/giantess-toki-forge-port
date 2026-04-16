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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.GtsMod;

public class AraBiteProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.getPersistentData().putBoolean("BeingGrabbed", true);
		sourceentity.getPersistentData().putBoolean("AttackCheck", true);
		if (sourceentity instanceof AraEntity _datEntSetS)
			_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "Ara.WaistAtk3");
		sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
		if (sourceentity instanceof Mob _mobSetNoAi) {
			_mobSetNoAi.setNoAi(true);
		}
		sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(GtsModMobEffects.GTS_TIMER.get(), 60, 1, false, false));
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
		GtsMod.queueServerWork(20, () -> {
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
		GtsMod.queueServerWork(25, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Press <Space> Repeatedly To Escape!"), true);
			}
		});
		GtsMod.queueServerWork(35, () -> {
			if (sourceentity.isAlive() && (entity.getVehicle()) == sourceentity) {
				entity.hurt((sourceentity instanceof LivingEntity _attacker ? _attacker.damageSources().mobAttack(_attacker) : sourceentity.damageSources().generic()),
						(float) (4 * (sourceentity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity31.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)));
			}
		});
		GtsMod.queueServerWork(45, () -> {
			if (sourceentity.isAlive() && entity.isAlive() && (entity.getVehicle()) == sourceentity) {
				sourceentity.getPersistentData().putBoolean("ThrowingTarget", true);
				entity.stopRiding();
				GtsMod.queueServerWork(1, () -> {
					{
						Entity _ent = entity;
						_ent.teleportTo((entity.getX()), (sourceentity.getY()), (entity.getZ()));
						if (_ent instanceof ServerPlayer _serverPlayer)
							_serverPlayer.connection.teleport((entity.getX()), (sourceentity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
					}
				});
			}
		});
		GtsMod.queueServerWork(55, () -> {
			if (sourceentity.isAlive() && sourceentity.getPersistentData()
					.getDouble("TargetStruggleCount") < (sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)) {
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









