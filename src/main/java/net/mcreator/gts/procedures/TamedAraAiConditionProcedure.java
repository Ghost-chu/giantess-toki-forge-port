package net.mcreator.gts.procedures;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TamedAraEntity;

public class TamedAraAiConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof TamedAraEntity _datEntL0 && _datEntL0.getEntityData().get(TamedAraEntity.DATA_Sleep)) && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof ArmorStand)
				&& (entity.getFirstPassenger()) == null;
	}
}








