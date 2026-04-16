package net.mcreator.gts.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.entity.TamedAraEntity;

import java.util.Comparator;

public class TamedAraSpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double health, double max_health, double movement_speed, double pitch, double safe_fall, double scale, double yaw) {
		if (entity == null)
			return;
		Entity tameableAra = null;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = GtsModEntities.TAMED_ARA.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot((float) yaw);
				entityToSpawn.setYBodyRot((float) yaw);
				entityToSpawn.setYHeadRot((float) yaw);
				entityToSpawn.setXRot((float) pitch);
			}
		}
		tameableAra = (Entity) world.getEntitiesOfClass(TamedAraEntity.class, AABB.ofSize(new Vec3(x, y, z), 2, 2, 2), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(tameableAra == null)) {
			if (tameableAra instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(GtsAttributes.SCALE.get()))
				_livingEntity3.getAttribute(GtsAttributes.SCALE.get()).setBaseValue(scale);
			if (tameableAra instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity4.getAttribute(Attributes.MAX_HEALTH).setBaseValue(max_health);
			if (tameableAra instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
				_livingEntity5.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movement_speed);
			if (tameableAra instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(GtsAttributes.SAFE_FALL_DISTANCE.get()))
				_livingEntity6.getAttribute(GtsAttributes.SAFE_FALL_DISTANCE.get()).setBaseValue(safe_fall);
			if (tameableAra instanceof LivingEntity _entity)
				_entity.setHealth((float) health);
			if (tameableAra instanceof TamableAnimal _toTame && entity instanceof Player _owner)
				_toTame.tame(_owner);
		}
	}
}









