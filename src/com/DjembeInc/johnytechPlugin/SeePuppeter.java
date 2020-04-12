package com.DjembeInc.johnytechPlugin;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SeePuppeter implements  CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		ControlEntityHandler ceh = ControlEntityHandler.getInstance();
		
		ArrayList<String> namesP = new ArrayList<String>();
		ArrayList<String> namesM = new ArrayList<String>();
		
		ArrayList<ControllingPlayer> cpPs = ceh.getPuppeters();
		ArrayList<ControllingPlayer> cpMs = ceh.getMorphPlayers();
		
		for(ControllingPlayer cp : cpPs) {
			namesP.add(cp.getName());
		}
		for(ControllingPlayer cp : cpMs) {
			namesM.add(cp.getName());
		}
		
		sender.sendMessage("Puppeters: " + namesP.toString());
		sender.sendMessage("Morph puppeters: " + namesM.toString());
		
		return true;
	}

}
