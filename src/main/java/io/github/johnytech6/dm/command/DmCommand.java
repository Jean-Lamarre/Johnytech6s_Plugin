package io.github.johnytech6.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import io.github.johnytech6.dm.DMHandler;

import java.util.ArrayList;
import java.util.List;

public class DmCommand implements TabExecutor {

	DMHandler dmh = DMHandler.getInstance();

	private ArrayList<SubCommand> subcommands = new ArrayList<>();

	public DmCommand(){
		//subcommands.add(new invisibility_toggle());
		//subcommands.add(new mode_toggle());
		//subcommands.add(new nightVision_toggle());
		//subcommands.add(new puppeterMode_toggle());
		//subcommands.add(new teftMode_toggle());
		//subcommands.add(new setRoom());
		//subcommands.add(new endSession());
		//subcommands.add(new startSession());
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		/*Logic for set room
		if(dmh.isPlayerDm(p.getName()) && sender.hasPermission("dm.mode")) {
			p.sendMessage("Setting dnd room location to " + p.getName() + "position.");
			return dmh.setDndRoomLocation(p.getLocation());
		}else {
			p.sendMessage("You need to be DM to set the DnD room.");
			return true;
		}*/
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
		return null;
	}
}
