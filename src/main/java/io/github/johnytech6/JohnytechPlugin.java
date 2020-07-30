package io.github.johnytech6;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.johnytech6.dm.command.*;
import io.github.johnytech6.listener.ClickEntityListener;
import io.github.johnytech6.listener.PlayerInteractArmorStandArmor;
import io.github.johnytech6.listener.PlayerJoinListener;
import io.github.johnytech6.listener.PlayerLeaveListener;
import io.github.johnytech6.listener.PlayerToggleSneakListener;
//
//****************
//**SUBJECT MAIN**
//****************
//
public class JohnytechPlugin  extends JavaPlugin{
	
	private static Plugin pluginInstance;
	
    @Override
    public void onEnable() {
    	
    	pluginInstance = this;
    	
    	//Register all Events
    	getServer().getPluginManager().registerEvents(new ClickEntityListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerInteractArmorStandArmor(), this);
    	getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);

    	
    	//Set all commands
    	this.getCommand("stat_Johnytech6Plugin").setExecutor(new StatJohnytech());
    	this.getCommand("dm_puppeter_toggle").setExecutor(new TogglePuppeterMode());
    	this.getCommand("dm_mode_toggle").setExecutor(new ToggleDmMode());
    	this.getCommand("dm_invisibility_toggle").setExecutor(new ToggleDmInvisibility());
    	this.getCommand("dm_vision_toggle").setExecutor(new ToggleDmVision());
    	this.getCommand("dm_teft_toggle").setExecutor(new ToggleTeftMode());
    	
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
    	return pluginInstance;
    }
    
	
}
