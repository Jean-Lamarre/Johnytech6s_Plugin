package com.DjembeInc.johnytechPlugin.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;

public class ToggleDmVision implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;

		if(DMHandler.getInstance().isPlayerDm(p.getName()) && sender.hasPermission("dm.mode.vision")) {
		return DMHandler.getInstance().DmVision(p);
		}
		else {
			return true;
		}
	}

}
