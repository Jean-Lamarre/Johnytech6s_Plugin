package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class StartSession extends SubCommand {

    private static DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "start_session";
    }

    @Override
    public String getDescription() {
        return "Start a new dnd session.";
    }

    @Override
    public String getSyntax() {
        return "/dm start_session";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = PluginHandler.getInstance().getDndPlayer(playerID);

            if (dmh.isPlayerDm(playerID) /*&& p.hasPermission("dm.*****")*/) {
                if (dmh.isSessionStarted()) {
                    p.sendMessage("The session is already started ");
                } else {
                    dmh.startSession(dmh.getDm(playerID));
                }
            } else {
                p.sendMessage("You need to be DM to start session.");
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }


}

