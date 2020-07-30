package io.github.johnytech6.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.DMHandler;

public class ToggleDmInvisibility implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		
		if(DMHandler.getInstance().isPlayerDm(p.getName()) && sender.hasPermission("dm.mode.invisibility")) {
		return DMHandler.getInstance().DmInvisibility(p);
		}
		else {
			p.sendMessage("You need to be DM to toggle invisibility.");
		return true;
		}
	}
}
