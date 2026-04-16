package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class DisableBiomeSpawnProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableGtsBiomeSpawn) {
			GtsModVariables.MapVariables.get(world).DisableGtsBiomeSpawn = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).DisableGtsBiomeSpawn = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








