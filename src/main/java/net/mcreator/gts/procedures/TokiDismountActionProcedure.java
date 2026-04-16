package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class TokiDismountActionProcedure {
	@SubscribeEvent
	public static void onEventTriggered(EntityMountEvent event) {
		execute(event, event.getLevel(), event.getEntityBeingMounted(), event.getEntityMounting(), event.isDismounting());
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, boolean DISMOUNTING) {
		execute(null, world, entity, sourceentity, DISMOUNTING);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity, boolean DISMOUNTING) {
		if (entity == null || sourceentity == null)
			return;
		if (!world.isClientSide() && DISMOUNTING) {
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "untamedgiantess")))) {
				if (!entity.getPersistentData().getBoolean("ThrowingTarget") && sourceentity.isAlive() && entity.isAlive()) {
					if (event != null) {
						event.setCanceled(true);
					}
				} else {
					entity.getPersistentData().putBoolean("ThrowingTarget", false);
					sourceentity.getPersistentData().putBoolean("BeingGrabbed", false);
				}
			}
		}
	}
}








