package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableEatAttacksDependonHealthProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth) {
			GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableEatDependsOnHealth = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








