package net.mcreator.gts;

import net.mcreator.gts.GtsMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GtsAttributes {
    public static final DeferredRegister<Attribute> REGISTRY =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, GtsMod.MODID);

    /**
     * Replacement for vanilla GtsAttributes.SCALE.get() (added in 1.21).
     * Drives visual/hitbox scaling for all GTS entities.
     * Default 3.0, range [1, 15]. Synced to client.
     */
    public static final RegistryObject<Attribute> SCALE =
            REGISTRY.register("scale", () ->
                    new RangedAttribute("attribute.name.gts.scale", 3.0, 1.0, 15.0).setSyncable(true));

    /**
     * Replacement for vanilla GtsAttributes.SAFE_FALL_DISTANCE.get() (added in 1.21).
     * Falls shorter than this (in blocks) deal no damage.
     * Default 3.0 (vanilla behavior).
     */
    public static final RegistryObject<Attribute> SAFE_FALL_DISTANCE =
            REGISTRY.register("safe_fall_distance", () ->
                    new RangedAttribute("attribute.name.gts.safe_fall_distance", 3.0, 0.0, 1024.0).setSyncable(true));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
