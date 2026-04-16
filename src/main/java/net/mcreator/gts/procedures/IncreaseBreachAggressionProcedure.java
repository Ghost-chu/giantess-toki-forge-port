package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class IncreaseBreachAggressionProcedure {
	public static void execute(LevelAccessor world) {
		GtsModVariables.MapVariables.get(world).TokiBreachAggression = GtsModVariables.MapVariables.get(world).TokiBreachAggression + 1;
		GtsModVariables.MapVariables.get(world).syncData(world);
	}
}








