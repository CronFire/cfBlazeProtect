package ru.cronfire.cfBlazeProtect;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class cfBlazeProtect extends JavaPlugin implements Listener {
	public static Logger log = Logger.getLogger("Minecraft");
	public FileConfiguration config;

	public void onEnable() {
		loadConfig();
		reloadConfig();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);

		log.info("[cfBlazeProtect]" + " Enabled cfBlazeProtect v" + getDescription().getVersion());
	}

	public void onDisable() {
		log.info("[cfBlazeProtect]" + " Disabled cfBlazeProtect v" + getDescription().getVersion());
	}

	public void loadConfig() {
		this.config = getConfig();
		this.config.addDefault("Message", "&cYou are not allowed to do it!");

		config.options().copyDefaults(true);
		saveConfig();
		log.info("[cfBlazeProtect] Successfully loaded configuration file.");
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void blazeProtect(BlockBreakEvent event) {
		Player pl = event.getPlayer();

		if(pl.getWorld().getEnvironment().equals(Environment.NETHER)) {
			if(event.getBlock().getType() == Material.MOB_SPAWNER) {
				CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

				if((spawner.getSpawnedType() == EntityType.BLAZE) && (!pl.hasPermission("cfbundle.blazeprotect.bypass"))) {
					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.BlazeProtect")));
					event.setCancelled(true);
				}
			}
		}
	}

}
