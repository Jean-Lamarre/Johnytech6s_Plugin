package io.github.johnytech6.hero.commands.subcommands;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetChair extends SubCommand {

    DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "set_chair";
    }

    @Override
    public String getDescription() {
        return "Set your chair in the dnd room";
    }

    @Override
    public String getSyntax() {
        return "/hero set_chair ";
    }

    @Override
    public void perform(Player p, String[] args) {


        /*
        if (dmh.isPlayerDm(p.getName()) && p.hasPermission("dm.********")) {
            if (args.length == 1) {
                dmh.setDndRoom(p.getLocation());
                p.sendMessage("Dnd room set to your location.");
            } else if (args.length == 4 && isDouble(args[1]) && isDouble(args[2]) && isDouble(args[3])) {
                Location targetLocation = new Location(p.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                dmh.setDndRoom(targetLocation);
                p.sendMessage("Dnd room set to your " + Integer.parseInt(args[1]) + ", " + Integer.parseInt(args[2]) + ", " + Integer.parseInt(args[3]));
            } else {
                p.sendMessage("Incorrect arguments.");
            }
        } else {
            p.sendMessage("You need to be DM to set the dnd room.");
        }
        */
        p.sendMessage("not functional command");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> arguments = new ArrayList<>();
            arguments.add("[<x> <y> <z>]");
            return arguments;
        } else if (args.length == 3 && isDouble(args[1])) {
            List<String> arguments = new ArrayList<>();
            arguments.add("<y> <z>");
            return arguments;
        } else if (args.length == 4 && isDouble(args[2])) {
            List<String> arguments = new ArrayList<>();
            arguments.add("<z>");
            return arguments;
        }
        return null;
    }


}

