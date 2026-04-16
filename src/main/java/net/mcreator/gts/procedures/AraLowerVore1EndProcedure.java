package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.AraEntity;
import net.mcreator.gts.GtsMod;

public class AraLowerVore1EndProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof AraEntity _datEntSetS)
			_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "Ara.LowNom1End");
		GtsMod.queueServerWork(110, () -> {
			if (sourceentity instanceof AraEntity _datEntSetS)
				_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "empty");
			sourceentity.getPersistentData().putBoolean("AttackCheck", false);
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(false);
			}
			AraBelchProcedure.execute(world, x, y, z, sourceentity);
		});
	}
}








