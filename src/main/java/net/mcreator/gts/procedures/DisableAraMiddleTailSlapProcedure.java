package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraMiddleTailSlapProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap) {
			GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraTailWaistSlap = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








