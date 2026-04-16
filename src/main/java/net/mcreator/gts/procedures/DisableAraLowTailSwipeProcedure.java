package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraLowTailSwipeProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraTailSwipe) {
			GtsModVariables.MapVariables.get(world).DisableAraTailSwipe = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraTailSwipe = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








