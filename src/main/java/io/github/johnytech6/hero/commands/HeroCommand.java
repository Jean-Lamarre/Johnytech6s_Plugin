package io.github.johnytech6.hero.commands;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.dm.commands.subcommands.*;
import io.github.johnytech6.hero.commands.subcommands.SetChair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HeroCommand implements TabExecutor {

    DMHandler dmh = DMHandler.getInstance();

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public HeroCommand() {
        subcommands.add(new SetChair());

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubCommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(p, args);
                    }
                }
            }
        }
		/*---------------Logic for set room---------------
		if(dmh.isPlayerDm(p.getName()) && sender.hasPermission("dm.mode")) {
			p.sendMessage("Setting dnd room location to " + p.getName() + "position.");
			return dmh.setDndRoomLocation(p.getLocation());
		}else {
			p.sendMessage("You need to be DM to set the DnD room.");
			return true;
		}*/
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }


        return null;
    }
}
