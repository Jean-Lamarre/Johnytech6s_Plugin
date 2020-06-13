package com.DjembeInc.johnytechPlugin.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.theft.TeftHandler;

public class ToggleTeftMode implements  CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;
		
		if(DMHandler.getInstance().isPlayerDm(p.getName()) && sender.hasPermission("dm.mode.teft")) {
			return TeftHandler.getInstance().ToggleTeftMode(p);
		}else {
			p.sendMessage("You need to be DM to toggle teft power.");
			return true;
		}
 
	}
	
}
