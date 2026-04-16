package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraLowGrabProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraLowGrab) {
			GtsModVariables.MapVariables.get(world).DisableAraLowGrab = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraLowGrab = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








