package io.github.johnytech6.dm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.theft.TeftHandler;

public class ToggleDmMode implements CommandExecutor {

	DMHandler dmh = DMHandler.getInstance();
	PuppeterHandler ph = PuppeterHandler.getInstance();
	TeftHandler th = TeftHandler.getInstance();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;

		boolean stateCommand = dmh.ToggleDmMode(p);

		if (sender.hasPermission("dm.mode")) {
			dmh.setDmInvisibility(p, (dmh.isPlayerDm(p.getName())));

			dmh.setDmVision(p, (dmh.isPlayerDm(p.getName())));

			ph.setPuppeterMode(p, (dmh.isPlayerDm(p.getName())));
			
			th.setTeftMode(p, true);
		}

		return stateCommand;
	}
}
