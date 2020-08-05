package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StartSession extends SubCommand {

    DMHandler dmh = DMHandler.getInstance();

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
    public void perform(Player p, String[] args) {
        //TODO
        if (dmh.isPlayerDm(p.getName()) /*&& p.hasPermission("dm.*****")*/) {
            if(dmh.isSessionStarted()){
                p.sendMessage("The session is already started ");
            }
            else{
                dmh.startSession(p);
            }
        } else {
            p.sendMessage("You need to be DM to start session.");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }


}

