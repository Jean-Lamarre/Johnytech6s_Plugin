package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
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
    public void perform(Player p, String[] args) {
        UUID playerID = p.getUniqueId();

        if (DMHandler.getInstance().isPlayerDm(playerID) /*&& p.hasPermission("dm.*****")*/) {
            if(!(dmh.isSessionStarted())){
                p.sendMessage("The session need to be started to end it.");
            }
            else{
                dmh.endSession(dmh.getDm(playerID));
            }
        } else {
            p.sendMessage("You need to be DM to end session.");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }


}

