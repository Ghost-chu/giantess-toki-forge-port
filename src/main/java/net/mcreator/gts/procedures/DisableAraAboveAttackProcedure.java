package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraAboveAttackProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraAboveAttack) {
			GtsModVariables.MapVariables.get(world).DisableAraAboveAttack = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraAboveAttack = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








