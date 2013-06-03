package ru.cronfire.cfBlazeProtect;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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


public class cfBlazeProtect extends JavaPlugin implements Listener {
	public static Logger log = Logger.getLogger("Minecraft");
	public FileConfiguration config;

public void onEnable() {
	loadConfig();
	reloadConfig();

	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(this, this);

	log.info("[" + getDescription().getName() + "]" + " Enabled " + getDescription().getName() + " v" + getDescription().getVersion());
}

public void onDisable() {
	log.info("[" + getDescription().getName() + "]" + " Disabled " + getDescription().getName() + " v" + getDescription().getVersion());
}
		  
public void loadConfig() { 
	this.config = getConfig();
	config.addDefault("Message", "You are not allowed to do it!");

	config.options().copyDefaults(true);
	saveConfig();
	log.info("[" + getDescription().getName() + "]" + " Successfully loaded configuration file.");
}

@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
public void onBlockBreak(BlockBreakEvent event) {
	this.config = getConfig();
	String worldname = event.getPlayer().getWorld().getName();
	Player player = event.getPlayer();
	
	if(worldname.equalsIgnoreCase("world_nether") || worldname.contains("nether")) {  //for bukkit users
		if (event.getBlock().getType() == Material.MOB_SPAWNER) {
        	CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        	
        	if ((spawner.getSpawnedType() == EntityType.BLAZE) && (!player.hasPermission("blazeprotect.exempt"))) {
            	player.sendMessage(ChatColor.RED + config.getString("Message"));
            	event.setCancelled(true);
        	}
    	}
	}
    
}
		
}
