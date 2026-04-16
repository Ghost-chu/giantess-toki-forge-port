package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.AraConfigGUIMenu;
import net.mcreator.gts.procedures.IncreaseAraBreachAggressionProcedure;
import net.mcreator.gts.procedures.DisableAraTailSqueezeProcedure;
import net.mcreator.gts.procedures.DisableAraStepProcedure;
import net.mcreator.gts.procedures.DisableAraMiddleTailSlapProcedure;
import net.mcreator.gts.procedures.DisableAraMiddleGrabProcedure;
import net.mcreator.gts.procedures.DisableAraLowTailSwipeProcedure;
import net.mcreator.gts.procedures.DisableAraLowGrabProcedure;
import net.mcreator.gts.procedures.DisableAraEatProcedure;
import net.mcreator.gts.procedures.DisableAraChompProcedure;
import net.mcreator.gts.procedures.DisableAraBreachProcedure;
import net.mcreator.gts.procedures.DisableAraAboveAttackProcedure;
import net.mcreator.gts.procedures.DecreaseAraBreachAggressionProcedure;
import net.mcreator.gts.procedures.AraConfigMainMenuProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

public record AraConfigGUIButtonMessage(int buttonID, int x, int y, int z) {
	public static void encode(AraConfigGUIButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static AraConfigGUIButtonMessage decode(FriendlyByteBuf buffer) {
		return new AraConfigGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final AraConfigGUIButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = AraConfigGUIMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			AraConfigMainMenuProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DisableAraStepProcedure.execute(world);
		}
		if (buttonID == 2) {

			DisableAraLowTailSwipeProcedure.execute(world);
		}
		if (buttonID == 3) {

			DisableAraTailSqueezeProcedure.execute(world);
		}
		if (buttonID == 4) {

			DisableAraLowGrabProcedure.execute(world);
		}
		if (buttonID == 5) {

			DisableAraMiddleTailSlapProcedure.execute(world);
		}
		if (buttonID == 6) {

			DisableAraMiddleGrabProcedure.execute(world);
		}
		if (buttonID == 7) {

			DisableAraChompProcedure.execute(world);
		}
		if (buttonID == 8) {

			DisableAraAboveAttackProcedure.execute(world);
		}
		if (buttonID == 9) {

			DisableAraBreachProcedure.execute(world);
		}
		if (buttonID == 10) {

			DisableAraEatProcedure.execute(world);
		}
		if (buttonID == 11) {

			IncreaseAraBreachAggressionProcedure.execute(world);
		}
		if (buttonID == 12) {

			DecreaseAraBreachAggressionProcedure.execute(world);
		}
	}
}


