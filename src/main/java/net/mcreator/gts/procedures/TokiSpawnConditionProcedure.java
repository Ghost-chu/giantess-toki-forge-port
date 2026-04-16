package net.mcreator.gts.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.network.GtsModVariables;

public class TokiSpawnConditionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "is_overworld"))) ? world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z)) : true)
				&& world.getMaxLocalRawBrightness(BlockPos.containing(x, y + 1, z)) <= 4
				&& (GtsModVariables.MapVariables.get(world).DisableGtsBiomeSpawn ? true : world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "is_taiga"))))
				&& !GtsModVariables.MapVariables.get(world).DisableNaturalSpawn) {
			return true;
		}
		return false;
	}
}








