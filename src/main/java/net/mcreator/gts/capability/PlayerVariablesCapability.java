package net.mcreator.gts.capability;

import net.mcreator.gts.network.GtsModVariables;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerVariablesCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<GtsModVariables.PlayerVariables> PLAYER_VARIABLES =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final LazyOptional<GtsModVariables.PlayerVariables> instance =
            LazyOptional.of(GtsModVariables.PlayerVariables::new);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_VARIABLES ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.orElseThrow(IllegalStateException::new).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.orElseThrow(IllegalStateException::new).deserializeNBT(nbt);
    }
}


