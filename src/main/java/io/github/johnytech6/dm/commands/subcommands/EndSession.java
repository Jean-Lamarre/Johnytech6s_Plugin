package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

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
        if (DMHandler.getInstance().isPlayerDm(p.getName()) /*&& p.hasPermission("dm.*****")*/) {
            if(!(dmh.isSessionStarted())){
                p.sendMessage("The session need to be started to end it.");
            }
            else{
                dmh.endSession(dmh.getDm(p.getName()));
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

