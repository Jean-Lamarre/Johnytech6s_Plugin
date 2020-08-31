package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.Handler.PuppeterHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PuppeterMode_toggle extends SubCommand {

    private static PuppeterHandler ph = PuppeterHandler.getInstance();
    private static DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "puppeter_mode_toggle";
    }

    @Override
    public String getDescription() {
        return "Toggle your puppeter mode as a dm or the puppeter mode of another dm.";
    }

    @Override
    public String getSyntax() {
        return "/dm puppeter_mode_toggle [player]";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(dmh.isPlayerDm(p.getName()) && p.hasPermission("dm.mode.puppeter")) {
            Dm targetDm;
            if(args.length == 2) {
                targetDm = dmh.getDm(args[1]);
                ph.TogglePuppeterMode((Player)targetDm, true);
                if(ph.isPlayerPuppeter(p.getName())){
                    p.sendMessage(args[1] + " has now puppeter's power.");
                }
                else{
                    p.sendMessage(args[1] + " lost puppeter's power.");
                }
            }
            else{
                targetDm = dmh.getDm(p.getName());
                ph.TogglePuppeterMode(p, true);
            }
            targetDm.setPuppeterPower(!targetDm.hasPuppeterPower());

        }else {
            p.sendMessage("You need to be DM to toggle puppeterMode.");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }

            return playerNames;
        }
        return null;
    }


}

