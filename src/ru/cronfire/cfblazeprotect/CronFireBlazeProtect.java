package ru.cronfire.cfblazeprotect;

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
 * Package: ru.cronfire.cfblazeprotect
 * Date: 03.06.2013
 * Time: 20:28
 * DO NOT DISTRIBUTE.
 */

@SuppressWarnings("UnusedDeclaration")
public class CronFireBlazeProtect extends JavaPlugin implements Listener {

	public void onEnable() {
		loadConfig();
		reloadConfig();

		getServer().getPluginManager().registerEvents(this, this);

		getLogger().info("Enabled " + getName() + " v" + getDescription().getVersion());
	}

	public void onDisable() {
		getLogger().info("Disabled " + getName() " v" + getDescription().getVersion());
	}

	@SuppressWarnings("WeakerAccess")
	public void loadConfig() {
		getConfig().addDefault("Message", "&cYou are not allowed to do it!");

		getConfig().options().copyDefaults(true);
		saveConfig();
		getLogger().info("Successfully loaded configuration file.");
	}

	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreakEvent(final BlockBreakEvent event) {
		final Player pl = event.getPlayer();

		if(!pl.getWorld().getEnvironment().equals(Environment.NETHER)) {
			return;
		}
		if(!event.getBlock().getType().equals(Material.MOB_SPAWNER)) {
			return;
		}

		final CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

		if((spawner.getSpawnedType().equals(EntityType.BLAZE)) && (!pl.hasPermission("cfbundle.blazeprotect.bypass"))) {
			pl.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.BlazeProtect")));
			event.setCancelled(true);
		}
	}

}
