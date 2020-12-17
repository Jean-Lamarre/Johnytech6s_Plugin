package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class EndSession extends SubCommand {

    private static DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "end_session";
    }

    @Override
    public String getDescription() {
        return "End the dnd session";
    }

    @Override
    public String getSyntax() {
        return "/dm end_session";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = PluginHandler.getInstance().getDndPlayer(playerID);

            if (dmh.isPlayerDm(playerID)) {
                if (!(dmh.isSessionStarted())) {
                    p.sendMessage("The session need to be started to end it.");
                } else {
                    dmh.endSession(dmh.getDm(playerID));
                }
            } else {
                p.sendMessage("You need to be DM to end session.");
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }


}

