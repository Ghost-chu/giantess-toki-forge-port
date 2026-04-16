package net.mcreator.gts.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import net.mcreator.gts.procedures.TamedGtsPlayerBreachProcedure;
import net.mcreator.gts.GtsMod;

public record BreachAttackMessage(int eventType, int pressedms) {
	public static void encode(BreachAttackMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}

	public static BreachAttackMessage decode(FriendlyByteBuf buffer) {
		return new BreachAttackMessage(buffer.readInt(), buffer.readInt());
	}
	public static void handleData(final BreachAttackMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
				pressAction(context.getSender(), message.eventType, message.pressedms);
			});
		context.setPacketHandled(true);
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			TamedGtsPlayerBreachProcedure.execute(world, entity);
		}
	}
}


