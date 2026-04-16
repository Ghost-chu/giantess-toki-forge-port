package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.gts.entity.TokiEntity;
import net.mcreator.gts.entity.TamedTokiEntity;
import net.mcreator.gts.entity.TamedCandyEntity;
import net.mcreator.gts.entity.TamedAraEntity;
import net.mcreator.gts.entity.CandyEntity;
import net.mcreator.gts.entity.AraEntity;

public class GtsDeathProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (entity instanceof TokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TokiEntity.DATA_AnimationDecision, "Toki.death");
			if (entity instanceof TamedTokiEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedTokiEntity.DATA_AnimationDecision, "Toki.death");
			if (entity instanceof CandyEntity _datEntSetS)
				_datEntSetS.getEntityData().set(CandyEntity.DATA_AnimationDecision, "gts.death");
			if (entity instanceof TamedCandyEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedCandyEntity.DATA_AnimationDecision, "gts.death");
			if (entity instanceof AraEntity _datEntSetS)
				_datEntSetS.getEntityData().set(AraEntity.DATA_AnimationDecision, "gts.death");
			if (entity instanceof TamedAraEntity _datEntSetS)
				_datEntSetS.getEntityData().set(TamedAraEntity.DATA_AnimationDecision, "gts.death");
		}
	}
}








