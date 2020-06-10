package com.DjembeInc.johnytechPlugin.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

public class TogglePuppeterMode implements  CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;
		
		if(DMHandler.getInstance().isPlayerDm(p.getName()) && sender.hasPermission("dm.mode.puppeter")) {
			return PuppeterHandler.getInstance().TogglePuppeterMode(p);
		}else {
			return true;
		}
 
	}
	
}
