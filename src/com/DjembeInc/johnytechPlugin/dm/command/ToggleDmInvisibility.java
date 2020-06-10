package com.DjembeInc.johnytechPlugin.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;

public class ToggleDmInvisibility implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		
		if(DMHandler.getInstance().isPlayerDm(p.getName()) && sender.hasPermission("dm.mode.invisibility")) {
		return DMHandler.getInstance().DmInvisibility(p);
		}
		else {
		return true;
		}
	}
}
