package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class ChanceBasedGrabEscapeProcedure {
	public static void execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).ChancedBasedGrabEscape) {
			GtsModVariables.MapVariables.get(world).ChancedBasedGrabEscape = false;
			GtsModVariables.MapVariables.get(world).syncData(world);
		} else {
			GtsModVariables.MapVariables.get(world).ChancedBasedGrabEscape = true;
			GtsModVariables.MapVariables.get(world).syncData(world);
		}
	}
}








