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
import net.mcreator.gts.entity.GiantessSpitEntity;
import net.mcreator.gts.entity.AraEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class AraMeleeAttackProcedure {
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
		if (sourceentity instanceof AraEntity) {
			if (immediatesourceentity instanceof GiantessSpitEntity) {
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
						&& !entity.isPassenger() && !GtsModVariables.MapVariables.get(world).DisableAraEat
						&& (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity15.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
						&& !(sourceentity instanceof LivingEntity _livEnt19 && _livEnt19.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed")) {
					if ((sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity21.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (Mth.nextInt(RandomSource.create(), 1, 2) == 1) {
							AraLowerVore1Procedure.execute(world, x, y, z, entity, sourceentity);
						} else {
							AraLowerVore2Procedure.execute(world, x, y, z, entity, sourceentity);
						}
					} else {
						AraWaistVoreProcedure.execute(world, x, y, z, entity, sourceentity);
					}
				} else if (entity.isShiftKeyDown() && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 0.5
						&& !(world instanceof ServerLevel _level26 && _level26.isRaided(BlockPos.containing(x, y, z))) && !GtsModVariables.MapVariables.get(world).DisableSubmission
						&& (sourceentity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& !(sourceentity instanceof LivingEntity _livEnt28 && _livEnt28.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed")
						&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AMETHYST_SHARD
								|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Items.AMETHYST_SHARD)
						&& sourceentity.getPersistentData().getBoolean("CanBeFriend") && Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
					AraTameProcedure.execute(world, entity, sourceentity, 80);
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity37.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableAraStep) {
							AraSteppingProcedure.execute(world, entity, sourceentity);
						} else if ((MeleeAttackChoice == 3 || MeleeAttackChoice == 4) && !GtsModVariables.MapVariables.get(world).DisableAraTailSwipe) {
							AraKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt39 && _livEnt39.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& MeleeAttackChoice == 5 && !GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze) {
							AraGrab1Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt42 && _livEnt42.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity43.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& MeleeAttackChoice == 6 && !GtsModVariables.MapVariables.get(world).DisableAraLowGrab) {
							AraGrab2Procedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableAraStep) {
							AraSteppingProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableAraTailSwipe) {
							AraKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt45 && _livEnt45.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity46 && _livingEntity46.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity46.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze) {
							AraGrab1Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt48 && _livEnt48.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraLowGrab) {
							AraGrab2Procedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity50.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity51.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if ((MeleeAttackChoice == 1 || MeleeAttackChoice == 2) && !GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap) {
							AraMiddleKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt53 && _livEnt53.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity54.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& (MeleeAttackChoice == 3 || MeleeAttackChoice == 4) && !GtsModVariables.MapVariables.get(world).DisableAraWaistGrab) {
							AraGrab3Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt56 && _livEnt56.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity57 && _livingEntity57.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity57.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& (MeleeAttackChoice == 5 || MeleeAttackChoice == 6) && !GtsModVariables.MapVariables.get(world).DisableAraChomp) {
							AraBiteProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap) {
							AraMiddleKnockbackProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt59 && _livEnt59.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity60 && _livingEntity60.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity60.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraWaistGrab) {
							AraGrab3Procedure.execute(world, entity, sourceentity);
						} else if (!entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt62 && _livEnt62.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& (sourceentity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity63.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !GtsModVariables.MapVariables.get(world).DisableAraChomp) {
							AraBiteProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity64 && _livingEntity64.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity64.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableAraAboveAttack) {
							AraUpperHitProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					}
				}
			}
		}
	}
}









