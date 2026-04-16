package net.mcreator.gts.network;

import net.mcreator.gts.GtsMod;
import net.mcreator.gts.capability.PlayerVariablesCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = GtsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GtsModVariables {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    // ---- Event handlers on the FORGE bus ----
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof net.minecraft.world.entity.player.Player) {
                if (!event.getObject().getCapability(PlayerVariablesCapability.PLAYER_VARIABLES).isPresent()) {
                    event.addCapability(
                            new net.minecraft.resources.ResourceLocation(GtsMod.MODID, "player_variables"),
                            new PlayerVariablesCapability());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getCapability(PlayerVariablesCapability.PLAYER_VARIABLES)
                        .ifPresent(vars -> vars.syncPlayerVariables(player));
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getCapability(PlayerVariablesCapability.PLAYER_VARIABLES)
                        .ifPresent(vars -> vars.syncPlayerVariables(player));
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getCapability(PlayerVariablesCapability.PLAYER_VARIABLES)
                        .ifPresent(vars -> vars.syncPlayerVariables(player));
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().getCapability(PlayerVariablesCapability.PLAYER_VARIABLES).ifPresent(original -> {
                event.getEntity().getCapability(PlayerVariablesCapability.PLAYER_VARIABLES).ifPresent(clone -> {
                    if (!event.isWasDeath()) {
                        clone.Player_Speed = original.Player_Speed;
                    }
                });
            });
        }

        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                SavedData mapdata = MapVariables.get(event.getEntity().level());
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (mapdata != null)
                    GtsNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SavedDataSyncMessage(0, mapdata));
                if (worlddata != null)
                    GtsNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SavedDataSyncMessage(1, worlddata));
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                SavedData worlddata = WorldVariables.get(event.getEntity().level());
                if (worlddata != null)
                    GtsNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SavedDataSyncMessage(1, worlddata));
            }
        }
    }

    // ---- WorldVariables ----
    public static class WorldVariables extends SavedData {
        public static final String DATA_NAME = "gts_worldvars";

        public static WorldVariables load(CompoundTag tag) {
            WorldVariables data = new WorldVariables();
            data.read(tag);
            return data;
        }

        public void read(CompoundTag nbt) {
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof ServerLevel level)
                GtsNetwork.CHANNEL.send(PacketDistributor.DIMENSION.with(level::dimension), new SavedDataSyncMessage(1, this));
        }

        static WorldVariables clientSide = new WorldVariables();

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel level) {
                return level.getDataStorage().computeIfAbsent(WorldVariables::load, WorldVariables::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    // ---- MapVariables ----
    public static class MapVariables extends SavedData {
        public static final String DATA_NAME = "gts_mapvars";
        public boolean DisableStep = false;
        public boolean DisableKick = false;
        public boolean DisableSit = false;
        public boolean DisableGunSlap = false;
        public boolean DisableGunSmash = false;
        public boolean DisableThrow = false;
        public boolean DisableVore = false;
        public boolean DisableSniper = false;
        public boolean DisableSpit = false;
        public boolean DisableDrone = false;
        public boolean DisableUpperBreach = false;
        public boolean DisableMiddleBreach = false;
        public boolean DisableLowerBreach = false;
        public boolean DisableTrappedBreach = false;
        public boolean DisableShowOff = false;
        public boolean DisableBelch = false;
        public boolean DisableStomachSound = false;
        public boolean DisableClothingDamage = false;
        public boolean DisableRaidWaveSizeChange = false;
        public double Toki_TurnSpeed = 30.0;
        public boolean DisableLeavesBreakWalk = false;
        public boolean DisableLockOnTarget = true;
        public boolean DisableSubmission = false;
        public double GtsNaturalSpawnHeight = 3.0;
        public boolean DisableNaturalSpawn = false;
        public boolean DisableCandyStep = false;
        public boolean DisableCandyShockWave = false;
        public boolean DisableCandyKick = false;
        public boolean DisableCandySwipe = false;
        public boolean DisableCandySmash = false;
        public boolean DisableCandyBreach = false;
        public boolean DisableCandyVore = false;
        public boolean DisableCandyHeartMagic = false;
        public boolean DisableCandySpit = false;
        public boolean DisableCandyThunderMagic = false;
        public boolean Version320 = true;
        public boolean DisableTamedGiantessFriendlyFire = false;
        public boolean DisableTamedGiantessBreach = false;
        public boolean DisableTamedGiantessMobAggro = false;
        public boolean DisableEatDependsOnHealth = false;
        public boolean ChancedBasedGrabEscape = false;
        public boolean DisableAraTailSwipe = false;
        public boolean DisableAraTailSqueeze = false;
        public boolean DisableAraStep = false;
        public boolean DisableAraLowGrab = false;
        public boolean DisableAraTailWaistSlap = false;
        public boolean DisableAraWaistGrab = false;
        public boolean DisableAraChomp = false;
        public boolean DisableAraAboveAttack = false;
        public boolean DisableAraBreach = false;
        public boolean DisableAraEat = false;
        public boolean DisableGtsBiomeSpawn = false;
        public double TokiBreachAggression = 15.0;
        public double CandyBreachAggression = 15.0;
        public double AraBreachAggression = 15.0;
        // Comma-separated entity ids, e.g. "minecraft:warden,minecraft:iron_golem"
        public String VoreKillBlacklist = "";

        public static MapVariables load(CompoundTag tag) {
            MapVariables data = new MapVariables();
            data.read(tag);
            return data;
        }

        public void read(CompoundTag nbt) {
            DisableStep = nbt.getBoolean("DisableStep");
            DisableKick = nbt.getBoolean("DisableKick");
            DisableSit = nbt.getBoolean("DisableSit");
            DisableGunSlap = nbt.getBoolean("DisableGunSlap");
            DisableGunSmash = nbt.getBoolean("DisableGunSmash");
            DisableThrow = nbt.getBoolean("DisableThrow");
            DisableVore = nbt.getBoolean("DisableVore");
            DisableSniper = nbt.getBoolean("DisableSniper");
            DisableSpit = nbt.getBoolean("DisableSpit");
            DisableDrone = nbt.getBoolean("DisableDrone");
            DisableUpperBreach = nbt.getBoolean("DisableUpperBreach");
            DisableMiddleBreach = nbt.getBoolean("DisableMiddleBreach");
            DisableLowerBreach = nbt.getBoolean("DisableLowerBreach");
            DisableTrappedBreach = nbt.getBoolean("DisableTrappedBreach");
            DisableShowOff = nbt.getBoolean("DisableShowOff");
            DisableBelch = nbt.getBoolean("DisableBelch");
            DisableStomachSound = nbt.getBoolean("DisableStomachSound");
            DisableClothingDamage = nbt.getBoolean("DisableClothingDamage");
            DisableRaidWaveSizeChange = nbt.getBoolean("DisableRaidWaveSizeChange");
            Toki_TurnSpeed = nbt.getDouble("Toki_TurnSpeed");
            DisableLeavesBreakWalk = nbt.getBoolean("DisableLeavesBreakWalk");
            DisableLockOnTarget = nbt.getBoolean("DisableLockOnTarget");
            DisableSubmission = nbt.getBoolean("DisableSubmission");
            GtsNaturalSpawnHeight = nbt.getDouble("GtsNaturalSpawnHeight");
            DisableNaturalSpawn = nbt.getBoolean("DisableNaturalSpawn");
            DisableCandyStep = nbt.getBoolean("DisableCandyStep");
            DisableCandyShockWave = nbt.getBoolean("DisableCandyShockWave");
            DisableCandyKick = nbt.getBoolean("DisableCandyKick");
            DisableCandySwipe = nbt.getBoolean("DisableCandySwipe");
            DisableCandySmash = nbt.getBoolean("DisableCandySmash");
            DisableCandyBreach = nbt.getBoolean("DisableCandyBreach");
            DisableCandyVore = nbt.getBoolean("DisableCandyVore");
            DisableCandyHeartMagic = nbt.getBoolean("DisableCandyHeartMagic");
            DisableCandySpit = nbt.getBoolean("DisableCandySpit");
            DisableCandyThunderMagic = nbt.getBoolean("DisableCandyThunderMagic");
            Version320 = nbt.getBoolean("Version320");
            DisableTamedGiantessFriendlyFire = nbt.getBoolean("DisableTamedGiantessFriendlyFire");
            DisableTamedGiantessBreach = nbt.getBoolean("DisableTamedGiantessBreach");
            DisableTamedGiantessMobAggro = nbt.getBoolean("DisableTamedGiantessMobAggro");
            DisableEatDependsOnHealth = nbt.getBoolean("DisableEatDependsOnHealth");
            ChancedBasedGrabEscape = nbt.getBoolean("ChancedBasedGrabEscape");
            DisableAraTailSwipe = nbt.getBoolean("DisableAraTailSwipe");
            DisableAraTailSqueeze = nbt.getBoolean("DisableAraTailSqueeze");
            DisableAraStep = nbt.getBoolean("DisableAraStep");
            DisableAraLowGrab = nbt.getBoolean("DisableAraLowGrab");
            DisableAraTailWaistSlap = nbt.getBoolean("DisableAraTailWaistSlap");
            DisableAraWaistGrab = nbt.getBoolean("DisableAraWaistGrab");
            DisableAraChomp = nbt.getBoolean("DisableAraChomp");
            DisableAraAboveAttack = nbt.getBoolean("DisableAraAboveAttack");
            DisableAraBreach = nbt.getBoolean("DisableAraBreach");
            DisableAraEat = nbt.getBoolean("DisableAraEat");
            DisableGtsBiomeSpawn = nbt.getBoolean("DisableGtsBiomeSpawn");
            TokiBreachAggression = nbt.getDouble("TokiBreachAggression");
            CandyBreachAggression = nbt.getDouble("CandyBreachAggression");
            AraBreachAggression = nbt.getDouble("AraBreachAggression");
            VoreKillBlacklist = nbt.getString("VoreKillBlacklist");
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            nbt.putBoolean("DisableStep", DisableStep);
            nbt.putBoolean("DisableKick", DisableKick);
            nbt.putBoolean("DisableSit", DisableSit);
            nbt.putBoolean("DisableGunSlap", DisableGunSlap);
            nbt.putBoolean("DisableGunSmash", DisableGunSmash);
            nbt.putBoolean("DisableThrow", DisableThrow);
            nbt.putBoolean("DisableVore", DisableVore);
            nbt.putBoolean("DisableSniper", DisableSniper);
            nbt.putBoolean("DisableSpit", DisableSpit);
            nbt.putBoolean("DisableDrone", DisableDrone);
            nbt.putBoolean("DisableUpperBreach", DisableUpperBreach);
            nbt.putBoolean("DisableMiddleBreach", DisableMiddleBreach);
            nbt.putBoolean("DisableLowerBreach", DisableLowerBreach);
            nbt.putBoolean("DisableTrappedBreach", DisableTrappedBreach);
            nbt.putBoolean("DisableShowOff", DisableShowOff);
            nbt.putBoolean("DisableBelch", DisableBelch);
            nbt.putBoolean("DisableStomachSound", DisableStomachSound);
            nbt.putBoolean("DisableClothingDamage", DisableClothingDamage);
            nbt.putBoolean("DisableRaidWaveSizeChange", DisableRaidWaveSizeChange);
            nbt.putDouble("Toki_TurnSpeed", Toki_TurnSpeed);
            nbt.putBoolean("DisableLeavesBreakWalk", DisableLeavesBreakWalk);
            nbt.putBoolean("DisableLockOnTarget", DisableLockOnTarget);
            nbt.putBoolean("DisableSubmission", DisableSubmission);
            nbt.putDouble("GtsNaturalSpawnHeight", GtsNaturalSpawnHeight);
            nbt.putBoolean("DisableNaturalSpawn", DisableNaturalSpawn);
            nbt.putBoolean("DisableCandyStep", DisableCandyStep);
            nbt.putBoolean("DisableCandyShockWave", DisableCandyShockWave);
            nbt.putBoolean("DisableCandyKick", DisableCandyKick);
            nbt.putBoolean("DisableCandySwipe", DisableCandySwipe);
            nbt.putBoolean("DisableCandySmash", DisableCandySmash);
            nbt.putBoolean("DisableCandyBreach", DisableCandyBreach);
            nbt.putBoolean("DisableCandyVore", DisableCandyVore);
            nbt.putBoolean("DisableCandyHeartMagic", DisableCandyHeartMagic);
            nbt.putBoolean("DisableCandySpit", DisableCandySpit);
            nbt.putBoolean("DisableCandyThunderMagic", DisableCandyThunderMagic);
            nbt.putBoolean("Version320", Version320);
            nbt.putBoolean("DisableTamedGiantessFriendlyFire", DisableTamedGiantessFriendlyFire);
            nbt.putBoolean("DisableTamedGiantessBreach", DisableTamedGiantessBreach);
            nbt.putBoolean("DisableTamedGiantessMobAggro", DisableTamedGiantessMobAggro);
            nbt.putBoolean("DisableEatDependsOnHealth", DisableEatDependsOnHealth);
            nbt.putBoolean("ChancedBasedGrabEscape", ChancedBasedGrabEscape);
            nbt.putBoolean("DisableAraTailSwipe", DisableAraTailSwipe);
            nbt.putBoolean("DisableAraTailSqueeze", DisableAraTailSqueeze);
            nbt.putBoolean("DisableAraStep", DisableAraStep);
            nbt.putBoolean("DisableAraLowGrab", DisableAraLowGrab);
            nbt.putBoolean("DisableAraTailWaistSlap", DisableAraTailWaistSlap);
            nbt.putBoolean("DisableAraWaistGrab", DisableAraWaistGrab);
            nbt.putBoolean("DisableAraChomp", DisableAraChomp);
            nbt.putBoolean("DisableAraAboveAttack", DisableAraAboveAttack);
            nbt.putBoolean("DisableAraBreach", DisableAraBreach);
            nbt.putBoolean("DisableAraEat", DisableAraEat);
            nbt.putBoolean("DisableGtsBiomeSpawn", DisableGtsBiomeSpawn);
            nbt.putDouble("TokiBreachAggression", TokiBreachAggression);
            nbt.putDouble("CandyBreachAggression", CandyBreachAggression);
            nbt.putDouble("AraBreachAggression", AraBreachAggression);
            nbt.putString("VoreKillBlacklist", VoreKillBlacklist);
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof Level && !world.isClientSide()) {
                GtsMapVariablesFileStorage.save(this);
                GtsNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new SavedDataSyncMessage(0, this));
            }
        }

        static MapVariables clientSide = new MapVariables();
        private static final MapVariables serverSide = new MapVariables();
        private static boolean globalConfigLoaded = false;

        private static synchronized void ensureServerConfigLoaded(ServerLevelAccessor serverLevelAcc) {
            if (globalConfigLoaded) {
                return;
            }
            if (GtsMapVariablesFileStorage.exists()) {
                GtsMapVariablesFileStorage.loadInto(serverSide);
            } else {
                MapVariables legacyData = serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(MapVariables::load, MapVariables::new, DATA_NAME);
                serverSide.read(legacyData.save(new CompoundTag()));
                GtsMapVariablesFileStorage.save(serverSide);
            }
            globalConfigLoaded = true;
        }

        public static MapVariables get(LevelAccessor world) {
            if (world instanceof ServerLevelAccessor serverLevelAcc) {
                ensureServerConfigLoaded(serverLevelAcc);
                return serverSide;
            } else {
                return clientSide;
            }
        }
    }

    // ---- Network messages ----
    public static class SavedDataSyncMessage {
        final int dataType;
        final SavedData data;

        public SavedDataSyncMessage(int dataType, SavedData data) {
            this.dataType = dataType;
            this.data = data;
        }

        public static void encode(SavedDataSyncMessage message, FriendlyByteBuf buf) {
            buf.writeInt(message.dataType);
            if (message.data != null) {
                buf.writeNbt(message.data.save(new CompoundTag()));
            } else {
                buf.writeNbt(new CompoundTag());
            }
        }

        public static SavedDataSyncMessage decode(FriendlyByteBuf buf) {
            int dataType = buf.readInt();
            CompoundTag nbt = buf.readNbt();
            SavedData data = null;
            if (nbt != null) {
                if (dataType == 0) {
                    MapVariables mv = new MapVariables();
                    mv.read(nbt);
                    data = mv;
                } else {
                    WorldVariables wv = new WorldVariables();
                    wv.read(nbt);
                    data = wv;
                }
            }
            return new SavedDataSyncMessage(dataType, data);
        }

        public static void handle(SavedDataSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                if (message.data != null) {
                    if (message.dataType == 0 && message.data instanceof MapVariables mv)
                        MapVariables.clientSide.read(mv.save(new CompoundTag()));
                    else if (message.dataType == 1 && message.data instanceof WorldVariables wv)
                        WorldVariables.clientSide.read(wv.save(new CompoundTag()));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static class PlayerVariables {
        public double Player_Speed = 0;

        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("Player_Speed", Player_Speed);
            return nbt;
        }

        public void deserializeNBT(CompoundTag nbt) {
            Player_Speed = nbt.getDouble("Player_Speed");
        }

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                GtsNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
        }
    }

    public static class PlayerVariablesSyncMessage {
        final PlayerVariables data;

        public PlayerVariablesSyncMessage(PlayerVariables data) {
            this.data = data;
        }

        public static void encode(PlayerVariablesSyncMessage message, FriendlyByteBuf buf) {
            buf.writeNbt(message.data.serializeNBT());
        }

        public static PlayerVariablesSyncMessage decode(FriendlyByteBuf buf) {
            PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
            message.data.deserializeNBT(buf.readNbt());
            return message;
        }

        public static void handle(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.getCapability(PlayerVariablesCapability.PLAYER_VARIABLES)
                            .ifPresent(vars -> vars.deserializeNBT(message.data.serializeNBT()));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

