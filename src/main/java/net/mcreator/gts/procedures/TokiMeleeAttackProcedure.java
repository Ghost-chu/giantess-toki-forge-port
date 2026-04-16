package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.init.GtsModMobEffects;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.GiantessSpitEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class TokiMeleeAttackProcedure {
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
		if (sourceentity instanceof TokiEntity) {
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
				if (((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) <= 0.5 || GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth)
						&& !entity.isPassenger() && !GtsModVariables.MapVariables.get(world).DisableVore
						&& (sourceentity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity11.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& Mth.nextInt(RandomSource.create(), 0, (int) Math.round(((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) * 10)) == 0
						&& !(sourceentity instanceof LivingEntity _livEnt15 && _livEnt15.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed")) {
					if ((sourceentity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity17.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.LowerVoreAttack");
					} else if ((sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity19.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity20.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.MiddleVoreAttack");
					} else if ((sourceentity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity22.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.UpperVoreAttack");
					}
					TokiVoreAttackProcedure.execute(world, x, y, z, entity, sourceentity);
				} else if (entity.isShiftKeyDown() && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 0.5
						&& !(world instanceof ServerLevel _level27 && _level27.isRaided(BlockPos.containing(x, y, z))) && !GtsModVariables.MapVariables.get(world).DisableSubmission
						&& (sourceentity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity28.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > 2
						&& !(sourceentity instanceof LivingEntity _livEnt29 && _livEnt29.hasEffect(GtsModMobEffects.GTS_TIMER.get())) && !entity.getPersistentData().getBoolean("BeingGrabbed")
						&& ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("minecraft", "flowers")))
								|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("minecraft", "flowers"))))
						&& sourceentity.getPersistentData().getBoolean("CanBeFriend") && Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
					if ((sourceentity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity37.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.LowerKiss");
						TokiKissProcedure.execute(world, x, y, z, entity, sourceentity, 85);
					} else if ((sourceentity instanceof LivingEntity _livingEntity39 && _livingEntity39.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity39.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity40.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.MiddleKiss");
						TokiKissProcedure.execute(world, x, y, z, entity, sourceentity, 75);
					} else if ((sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity42.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (sourceentity instanceof TokiEntity _datEntSetS)
							_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.UpperKiss");
						TokiKissProcedure.execute(world, x, y, z, entity, sourceentity, 85);
					}
				} else {
					MeleeAttackChoice = Mth.nextInt(RandomSource.create(), 1, 6);
					if ((sourceentity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity45.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) > TargetHeight) {
						if (MeleeAttackChoice < 3 && !GtsModVariables.MapVariables.get(world).DisableStep) {
							TokiSteppingProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice == 3 && !(entity instanceof IronGolem) && !GtsModVariables.MapVariables.get(world).DisableSit) {
							TokiSittingProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableKick) {
							TokiKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableStep) {
							TokiSteppingProcedure.execute(world, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableKick) {
							TokiKickProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableSit) {
							TokiSittingProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity47.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) <= TargetHeight
							&& (sourceentity instanceof LivingEntity _livingEntity48 && _livingEntity48.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity48.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 >= TargetHeight) {
						if (MeleeAttackChoice <= 2 && !GtsModVariables.MapVariables.get(world).DisableGunSlap) {
							TokiGunMeleeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (MeleeAttackChoice > 2 && MeleeAttackChoice <= 4
								&& (sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt51 && _livEnt51.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& !GtsModVariables.MapVariables.get(world).DisableThrow) {
							TokiThrowAttackProcedure.execute(world, entity, sourceentity);
						} else if (MeleeAttackChoice >= 5 && !GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TokiGunSmashProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableGunSlap) {
							TokiGunMeleeProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if (!GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TokiGunSmashProcedure.execute(world, x, y, z, entity, sourceentity);
						} else if ((sourceentity instanceof LivingEntity _livingEntity52 && _livingEntity52.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity52.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) >= 2
								&& !entity.getPersistentData().getBoolean("BeingGrabbed") && !(sourceentity instanceof LivingEntity _livEnt54 && _livEnt54.hasEffect(GtsModMobEffects.GTS_TIMER.get()))
								&& !GtsModVariables.MapVariables.get(world).DisableThrow) {
							TokiThrowAttackProcedure.execute(world, entity, sourceentity);
						}
					} else if ((sourceentity instanceof LivingEntity _livingEntity55 && _livingEntity55.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity55.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2 < TargetHeight) {
						if (!GtsModVariables.MapVariables.get(world).DisableGunSmash) {
							TokiGunSmashProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					}
				}
			}
		}
	}
}









