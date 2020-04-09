package com.DjembeInc.johnytechPlugin;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SeePuppeter implements  CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		ArrayList<String> names = new ArrayList<String>();
		
		ArrayList<ControllingPlayer> cps = ControlEntityHandler.getInstance().getPuppeters();
		
		for(ControllingPlayer cp : cps) {
			names.add(cp.getName());
		}
		
		sender.sendMessage(names.toString());
		return true;
	}

}
