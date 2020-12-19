package io.github.johnytech6;

import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.DmCommand;
import io.github.johnytech6.dm.commands.GetPlayerPosition;
import io.github.johnytech6.dm.commands.StatJohnytech;
import io.github.johnytech6.hero.commands.HeroCommand;
import io.github.johnytech6.listener.*;
import io.github.johnytech6.util.PluginStat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//
//****************
//**SUBJECT MAIN**
//****************
//
public class JohnytechPlugin extends JavaPlugin{
	
	private Plugin pluginInstance;

	private PluginHandler pluginHandler;
	private PluginStat pluginStat;

	@Override
    public void onEnable() {

    	pluginInstance = this;

		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();

		pluginHandler = new PluginHandler(pluginInstance, config);

		pluginHandler.loadOfflinePlayer(config);

		pluginStat = new PluginStat(pluginHandler);

    	registerListener(this.getServer().getPluginManager());
		registerCommand();
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
		//save config?
		getLogger().info("Johnytech6's plugin has been disabled.");
    }

    private void registerListener(PluginManager pluginManager){
		pluginManager.registerEvents(new ClickEntityListener(pluginHandler), this);
		pluginManager.registerEvents(new PlayerToggleSneakListener(pluginHandler), this);
		pluginManager.registerEvents(new PlayerJoinListener(pluginHandler), this);
		pluginManager.registerEvents(new PlayerInteractArmorStandArmor(pluginHandler), this);
		pluginManager.registerEvents(new DmInteractChest(pluginHandler), this);
	}

	private void registerCommand(){
		this.getCommand("dm").setExecutor(new DmCommand(pluginHandler));
		this.getCommand("hero").setExecutor(new HeroCommand(pluginHandler));
		this.getCommand("getPlayerPosition").setExecutor(new GetPlayerPosition());
		this.getCommand("stat_Johnytech6Plugin").setExecutor(new StatJohnytech(pluginStat));
	}

}
