package com.DjembeInc.johnytechPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetEntityCage implements  CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;
		ControlEntityHandler.setTrueEntityLocation(p.getLocation());
		return true;
	}
}
