package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.mcreator.gts.network.GtsModVariables;

public class CheckBiomeSpawnProcedure {
	public static String execute(LevelAccessor world) {
		if (GtsModVariables.MapVariables.get(world).DisableGtsBiomeSpawn) {
			return Component.translatable("True").getString();
		}
		return Component.translatable("False").getString();
	}
}








