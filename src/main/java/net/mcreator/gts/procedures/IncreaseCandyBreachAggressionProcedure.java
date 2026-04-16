package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class IncreaseCandyBreachAggressionProcedure {
	public static void execute(LevelAccessor world) {
		GtsModVariables.MapVariables.get(world).CandyBreachAggression = GtsModVariables.MapVariables.get(world).CandyBreachAggression + 1;
		GtsModVariables.MapVariables.get(world).syncData(world);
	}
}








