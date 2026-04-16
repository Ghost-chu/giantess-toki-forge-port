package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DecreaseAraBreachAggressionProcedure {
	public static void execute(LevelAccessor world) {
		if (!(GtsModVariables.MapVariables.get(world).AraBreachAggression == 5)) {
			GtsModVariables.MapVariables.get(world).AraBreachAggression = GtsModVariables.MapVariables.get(world).AraBreachAggression - 1;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








