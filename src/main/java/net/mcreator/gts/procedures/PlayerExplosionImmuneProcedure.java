package net.mcreator.gts.procedures;

import net.mcreator.gts.GtsMod;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = GtsMod.MODID)
public class PlayerExplosionImmuneProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity) {
		execute(null, damagesource, entity);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		if (entity instanceof Player && !((entity.getVehicle()) == null) && (entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("gts", "tamedgiantess"))) && damagesource.is(DamageTypes.EXPLOSION)) {
			if (event != null) {
				event.setCanceled(true);
			}
		}
	}
}








