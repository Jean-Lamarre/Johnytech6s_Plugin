package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetRoom extends SubCommand {

    DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "setRoom";
    }

    @Override
    public String getDescription() {
        return "Set the room of the dnd table.";
    }

    @Override
    public String getSyntax() {
        return "/dm setRoom [<x> <y> <z>]";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (dmh.isPlayerDm(p.getName()) /*&& p.hasPermission("dm.********")*/) {
            if (args.length > 1 || args.length < 4) {
                p.sendMessage("Missing coordinate arguments.");
            } else if (args.length == 4 && isDouble(args[2]) && isDouble(args[3]) && isDouble(args[4])) {
                Location targetLocation = new Location(p.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
                dmh.setDndRoom(targetLocation);
            }else if(!(isDouble(args[2])) || !(isDouble(args[3])) || !(isDouble(args[4]))){
                p.sendMessage("Incorrect coordinate arguments.");
            }
            else{
                dmh.setDndRoom(p.getLocation());
            }
        } else {
            p.sendMessage("You need to be DM to set the dnd room.");
        }
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

