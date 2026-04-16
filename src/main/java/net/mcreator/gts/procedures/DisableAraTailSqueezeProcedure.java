package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableAraTailSqueezeProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze) {
			GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableAraTailSqueeze = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








