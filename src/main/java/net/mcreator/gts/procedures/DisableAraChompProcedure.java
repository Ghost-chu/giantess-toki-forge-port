package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraChompProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraChomp) {
			GtsModVariables.MapVariables.get(world).DisableAraChomp = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraChomp = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








