package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SetChair extends SubCommand {

    PluginHandler ph = PluginHandler.getInstance();

    @Override
    public String getName() {
        return "set_chair";
    }

    @Override
    public String getDescription() {
        return "Set chair of a dm or player in the dnd room";
    }

    @Override
    public String getSyntax() {
        return "/dm set_chair [<PlayerName>]";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = PluginHandler.getInstance().getDndPlayer(playerID);

            if (ph.isPlayerDndPlayer(p.getUniqueId())) {
                DndPlayer dndP = ph.getDndPlayer(p.getUniqueId());
                Location targetLocation;
                if (args.length == 4 && isDouble(args[1]) && isDouble(args[2]) && isDouble(args[3])) {
                    targetLocation = new Location(p.getPlayer().getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                } else {
                    targetLocation = p.getLocation();
                }
                dndP.setChairPosition(targetLocation);

            } else {
                p.sendMessage("You are not a DndPlayer ask Jean for help.");
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("[<PlayerName>]");
            return arguments;
        }
        return null;
    }


}

