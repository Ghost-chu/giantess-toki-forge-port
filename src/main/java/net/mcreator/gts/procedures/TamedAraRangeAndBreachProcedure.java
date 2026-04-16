package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.network.GtsModVariables;

import com.ibm.icu.number.Scale;

public class TamedAraRangeAndBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		Entity SpeedEntity = null;
		double DistanceToTarget = 0;
		double TargetHeight = 0;
		double TargetSpeed = 0;
		double Scale = 0;
		double AraSpeed = 0;
		boolean AraInWater = false;
		AraInWater = sourceentity.isInWater();
		SpeedEntity = entity.getRootVehicle();
		AraSpeed = Math.pow(sourceentity.getDeltaMovement().x(), 2) + Math.pow(sourceentity.getDeltaMovement().z(), 2);
		TargetSpeed = Math.pow(SpeedEntity.getDeltaMovement().x(), 2) + Math.pow(SpeedEntity.getDeltaMovement().z(), 2);
		if (AraSpeed >= (AraInWater ? 0.000784 : 0.01)) {
			sourceentity.getPersistentData().putDouble("RangeAttack", (sourceentity.getPersistentData().getDouble("RangeAttack") + 1));
			sourceentity.getPersistentData().putDouble("BreachAttack", 0);
		} else if (AraSpeed < (AraInWater ? 0.000784 : 0.01)) {
			sourceentity.getPersistentData().putDouble("BreachAttack", (sourceentity.getPersistentData().getDouble("BreachAttack") + 1));
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
		}
		if (!(entity instanceof Player)) {
			if (TargetSpeed > (AraInWater ? 0.000784 : 0.01)) {
				entity.getPersistentData().putDouble("TargetRangeAttack", (entity.getPersistentData().getDouble("TargetRangeAttack") + 1));
			} else {
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
			}
		} else {
			if (entity.getCapability(net.mcreator.gts.capability.PlayerVariablesCapability.PLAYER_VARIABLES).orElse(new GtsModVariables.PlayerVariables()).Player_Speed > (AraInWater ? 2 : 5)) {
				entity.getPersistentData().putDouble("TargetRangeAttack", (entity.getPersistentData().getDouble("TargetRangeAttack") + 1));
			} else {
				entity.getPersistentData().putDouble("TargetRangeAttack", 0);
			}
		}
		if (sourceentity.getPersistentData().getDouble("RangeAttack") >= 15) {
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
		}
		if (entity.getPersistentData().getDouble("TargetRangeAttack") >= 15) {
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
		if (sourceentity.getPersistentData().getDouble("RangeAttack") >= 6 && entity.getPersistentData().getDouble("TargetRangeAttack") >= 6) {
			sourceentity.getPersistentData().putDouble("RangeAttack", 0);
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
		if (sourceentity.getPersistentData().getDouble("BreachAttack") >= GtsModVariables.MapVariables.get(world).AraBreachAggression) {
			TamedAraBreachProcedure.execute(world, entity, sourceentity);
			sourceentity.getPersistentData().putDouble("BreachAttack", 0);
			entity.getPersistentData().putDouble("TargetRangeAttack", 0);
		}
	}
}








