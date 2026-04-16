package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsAttributes;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.GtsMod;

public class AraBelchProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (Mth.nextInt(RandomSource.create(), 1, 3) == 1 && !GtsModVariables.MapVariables.get(world).DisableBelch) {
			GtsMod.queueServerWork(20, () -> {
				if (!sourceentity.getPersistentData().getBoolean("AttackCheck")) {
					if (sourceentity instanceof AraEntity _datEntSetS)
						_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "empty");
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(true);
					}
					{
						Entity _ent = sourceentity;
						_ent.setYRot((float) sourceentity.getVisualRotationYInDegrees());
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
					GtsMod.queueServerWork(5, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts", "ara_burp")), SoundSource.HOSTILE,
										(float) (sourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity6.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0), 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts", "ara_burp")), SoundSource.HOSTILE,
										(float) (sourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity6.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0), 1, false);
							}
						}
					});
					GtsMod.queueServerWork(40, () -> {
						if (sourceentity.isAlive()) {
							sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
							if (sourceentity instanceof Mob _mobSetNoAi) {
								_mobSetNoAi.setNoAi(false);
							}
							if (sourceentity instanceof AraEntity _datEntSetS)
								_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "empty");
						}
					});
				}
			});
		}
	}
}








