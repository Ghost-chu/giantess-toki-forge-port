package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.CandyConfigGuiMenu;
import net.mcreator.gts.procedures.IncreaseCandyBreachAggressionProcedure;
import net.mcreator.gts.procedures.DisableCandyVoreProcedure;
import net.mcreator.gts.procedures.DisableCandyUpperSwipeProcedure;
import net.mcreator.gts.procedures.DisableCandyUpperSmashProcedure;
import net.mcreator.gts.procedures.DisableCandyThunderMagicProcedure;
import net.mcreator.gts.procedures.DisableCandyStompProcedure;
import net.mcreator.gts.procedures.DisableCandySpitProcedure;
import net.mcreator.gts.procedures.DisableCandyKickProcedure;
import net.mcreator.gts.procedures.DisableCandyHeartMagicProcedure;
import net.mcreator.gts.procedures.DisableCandyGroundSlamProcedure;
import net.mcreator.gts.procedures.DisableCandyBreachProcedure;
import net.mcreator.gts.procedures.DecreaseCandyBreachAggressionProcedure;
import net.mcreator.gts.procedures.CandyConfigMainMenuProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

public record CandyConfigGuiButtonMessage(int buttonID, int x, int y, int z) {
	public static void encode(CandyConfigGuiButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static CandyConfigGuiButtonMessage decode(FriendlyByteBuf buffer) {
		return new CandyConfigGuiButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final CandyConfigGuiButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = CandyConfigGuiMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			CandyConfigMainMenuProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DisableCandyStompProcedure.execute(world);
		}
		if (buttonID == 2) {

			DisableCandyGroundSlamProcedure.execute(world);
		}
		if (buttonID == 3) {

			DisableCandyKickProcedure.execute(world);
		}
		if (buttonID == 4) {

			DisableCandyUpperSwipeProcedure.execute(world);
		}
		if (buttonID == 5) {

			DisableCandyUpperSmashProcedure.execute(world);
		}
		if (buttonID == 6) {

			DisableCandyBreachProcedure.execute(world);
		}
		if (buttonID == 7) {

			DisableCandyVoreProcedure.execute(world);
		}
		if (buttonID == 8) {

			DisableCandySpitProcedure.execute(world);
		}
		if (buttonID == 9) {

			DisableCandyHeartMagicProcedure.execute(world);
		}
		if (buttonID == 10) {

			DisableCandyThunderMagicProcedure.execute(world);
		}
		if (buttonID == 11) {

			IncreaseCandyBreachAggressionProcedure.execute(world);
		}
		if (buttonID == 12) {

			DecreaseCandyBreachAggressionProcedure.execute(world);
		}
	}
}


