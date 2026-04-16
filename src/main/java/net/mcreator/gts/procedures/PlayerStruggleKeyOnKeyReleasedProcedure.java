package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsAttributes;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.entity.AraEntity;

public class PlayerStruggleKeyOnKeyReleasedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide() && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "untamedgiantess"))) && entity.isAlive()
				&& (entity.getVehicle()).isAlive()) {
			if (GtsModVariables.MapVariables.get(world).ChancedBasedGrabEscape) {
				if (1 == Mth.nextInt(RandomSource.create(), 1,
						(int) (((entity.getVehicle()) instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity9.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) * 2))) {
					(entity.getVehicle()).getPersistentData().putDouble("TargetStruggleCount",
							((entity.getVehicle()) instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity12.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0));
					if ((entity.getVehicle()) instanceof TokiEntity) {
						PlayerStruggleTokiActionProcedure.execute(world, entity.getVehicle(), entity);
					} else if ((entity.getVehicle()) instanceof CandyEntity) {
						PlayerStruggleCandyActionProcedure.execute(world, entity.getVehicle(), entity);
					} else if ((entity.getVehicle()) instanceof AraEntity) {
						PlayerStruggleAraActionProcedure.execute(world, entity.getVehicle(), entity);
					}
				}
			} else {
				if ((entity.getVehicle()).getPersistentData().getDouble(
						"TargetStruggleCount") >= ((entity.getVehicle()) instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity27.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0)) {
					if ((entity.getVehicle()) instanceof TokiEntity) {
						PlayerStruggleTokiActionProcedure.execute(world, entity.getVehicle(), entity);
					} else if ((entity.getVehicle()) instanceof CandyEntity) {
						PlayerStruggleCandyActionProcedure.execute(world, entity.getVehicle(), entity);
					} else if ((entity.getVehicle()) instanceof AraEntity) {
						PlayerStruggleAraActionProcedure.execute(world, entity.getVehicle(), entity);
					}
				} else {
					(entity.getVehicle()).getPersistentData().putDouble("TargetStruggleCount", ((entity.getVehicle()).getPersistentData().getDouble("TargetStruggleCount") + 1));
				}
			}
		} else if (!world.isClientSide() && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "tamedgiantess"))) && (entity.getVehicle()).onGround()) {
			(entity.getVehicle()).setDeltaMovement(
					new Vec3(0, (1 + ((entity.getVehicle()) instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(GtsAttributes.SCALE.get()) ? _livingEntity49.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() : 0) / 10), 0));
		}
	}
}








