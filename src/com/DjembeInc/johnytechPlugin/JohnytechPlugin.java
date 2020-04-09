package com.DjembeInc.johnytechPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class JohnytechPlugin  extends JavaPlugin{
	
	private ControlEntityHandler puppeter = ControlEntityHandler.getInstance();
	
    @Override
    public void onEnable() {
    	getServer().getPluginManager().registerEvents(new ClickEntityListener(), this);
    	
    	getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
    	
    	this.getCommand("see_puppeters").setExecutor(new SeePuppeter());
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

	public ControlEntityHandler getPuppeter() {
		return puppeter;
	}
}
