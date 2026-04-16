package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TamedAraEntity;
import net.mcreator.gts.GtsMod;

public class TamedAraLowerVore1EndProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof TamedAraEntity _datEntSetS)
			_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "Ara.LowNom1End");
		GtsMod.queueServerWork(110, () -> {
			if (sourceentity instanceof TamedAraEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "empty");
			sourceentity.getPersistentData().putBoolean("AttackCheck", false);
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(false);
			}
			TamedAraBelchProcedure.execute(world, x, y, z, sourceentity);
		});
	}
}








