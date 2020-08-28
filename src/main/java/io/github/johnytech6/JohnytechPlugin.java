package io.github.johnytech6;

import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.hero.commands.HeroCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.johnytech6.dm.commands.*;
import io.github.johnytech6.listener.ClickEntityListener;
import io.github.johnytech6.listener.PlayerInteractArmorStandArmor;
import io.github.johnytech6.listener.PlayerJoinListener;
import io.github.johnytech6.listener.PlayerLeaveListener;
import io.github.johnytech6.listener.PlayerToggleSneakListener;

import java.io.File;

//
//****************
//**SUBJECT MAIN**
//****************
//
public class JohnytechPlugin  extends JavaPlugin{
	
	private static Plugin pluginInstance;

	private File customConfigFile;
	private FileConfiguration config;

    @Override
    public void onEnable() {
    	
    	pluginInstance = this;

		config = getConfig();

		config.options().copyDefaults(true);
		saveConfig();

		PluginHandler.getInstance().loadConfig(config);

    	//Register all Events
    	getServer().getPluginManager().registerEvents(new ClickEntityListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerInteractArmorStandArmor(), this);
    	getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);

    	
    	//Set all commands
		this.getCommand("dm").setExecutor(new DmCommand());
		this.getCommand("hero").setExecutor(new HeroCommand());
		this.getCommand("getPlayerPosition").setExecutor(new GetPlayerPosition());
    	this.getCommand("stat_Johnytech6Plugin").setExecutor(new StatJohnytech());

    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
		getLogger().info("Johnytech6's plugin has been disabled.");
    }

    public static Plugin getPlugin() {
    	return pluginInstance;
    }



}
