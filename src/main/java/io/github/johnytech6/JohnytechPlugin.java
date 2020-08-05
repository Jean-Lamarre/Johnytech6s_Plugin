package io.github.johnytech6;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.johnytech6.dm.commands.*;
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
		this.getCommand("dm").setExecutor(new DmCommand());
		this.getCommand("getPlayerPosition").setExecutor(new GetPlayerPosition());
    	this.getCommand("stat_Johnytech6Plugin").setExecutor(new StatJohnytech());

    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
    	return pluginInstance;
    }
    
	
}
