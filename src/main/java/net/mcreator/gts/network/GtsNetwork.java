package net.mcreator.gts.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GtsNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("gts", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++,
                GtsModVariables.SavedDataSyncMessage.class,
                GtsModVariables.SavedDataSyncMessage::encode,
                GtsModVariables.SavedDataSyncMessage::decode,
                GtsModVariables.SavedDataSyncMessage::handle);

        CHANNEL.registerMessage(id++,
                GtsModVariables.PlayerVariablesSyncMessage.class,
                GtsModVariables.PlayerVariablesSyncMessage::encode,
                GtsModVariables.PlayerVariablesSyncMessage::decode,
                GtsModVariables.PlayerVariablesSyncMessage::handle);

        CHANNEL.registerMessage(id++,
                BreachAttackMessage.class,
                BreachAttackMessage::encode,
                BreachAttackMessage::decode,
                BreachAttackMessage::handleData);

        CHANNEL.registerMessage(id++,
                RegularAttackMessage.class,
                RegularAttackMessage::encode,
                RegularAttackMessage::decode,
                RegularAttackMessage::handleData);

        CHANNEL.registerMessage(id++,
                PlayerStruggleKeyMessage.class,
                PlayerStruggleKeyMessage::encode,
                PlayerStruggleKeyMessage::decode,
                PlayerStruggleKeyMessage::handleData);

        CHANNEL.registerMessage(id++,
                TokiConfigMenu1ButtonMessage.class,
                TokiConfigMenu1ButtonMessage::encode,
                TokiConfigMenu1ButtonMessage::decode,
                TokiConfigMenu1ButtonMessage::handleData);

        CHANNEL.registerMessage(id++,
                TokiAnvilGUISlotMessage.class,
                TokiAnvilGUISlotMessage::encode,
                TokiAnvilGUISlotMessage::decode,
                TokiAnvilGUISlotMessage::handleData);

        CHANNEL.registerMessage(id++,
                TamedGtsInteractionMenuButtonMessage.class,
                TamedGtsInteractionMenuButtonMessage::encode,
                TamedGtsInteractionMenuButtonMessage::decode,
                TamedGtsInteractionMenuButtonMessage::handleData);

        CHANNEL.registerMessage(id++,
                AraConfigGUIButtonMessage.class,
                AraConfigGUIButtonMessage::encode,
                AraConfigGUIButtonMessage::decode,
                AraConfigGUIButtonMessage::handleData);

        CHANNEL.registerMessage(id++,
                CandyConfigGuiButtonMessage.class,
                CandyConfigGuiButtonMessage::encode,
                CandyConfigGuiButtonMessage::decode,
                CandyConfigGuiButtonMessage::handleData);

        CHANNEL.registerMessage(id++,
                GtsCommonGUIButtonMessage.class,
                GtsCommonGUIButtonMessage::encode,
                GtsCommonGUIButtonMessage::decode,
                GtsCommonGUIButtonMessage::handleData);

        CHANNEL.registerMessage(id++,
                GtsCommonGuiPage2ButtonMessage.class,
                GtsCommonGuiPage2ButtonMessage::encode,
                GtsCommonGuiPage2ButtonMessage::decode,
                GtsCommonGuiPage2ButtonMessage::handleData);
    }
}


