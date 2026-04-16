package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.TamedGtsInteractionMenuMenu;
import net.mcreator.gts.procedures.TamedGtsSizeIncreaseProcedure;
import net.mcreator.gts.procedures.TamedGtsSizeDecreaseProcedure;
import net.mcreator.gts.procedures.TamedGtsSitProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

public record TamedGtsInteractionMenuButtonMessage(int buttonID, int x, int y, int z) {
	public static void encode(TamedGtsInteractionMenuButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static TamedGtsInteractionMenuButtonMessage decode(FriendlyByteBuf buffer) {
		return new TamedGtsInteractionMenuButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final TamedGtsInteractionMenuButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
				Player entity = context.getSender();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = TamedGtsInteractionMenuMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			TamedGtsSitProcedure.execute(world, entity);
		}
		if (buttonID == 1) {

			TamedGtsSizeIncreaseProcedure.execute(world, entity);
		}
		if (buttonID == 2) {

			TamedGtsSizeDecreaseProcedure.execute(world, entity);
		}
	}
}


