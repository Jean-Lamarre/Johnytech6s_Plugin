package io.github.johnytech6.dm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.johnytech6.PluginHandler;

public class StatJohnytech implements  CommandExecutor{

	private PluginHandler ph = PluginHandler.getInstance();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;
		if(sender.hasPermission("dm.mode")) {
			ph.johnytech6Stat(p);
		}		
		
		return true;
		
	}

}
