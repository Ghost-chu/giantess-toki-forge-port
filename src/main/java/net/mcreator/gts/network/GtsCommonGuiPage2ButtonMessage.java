package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.gts.world.inventory.GtsCommonGuiPage2Menu;
import net.mcreator.gts.procedures.MainPage2PreviousPageProcedure;
import net.mcreator.gts.procedures.DisableTamedSeekOutHostileMobProcedure;
import net.mcreator.gts.procedures.DisableTamedFriendlyFireProcedure;
import net.mcreator.gts.procedures.DisableTamedBreachProcedure;
import net.mcreator.gts.procedures.DisableEatAttacksDependonHealthProcedure;
import net.mcreator.gts.procedures.DisableBiomeSpawnProcedure;
import net.mcreator.gts.procedures.ChanceBasedGrabEscapeProcedure;
import net.mcreator.gts.GtsMod;

import java.util.HashMap;

public record GtsCommonGuiPage2ButtonMessage(int buttonID, int x, int y, int z) {
	public static void encode(GtsCommonGuiPage2ButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static GtsCommonGuiPage2ButtonMessage decode(FriendlyByteBuf buffer) {
		return new GtsCommonGuiPage2ButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final GtsCommonGuiPage2ButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = GtsCommonGuiPage2Menu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			MainPage2PreviousPageProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			DisableTamedFriendlyFireProcedure.execute(world);
		}
		if (buttonID == 2) {

			DisableTamedSeekOutHostileMobProcedure.execute(world);
		}
		if (buttonID == 3) {

			DisableTamedBreachProcedure.execute(world);
		}
		if (buttonID == 4) {

			DisableEatAttacksDependonHealthProcedure.execute(world);
		}
		if (buttonID == 5) {

			ChanceBasedGrabEscapeProcedure.execute(world);
		}
		if (buttonID == 6) {

			DisableBiomeSpawnProcedure.execute(world);
		}
	}
}


