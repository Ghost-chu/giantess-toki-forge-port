package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.gts.network.GtsModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class OnWorldLoadResetProcedure {
	@SubscribeEvent
	public static void onWorldLoad(net.minecraftforge.event.level.LevelEvent.Load event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		if (!GtsModVariables.MapVariables.get(world).Version320) {
			ResetAllConfigProcedure.execute(world);
		}
	}
}








