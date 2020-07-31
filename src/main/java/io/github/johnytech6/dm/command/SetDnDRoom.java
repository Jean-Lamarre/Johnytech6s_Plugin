package io.github.johnytech6.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.github.johnytech6.dm.DMHandler;

public class SetDnDRoom implements  CommandExecutor{

	DMHandler dmh = DMHandler.getInstance();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;

		if(dmh.isPlayerDm(p.getName()) && sender.hasPermission("dm.mode")) {
			p.sendMessage("Setting dnd room location to " + p.getName() + "position.");
			return dmh.setDndRoomLocation(p.getLocation());
		}else {
			p.sendMessage("You need to be DM to set the DnD room.");
			return true;
		}
	}

}
