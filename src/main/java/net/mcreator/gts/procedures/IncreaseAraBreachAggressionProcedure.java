package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class IncreaseAraBreachAggressionProcedure {
	public static void execute(LevelAccessor world) {
		GtsModVariables.MapVariables.get(world).AraBreachAggression = GtsModVariables.MapVariables.get(world).AraBreachAggression + 1;
		GtsModVariables.MapVariables.get(world).syncData(world);
	}
}








