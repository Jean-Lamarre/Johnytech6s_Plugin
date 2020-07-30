package io.github.johnytech6.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;

public class TogglePuppeterMode implements  CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player) sender;
		
		if(DMHandler.getInstance().isPlayerDm(p.getName()) && sender.hasPermission("dm.mode.puppeter")) {
			return PuppeterHandler.getInstance().TogglePuppeterMode(p);
		}else {
			p.sendMessage("You need to be DM to toggle puppeterMode.");
			return true;	
		}
 
	}
	
}
