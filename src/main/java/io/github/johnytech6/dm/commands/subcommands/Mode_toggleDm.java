package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.HeroHandler;
import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.theft.TeftHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Mode_toggleDm extends SubCommand {

    DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "mode_toggleDm";
    }

    @Override
    public String getDescription() {
        return "Toggle the dm mode on you or another player.";
    }

    @Override
    public String getSyntax() {
        return "/dm mode_toggleDm [player]";
    }

    @Override
    public void perform(Player p, String[] args) {

        dmh.ToggleDmMode(p);

        if (p.hasPermission("dm.mode")) {
            if(args.length == 2){
                Player targetPlayer = dmh.getDm(args[1]);
                dmh.setDmInvisibility(targetPlayer, (dmh.isPlayerDm(p.getName())));
                dmh.setDmVision(targetPlayer, (dmh.isPlayerDm(p.getName())));
                PuppeterHandler.getInstance().setPuppeterMode(targetPlayer, (dmh.isPlayerDm(p.getName())));
                TeftHandler.getInstance().setTeftMode(targetPlayer, true);
            }
            else{
                dmh.setDmInvisibility(p, (dmh.isPlayerDm(p.getName())));
                dmh.setDmVision(p, (dmh.isPlayerDm(p.getName())));
                PuppeterHandler.getInstance().setPuppeterMode(p, (dmh.isPlayerDm(p.getName())));
                TeftHandler.getInstance().setTeftMode(p, true);
            }
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

