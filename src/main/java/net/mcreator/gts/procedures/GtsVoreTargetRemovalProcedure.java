package net.mcreator.gts.procedures;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

public class GtsVoreTargetRemovalProcedure {
	private static final TagKey<net.minecraft.world.entity.EntityType<?>> VORE_KILL_BLACKLIST_TAG = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "vore_kill_blacklist"));

	public static void execute(LevelAccessor world, Entity target) {
		if (target == null)
			return;
		if (shouldKillInsteadOfDiscard(world, target)) {
			if (target instanceof LivingEntity living) {
				DamageSource source = target.damageSources().magic();
				living.hurt(source, Float.MAX_VALUE);
				if (!living.isDeadOrDying()) {
					living.setHealth(0);
					living.kill();
				}
			} else {
				target.discard();
			}
			return;
		}
		target.discard();
	}

	private static boolean shouldKillInsteadOfDiscard(LevelAccessor world, Entity target) {
		if (target.getType().is(VORE_KILL_BLACKLIST_TAG)) {
			return true;
		}
		String csv = GtsModVariables.MapVariables.get(world).VoreKillBlacklist;
		if (csv == null || csv.isBlank()) {
			return false;
		}
		String targetId = net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getKey(target.getType()) != null
				? net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getKey(target.getType()).toString()
				: "";
		for (String item : csv.split(",")) {
			if (targetId.equals(item.trim())) {
				return true;
			}
		}
		return false;
	}
}
