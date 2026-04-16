package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.entity.HeartBulletEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class TamedCandyMeleeAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		double MeleeAttackChoice = 0;
		double VoreChance = 0;
		double TargetHeight = 0;
		if (sourceentity instanceof TamedCandyEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity || immediatesourceentity instanceof HeartBulletEntity || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "giantess")))) {
				if (event != null) {
					event.setCanceled(true);
				}
			} else if (!sourceentity.getPersistentData().getBoolean("AttackCheck")) {
				if (event != null) {
					event.setCanceled(true);
				}
				sourceentity.getPersistentData().putDouble("RangeAttack", 0);
				sourceentity.getPersistentData().putDouble("BreachAttack", 0);
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
				TargetHeight = entity.getY() - sourceentity.getY();
				sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
				if (!(sourceentity instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed") && !GtsModVariables.MapVariables.get(world).DisableCandyVore
						&& (sourceentity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity16.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.7
								|| GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth)
						&& (Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
								|| (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
					if ((sourceentity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity24.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof TamedCandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.lowerVore");
					} else if ((sourceentity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity26.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof TamedCandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.middleVore");
					} else if ((sourceentity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity29.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof TamedCandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "candy.upperVore");
					}
					TamedCandyVoreAttackProcedure.execute(world, x, y, z, entity, sourceentity);
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableCandyStep) {
							TamedCandySteppingProcedure.execute(world, sourceentity);
						} else if (MeleeAttackChoice == 3 && !(entity instanceof IronGolem) && !GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
							TamedCandyShockWaveProcedure.execute(world, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableCandyKick) {
							TamedCandyKickProcedure.execute(world, x, y, z, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyStep) {
							TamedCandySteppingProcedure.execute(world, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyKick) {
							TamedCandyKickProcedure.execute(world, x, y, z, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
							TamedCandyShockWaveProcedure.execute(world, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity34.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity35.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (MeleeAttackChoice <= 3 && !GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
							TamedCandySwipeProcedure.execute(world, x, y, z, sourceentity);
						} else if (MeleeAttackChoice > 3 && !GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							TamedCandySmashProcedure.execute(world, x, y, z, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
							TamedCandySwipeProcedure.execute(world, x, y, z, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							TamedCandySmashProcedure.execute(world, x, y, z, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity36.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							TamedCandySmashProcedure.execute(world, x, y, z, sourceentity);
						}
					}
				}
			}
		}
	}
}









