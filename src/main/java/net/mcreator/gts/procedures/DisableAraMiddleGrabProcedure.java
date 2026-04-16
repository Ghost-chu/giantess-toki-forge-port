package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraMiddleGrabProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraWaistGrab) {
			GtsModVariables.MapVariables.get(world).DisableAraWaistGrab = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraWaistGrab = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








