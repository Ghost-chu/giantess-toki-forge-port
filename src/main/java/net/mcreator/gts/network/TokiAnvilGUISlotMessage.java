package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.TokiAnvilGUIMenu;
import net.mcreator.gts.procedures.TokiAnvilOutputSlotProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

public record TokiAnvilGUISlotMessage(int slotID, int x, int y, int z, int changeType, int meta) {
	public static void encode(TokiAnvilGUISlotMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.slotID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		buffer.writeInt(message.changeType);
		buffer.writeInt(message.meta);
	}

	public static TokiAnvilGUISlotMessage decode(FriendlyByteBuf buffer) {
		return new TokiAnvilGUISlotMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final TokiAnvilGUISlotMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
				Player entity = context.getSender();
				int slotID = message.slotID;
				int changeType = message.changeType;
				int meta = message.meta;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleSlotAction(entity, slotID, changeType, meta, x, y, z);
			});
		context.setPacketHandled(true);
	}

	public static void handleSlotAction(Player entity, int slot, int changeType, int meta, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = TokiAnvilGUIMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (slot == 1 && changeType == 1) {

			TokiAnvilOutputSlotProcedure.execute(entity);
		}
	}
}


