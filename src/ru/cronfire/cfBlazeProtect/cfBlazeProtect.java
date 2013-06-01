package ru.cronfire.cfBlazeProtect;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class cfBlazeProtect extends JavaPlugin implements Listener {
	
	  public static Logger log = Logger.getLogger("Minecraft");
	  public static PluginDescriptionFile pdfFile;
	  public FileConfiguration config;

public void onEnable() {
	try {
		if (!getDataFolder().exists()) {
		    log.info("[cfBlazeProtect] DataFolder not found, creating a new one.");
		    getDataFolder().mkdir();
		    }
		    } catch (Exception ex) {
				ex.printStackTrace();
		    }

	this.config = getConfig();
	loadConfig();
	reloadConfig();

	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(new cfBlazeProtect(), this);

	pdfFile = getDescription();
	PluginDescriptionFile pdfFile = getDescription();

	log.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is enabled!");
}

public void onDisable() {
	log.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is disabled!");
}
		  
public void loadConfig() {
	this.config = getConfig(); 
	this.config.addDefault("message", "You are not allowed to do it!");

	getConfig().options().copyDefaults(true);
	saveConfig();
	log.info("[cfBlazeProtect] Successfully loaded configuration file.");
}

@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
public void onBlockBreak(BlockBreakEvent event) {
	
	if(event.getPlayer().getWorld().getName().contains("nether")) { //equalsIgnoreCase("world_nether") // special for bukkit users
		if (event.getBlock().getType() == Material.MOB_SPAWNER) {
        	CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

        	if ((spawner.getSpawnedType() == EntityType.BLAZE) && (!event.getPlayer().hasPermission("blazeprotect.exempt"))) {
            	event.getPlayer().sendMessage(ChatColor.RED + getConfig().getString("message"));
            	event.setCancelled(true);
        	}
    	}
	}
    
}
		
}
