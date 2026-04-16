package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
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
import net.mcreator.gts.entity.TamedAraEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class TamedAraMeleeAttackProcedure {
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
		double TargetHeight = 0;
		if (sourceentity instanceof TamedAraEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "giantess")))) {
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
				if (!(sourceentity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed") && !GtsModVariables.MapVariables.get(world).DisableAraEat
						&& (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.7
								|| GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth)
						&& (Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
								|| (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
					if ((sourceentity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity23.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (Mth.nextInt(RandomSource.create(), 1, 2) == 1) {
							TamedAraLowerVore1Procedure.execute(world, x, y, z, entity, sourceentity);
						} else {
							TamedAraLowerVore2Procedure.execute(world, x, y, z, entity, sourceentity);
						}
					} else {
						TamedAraWaistVoreProcedure.execute(world, x, y, z, entity, sourceentity);
					}
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity26.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableAraStep) {
							TamedAraSteppingProcedure.execute(world, entity, sourceentity);
						} else if ((MeleeAttackChoice == 3 || MeleeAttackChoice == 4) && !GtsModVariables.MapVariables.get(world).DisableAraTailSwipe) {
							TamedAraKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt28 && _livEnt28.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity29.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& MeleeAttackChoice == 5 && !GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze) {
							TamedAraGrab1Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt31 && _livEnt31.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity32.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& MeleeAttackChoice == 6 && !GtsModVariables.MapVariables.get(world).DisableAraLowGrab) {
							TamedAraGrab2Procedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableAraStep) {
							TamedAraSteppingProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableAraTailSwipe) {
							TamedAraKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt34 && _livEnt34.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity35.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze) {
							TamedAraGrab1Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt37 && _livEnt37.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity38.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraLowGrab) {
							TamedAraGrab2Procedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if ((MeleeAttackChoice == 1 || MeleeAttackChoice == 2) && !GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap) {
							TamedAraMiddleKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt42 && _livEnt42.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity43.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& (MeleeAttackChoice == 3 || MeleeAttackChoice == 4) && !GtsModVariables.MapVariables.get(world).DisableAraWaistGrab) {
							TamedAraGrab3Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt45 && _livEnt45.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity46 && _livingEntity46.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity46.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& (MeleeAttackChoice == 5 || MeleeAttackChoice == 6) && !GtsModVariables.MapVariables.get(world).DisableAraChomp) {
							TamedAraBiteProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap) {
							TamedAraMiddleKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt48 && _livEnt48.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraWaistGrab) {
							TamedAraGrab3Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt51 && _livEnt51.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity52 && _livingEntity52.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity52.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraChomp) {
							TamedAraBiteProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity53.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableAraAboveAttack) {
							TamedAraUpperHitProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					}
				}
			}
		}
	}
}









