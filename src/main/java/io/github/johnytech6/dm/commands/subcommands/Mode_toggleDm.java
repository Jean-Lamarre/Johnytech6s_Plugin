package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.theft.TeftHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Mode_toggleDm extends SubCommand {

    private static DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "mode_toggle";
    }

    @Override
    public String getDescription() {
        return "Toggle the dm mode on you or another player.";
    }

    @Override
    public String getSyntax() {
        return "/dm mode_toggle [player]";
    }

    @Override
    public void perform(Player p, String[] args) {

        if (p.hasPermission("dm.mode")) {
            if(args.length == 2){
                Player targetPlayer = Bukkit.getPlayer(args[1]);
                dmh.ToggleDmMode(targetPlayer, true);
            }
            else{
                dmh.ToggleDmMode(p, true);
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

