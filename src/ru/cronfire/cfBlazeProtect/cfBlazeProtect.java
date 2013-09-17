package ru.cronfire.cfBlazeProtect;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * COPYRIGHT (c) 2013 Zeluboba (Roman Zabaluev)
 * This file is part of cfBlazeProtect
 * Package: ru.cronfire.cfBlazeProtect
 * Date: 03.06.2013
 * Time: 20:28
 * DO NOT DISTRIBUTE.
 */

public class cfBlazeProtect extends JavaPlugin implements Listener {

	public void onEnable() {
		loadConfig();
		reloadConfig();

		getServer().getPluginManager().registerEvents(this, this);

		getLogger().info("Enabled cfBlazeProtect v" + getDescription().getVersion());
	}

	public void onDisable() {
		getLogger().info("Disabled cfBlazeProtect v" + getDescription().getVersion());
	}

	public void loadConfig() {
		getConfig().addDefault("Message", "&cYou are not allowed to do it!");

		getConfig().options().copyDefaults(true);
		saveConfig();
		getLogger().info("Successfully loaded configuration file.");
	}

	//TODO:equals()
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void blazeProtect(final BlockBreakEvent event) {
		final Player pl = event.getPlayer();

		if(pl.getWorld().getEnvironment().equals(Environment.NETHER)) {
			if(event.getBlock().getType() == Material.MOB_SPAWNER) {
				final CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

				if((spawner.getSpawnedType() == EntityType.BLAZE) && (!pl.hasPermission("cfbundle.blazeprotect.bypass"))) {
					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.BlazeProtect")));
					event.setCancelled(true);
				}
			}
		}
	}

}
