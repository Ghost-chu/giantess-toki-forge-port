
package net.mcreator.gts.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.GtsAttributes;
import net.mcreator.gts.procedures.GtsSpawnProcedure;
import net.mcreator.gts.procedures.GtsHurtProcedure;
import net.mcreator.gts.procedures.GtsDeathProcedure;
import net.mcreator.gts.procedures.CandyTickUpdateProcedure;
import net.mcreator.gts.procedures.CandySpawnConditionProcedure;
import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.init.GtsModEntities;
import net.mcreator.gts.GtsNavigation;
import net.mcreator.gts.GtsMoveHelper;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.HashMap;

public class CandyEntity extends Raider implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(CandyEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(CandyEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(CandyEntity.class, EntityDataSerializers.STRING);
	public static Raid.RaiderType CANDY_RAIDER_TYPE = Raid.RaiderType.create("gts_candy", GtsModEntities.CANDY.get(), new int[]{0, 0, 0, 1, 1, 1, 1, 1});
	public static final EntityDataAccessor<Integer> DATA_passengerSlots = SynchedEntityData.defineId(CandyEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_AnimationDecision = SynchedEntityData.defineId(CandyEntity.class, EntityDataSerializers.STRING);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private Map<Integer, Vec3> passengerSlots;
	private int stompDelay;

	public CandyEntity(EntityType<CandyEntity> type, Level world) {
		super(type, world);
		xpReward = 50;
		setNoAi(false);
		passengerSlots = new HashMap();
		if (!GtsModVariables.MapVariables.get(this.level()).DisableLeavesBreakWalk) {
			this.setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
		} ;
		moveControl = new GtsMoveHelper(this, (float) GtsModVariables.MapVariables.get(this.level()).Toki_TurnSpeed);
		noCulling = true;
		stompDelay = 0;
	}

	protected PathNavigation createNavigation(Level world) {
		return new GtsNavigation(this, world);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "candyspawntexture");
		this.entityData.define(DATA_passengerSlots, 1);
		this.entityData.define(DATA_AnimationDecision, "candy.spawn");
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractGolem.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Villager.class, false, false));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1, true));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1));
	}

	protected boolean isAffectedByFluids() {
		if (this.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() >= 3) {
			return false;
		}
		return super.isAffectedByFluids();
	}

	protected float getSoundVolume() {
		if (this.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() >= 3) {
			return 5.0F;
		}
        return 1.0F;
    }

	public void playStepSound(BlockPos pos, BlockState blockIn) {
		if (this.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() < 3) return;
		
		stompDelay += 1;
		if (stompDelay >= 3) {
				this.playSound(BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts", "stomp")), this.getSoundVolume(), 1.0f);
			stompDelay = 0;
		}
	}

	public SoundEvent getAmbientSound() {
		if ((this.getHealth() / this.getMaxHealth()) <= 0.5 && this.getAttribute(GtsAttributes.SCALE.get()).getBaseValue() >= 3 && !GtsModVariables.MapVariables.get(this.level()).DisableStomachSound) {
			return BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gts", "candy.stomach"));
		} else {
			return SoundEvents.EMPTY;
		}
	}

	public SoundEvent getCelebrateSound() {
		return SoundEvents.EMPTY;
	}

	public boolean hurt(DamageSource source, float amount) {
		GtsHurtProcedure.execute(this, source.getEntity());
		Entity immediatesourceentity = source.getDirectEntity();
		return super.hurt(source, amount);
	}

	public void die(DamageSource source) {
		super.die(source);
		GtsDeathProcedure.execute(this.level(), this);
	}

	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag dataTag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, dataTag);
		GtsSpawnProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DatapassengerSlots", this.entityData.get(DATA_passengerSlots));
		compound.putString("DataAnimationDecision", this.entityData.get(DATA_AnimationDecision));
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DatapassengerSlots"))
			this.entityData.set(DATA_passengerSlots, compound.getInt("DatapassengerSlots"));
		if (compound.contains("DataAnimationDecision"))
			this.entityData.set(DATA_AnimationDecision, compound.getString("DataAnimationDecision"));
	}

	public void baseTick() {
		super.baseTick();
		CandyTickUpdateProcedure.execute(this.level(), this);
		this.refreshDimensions();
	}

	public void aiStep() {
		super.aiStep();
		if (this.isAlive()) {
			if ((this.horizontalCollision || this.verticalCollision) && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && !GtsModVariables.MapVariables.get(this.level()).DisableLeavesBreakWalk) {
				boolean flag = false;
				AABB aabb = this.getBoundingBox().inflate(1);
				for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					Block block = this.level().getBlockState(blockpos).getBlock();
					if (block instanceof LeavesBlock) {
						flag = this.level().destroyBlock(blockpos, true, this) || flag;
					}
				}
			}
		}
		this.updateSwingTime();
	}

	public static void init(SpawnPlacementRegisterEvent event) {
		event.register(GtsModEntities.CANDY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return CandySpawnConditionProcedure.execute(world, x, y, z);
		}, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

	public void applyRaidBuffs(int num, boolean logic) {
	}

		public float maxUpStep() {
		return 2.6f;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		double scale = this.getAttribute(GtsAttributes.SCALE.get()).getBaseValue();
		// 基础尺寸：0.6宽 x 1.8高（标准Minecraft Mob）
		return super.getDimensions(pose).scale((float) scale);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 70);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 25);
		builder = builder.add(Attributes.FOLLOW_RANGE, 80);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		builder = builder.add(GtsAttributes.SCALE.get(), 3.0);
		builder = builder.add(GtsAttributes.SAFE_FALL_DISTANCE.get(), 3.0);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.getEntityData().get(CandyEntity.DATA_AnimationDecision).equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("gts.walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("gts.idle"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.getEntityData().get(CandyEntity.DATA_AnimationDecision)));
		}
		return PlayState.CONTINUE;
	}

	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 45) {
			this.remove(CandyEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 5, this::movementPredicate));
	}

	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float yaw) {
		int index = this.getPassengers().indexOf(entity);
		Vec3 offset = getPassengerSlotOffset(index);
		return offset;
	}

		protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < this.getEntityData().get(CandyEntity.DATA_passengerSlots);
	}

	public Vec3 getPassengerSlotOffset(int index) {
		if (passengerSlots.containsKey(index)) {
			return passengerSlots.get(index);
		}
		return Vec3.ZERO;
	}

	public void setPassengerSlotOffset(int index, Vec3 offset) {
		passengerSlots.put(index, offset);
	}
}





