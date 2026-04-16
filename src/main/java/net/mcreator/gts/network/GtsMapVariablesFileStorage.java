package net.mcreator.gts.network;

import net.mcreator.gts.GtsMod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class GtsMapVariablesFileStorage {
	private static final Path FILE_PATH = FMLPaths.CONFIGDIR.get().resolve("gts_mapvars.nbt");

	public static boolean exists() {
		return FILE_PATH.toFile().exists();
	}

	public static void save(GtsModVariables.MapVariables vars) {
		try {
			File file = FILE_PATH.toFile();
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			CompoundTag tag = vars.save(new CompoundTag());
			NbtIo.writeCompressed(tag, file);
		} catch (IOException e) {
			GtsMod.LOGGER.error("Failed to save gts_mapvars.nbt", e);
		}
	}

	public static void loadInto(GtsModVariables.MapVariables vars) {
		try {
			File file = FILE_PATH.toFile();
			if (!file.exists()) {
				return;
			}
			CompoundTag tag = NbtIo.readCompressed(file);
			if (tag != null) {
				vars.read(tag);
			}
		} catch (IOException e) {
			GtsMod.LOGGER.error("Failed to load gts_mapvars.nbt", e);
		}
	}
}
