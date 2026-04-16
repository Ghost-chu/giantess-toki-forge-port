package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraBreachProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraBreach) {
			GtsModVariables.MapVariables.get(world).DisableAraBreach = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraBreach = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








