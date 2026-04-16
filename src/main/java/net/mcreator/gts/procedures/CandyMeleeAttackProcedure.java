package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.HeartBulletEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;
import net.mcreator.gts.entity.CandyEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class CandyMeleeAttackProcedure {
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
		if (sourceentity instanceof CandyEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity || immediatesourceentity instanceof HeartBulletEntity) {
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
				if (((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.5 || GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth)
						&& !entity.isPassenger() && !GtsModVariables.MapVariables.get(world).DisableCandyVore
						&& (sourceentity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity16.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
						&& !(sourceentity instanceof LivingEntity _livEnt20 && _livEnt20.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed")) {
					if ((sourceentity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity22.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.lowerVore");
					} else if ((sourceentity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity24.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity25.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.middleVore");
					} else if ((sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.upperVore");
					}
					CandyVoreAttackProcedure.execute(world, x, y, z, entity, sourceentity);
				} else if (Mth.nextInt(RandomSource.create(), 1, 3) == 1 && !(world instanceof ServerLevel _level30 && _level30.isRaided(BlockPos.containing(x, y, z))) && !GtsModVariables.MapVariables.get(world).DisableSubmission
						&& (sourceentity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity31.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 0.5 && !entity.getPersistentData().getBoolean("BeingGrabbed")
						&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.CAKE
								|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Items.CAKE)
						&& sourceentity.getPersistentData().getBoolean("CanBeFriend")) {
					if ((sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.lowerTame");
					} else if ((sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity42.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity43.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.middleTame");
					} else if ((sourceentity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity45.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof CandyEntity _datEntSetS)
							_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "candy.upperTame");
					}
					CandyTamingProcedure.execute(world, x, y, z, entity, sourceentity);
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity48 && _livingEntity48.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity48.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableCandyStep) {
							CandySteppingProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice == 3 && !(entity instanceof IronGolem) && !GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
							CandyShockWaveProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableCandyKick) {
							CandyKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyStep) {
							CandySteppingProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyKick) {
							CandyKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandyShockWave) {
							CandyShockWaveProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity50.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity51.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (MeleeAttackChoice <= 3 && !GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
							CandySwipeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (MeleeAttackChoice > 3 && !GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							CandySmashProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandySwipe) {
							CandySwipeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							CandySmashProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity52 && _livingEntity52.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity52.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableCandySmash) {
							CandySmashProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					}
				}
			}
		}
	}
}









