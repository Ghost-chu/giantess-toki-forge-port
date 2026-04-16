package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.TokiConfigMenu1Menu;
import net.mcreator.gts.procedures.TokiConfigMainMenuProcedure;
import net.mcreator.gts.procedures.IncreaseBreachAggressionProcedure;
import net.mcreator.gts.procedures.DisableVoreProcedure;
import net.mcreator.gts.procedures.DisableUpperBreachProcedure;
import net.mcreator.gts.procedures.DisableTrappedBreachProcedure;
import net.mcreator.gts.procedures.DisableThrowProcedure;
import net.mcreator.gts.procedures.DisableStompProcedure;
import net.mcreator.gts.procedures.DisableSpitProcedure;
import net.mcreator.gts.procedures.DisableSniperProcedure;
import net.mcreator.gts.procedures.DisableMiddleBreachProcedure;
import net.mcreator.gts.procedures.DisableLowerBreachProcedure;
import net.mcreator.gts.procedures.DisableKickProcedure;
import net.mcreator.gts.procedures.DisableGunSmashProcedure;
import net.mcreator.gts.procedures.DisableGunSlapProcedure;
import net.mcreator.gts.procedures.DisableGroundSlamProcedure;
import net.mcreator.gts.procedures.DisableDroneProcedure;
import net.mcreator.gts.procedures.DisableCelebrationProcedure;
import net.mcreator.gts.procedures.DecreaseBreachAggresionProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

public record TokiConfigMenu1ButtonMessage(int buttonID, int x, int y, int z) {
	public static void encode(TokiConfigMenu1ButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static TokiConfigMenu1ButtonMessage decode(FriendlyByteBuf buffer) {
		return new TokiConfigMenu1ButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final TokiConfigMenu1ButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = TokiConfigMenu1Menu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			TokiConfigMainMenuProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DisableStompProcedure.execute(world);
		}
		if (buttonID == 2) {

			DisableKickProcedure.execute(world);
		}
		if (buttonID == 3) {

			DisableGroundSlamProcedure.execute(world);
		}
		if (buttonID == 4) {

			DisableGunSlapProcedure.execute(world);
		}
		if (buttonID == 5) {

			DisableGunSmashProcedure.execute(world);
		}
		if (buttonID == 6) {

			DisableThrowProcedure.execute(world);
		}
		if (buttonID == 7) {

			DisableVoreProcedure.execute(world);
		}
		if (buttonID == 8) {

			DisableSniperProcedure.execute(world);
		}
		if (buttonID == 9) {

			DisableSpitProcedure.execute(world);
		}
		if (buttonID == 10) {

			DisableDroneProcedure.execute(world);
		}
		if (buttonID == 11) {

			DisableUpperBreachProcedure.execute(world);
		}
		if (buttonID == 12) {

			DisableMiddleBreachProcedure.execute(world);
		}
		if (buttonID == 13) {

			DisableLowerBreachProcedure.execute(world);
		}
		if (buttonID == 14) {

			DisableTrappedBreachProcedure.execute(world);
		}
		if (buttonID == 15) {

			DisableCelebrationProcedure.execute(world);
		}
		if (buttonID == 16) {

			IncreaseBreachAggressionProcedure.execute(world);
		}
		if (buttonID == 17) {

			DecreaseBreachAggresionProcedure.execute(world);
		}
	}
}


