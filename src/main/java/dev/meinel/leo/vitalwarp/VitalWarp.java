/*
 * VitalWarp is a Spigot Plugin that gives players the ability to set warps and teleport to them.
 * Copyright © 2022 Leopold Meinel & contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://github.com/LeoMeinel/VitalWarp/blob/main/LICENSE
 */

package dev.meinel.leo.vitalwarp;

import dev.meinel.leo.vitalwarp.commands.VitalDelWarpCmd;
import dev.meinel.leo.vitalwarp.commands.VitalSetwarpCmd;
import dev.meinel.leo.vitalwarp.commands.VitalWarpCmd;
import dev.meinel.leo.vitalwarp.commands.VitalWarpsCmd;
import dev.meinel.leo.vitalwarp.files.Messages;
import dev.meinel.leo.vitalwarp.storage.WarpStorage;
import dev.meinel.leo.vitalwarp.storage.WarpStorageSql;
import dev.meinel.leo.vitalwarp.storage.WarpStorageYaml;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VitalWarp
		extends JavaPlugin {

	private WarpStorage warpStorage;
	private Messages messages;

	@Override
	public void onEnable() {
		registerCommands();
		saveDefaultConfig();
		setupStorage();
		messages = new Messages();
		Bukkit.getLogger()
		      .info("VitalWarp v" + this.getDescription()
		                                .getVersion() + " enabled");
		Bukkit.getLogger()
		      .info("Copyright (C) 2022 Leopold Meinel");
		Bukkit.getLogger()
		      .info("This program comes with ABSOLUTELY NO WARRANTY!");
		Bukkit.getLogger()
		      .info("This is free software, and you are welcome to redistribute it under certain conditions.");
		Bukkit.getLogger()
		      .info("See https://github.com/LeoMeinel/VitalWarp/blob/main/LICENSE for more details.");
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger()
		      .info("VitalWarp v" + this.getDescription()
		                                .getVersion() + " disabled");
	}

	private void setupStorage() {
		String storageSystem = getConfig().getString("storage-system");
		if (Objects.requireNonNull(storageSystem)
		           .equalsIgnoreCase("mysql")) {
			this.warpStorage = new WarpStorageSql();
		}
		else {
			this.warpStorage = new WarpStorageYaml();
		}
	}

	private void registerCommands() {
		Objects.requireNonNull(getCommand("warp"))
		       .setExecutor(new VitalWarpCmd());
		Objects.requireNonNull(getCommand("warp"))
		       .setTabCompleter(new VitalWarpCmd());
		Objects.requireNonNull(getCommand("warps"))
		       .setExecutor(new VitalWarpsCmd());
		Objects.requireNonNull(getCommand("setwarp"))
		       .setExecutor(new VitalSetwarpCmd());
		Objects.requireNonNull(getCommand("delwarp"))
		       .setExecutor(new VitalDelWarpCmd());
		Objects.requireNonNull(getCommand("delwarp"))
		       .setTabCompleter(new VitalDelWarpCmd());
	}

	public Messages getMessages() {
		return messages;
	}

	public WarpStorage getWarpStorage() {
		return warpStorage;
	}
}

