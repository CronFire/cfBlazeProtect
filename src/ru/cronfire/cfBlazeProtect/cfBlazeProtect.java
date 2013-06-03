package ru.cronfire.cfBlazeProtect;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class cfBlazeProtect extends JavaPlugin implements Listener {

public void onEnable() {
	loadConfig();
	reloadConfig();

	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(this, this);

	getLogger().info(" version " + getDescription().getVersion() + " is enabled!");
}

public void onDisable() {
	getLogger().info(" version " + getDescription().getVersion() + " is disabled!");
}
		  
public void loadConfig() { 
	getConfig().addDefault("Message", "You are not allowed to do it!");

	getConfig().options().copyDefaults(true);
	saveConfig();
	getLogger().info("Successfully loaded configuration file.");
}

@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
public void onBlockBreak(BlockBreakEvent event) {
	String worldname = event.getPlayer().getWorld().getName();
	Player player = event.getPlayer();
	
	if(worldname.equalsIgnoreCase("world_nether") || worldname.contains("nether")) {  //for bukkit users
		if (event.getBlock().getType() == Material.MOB_SPAWNER) {
        	CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        	
        	if ((spawner.getSpawnedType() == EntityType.BLAZE) && (!player.hasPermission("blazeprotect.exempt"))) {
            	player.sendMessage(ChatColor.RED + getConfig().getString("Message"));
            	event.setCancelled(true);
        	}
    	}
	}
    
}
		
}
