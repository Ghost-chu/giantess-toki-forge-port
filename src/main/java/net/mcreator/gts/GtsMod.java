package net.mcreator.gts;

import net.mcreator.gts.init.*;
import net.mcreator.gts.network.GtsModVariables;
import net.mcreator.gts.network.GtsNetwork;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod("gts")
public class GtsMod {
    public static final Logger LOGGER = LogManager.getLogger(GtsMod.class);
    public static final String MODID = "gts";

    public GtsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        GtsAttributes.register(modEventBus);
        GtsModSounds.REGISTRY.register(modEventBus);
        GtsModBlocks.REGISTRY.register(modEventBus);
        GtsModItems.REGISTRY.register(modEventBus);
        GtsModEntities.REGISTRY.register(modEventBus);
        GtsModTabs.REGISTRY.register(modEventBus);
        GtsModMobEffects.REGISTRY.register(modEventBus);
        GtsModMenus.REGISTRY.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(GtsModVariables.EventBusVariableHandlers.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        GtsNetwork.register();
    }

    // ---- Server-side deferred work queue ----
    private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        workQueue.add(new Tuple<>(action, tick));
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
        workQueue.forEach(work -> {
            work.setB(work.getB() - 1);
            if (work.getB() == 0)
                actions.add(work);
        });
        actions.forEach(e -> e.getA().run());
        workQueue.removeAll(actions);
    }

    @SubscribeEvent
    public void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getAttribute(GtsAttributes.SAFE_FALL_DISTANCE.get()) != null) {
            double safeDist = entity.getAttributeValue(GtsAttributes.SAFE_FALL_DISTANCE.get());
            float reduction = (float) (safeDist - 3.0);
            if (reduction > 0) {
                event.setDistance(Math.max(0, event.getDistance() - reduction));
            }
        }
    }
}
