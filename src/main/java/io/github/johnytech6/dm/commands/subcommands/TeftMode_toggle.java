package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.Handler.TeftHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeftMode_toggle extends SubCommand {

    private static DMHandler dmh = DMHandler.getInstance();
    private static TeftHandler th = TeftHandler.getInstance();

    @Override
    public String getName() {
        return "teft_mode_toggle";
    }

    @Override
    public String getDescription() {
        return "Toggle your teft mode as a dm or the teft mode of another dm.";
    }

    @Override
    public String getSyntax() {
        return "/dm teft_mode_toggle [player]";
    }

    @Override
    public void perform(Player p, String[] args) {

        if (dmh.isPlayerDm(p.getName()) && p.hasPermission("dm.mode.teft")) {
            if (args.length == 2) {
                Dm targetDm = dmh.getDm(args[1]);
                th.ToggleTeftMode((Player)targetDm, true);
            } else {
                th.ToggleTeftMode(p, true);
            }
        } else {
            p.sendMessage("You need to be DM to toggle teft power.");
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

