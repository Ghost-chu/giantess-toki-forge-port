package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.entity.TamedAraEntity;
import net.mcreator.gts.GtsMod;

public class TamedAraBreachProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!GtsModVariables.MapVariables.get(world).DisableAraBreach && !GtsModVariables.MapVariables.get(world).DisableAraBreach) {
			if (sourceentity instanceof TamedAraEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "Ara.Breach");
			sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
			if (sourceentity instanceof Mob _mobSetNoAi) {
				_mobSetNoAi.setNoAi(true);
			}
			sourceentity.getPersistentData().putBoolean("AttackCheck", true);
			sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", true);
			sourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())));
			GtsMod.queueServerWork(17, () -> {
				if (sourceentity.isAlive()) {
					AraBreachExplosionProcedure.execute(world, sourceentity);
				}
			});
			GtsMod.queueServerWork(30, () -> {
				if (sourceentity.isAlive()) {
					sourceentity.setDeltaMovement(new Vec3(0, 0, 0));
					if (sourceentity instanceof TamedAraEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "empty");
					if (sourceentity instanceof Mob _mobSetNoAi) {
						_mobSetNoAi.setNoAi(false);
					}
					sourceentity.getPersistentData().putBoolean("ExplosionImmunityCheck", false);
					sourceentity.getPersistentData().putBoolean("AttackCheck", false);
				}
			});
		}
	}
}








