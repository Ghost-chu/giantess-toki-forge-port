package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedAraEntity;

public class TamedAraMobAggroCheckProcedure {
	public static boolean execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return false;
		return !GtsModVariables.MapVariables.get(world).DisableTamedGiantessMobAggro && (entity.getFirstPassenger()) == null && !(entity instanceof TamedAraEntity _datEntL2 && _datEntL2.getEntityData().get(TamedAraEntity.DATA_Sleep));
	}
}








