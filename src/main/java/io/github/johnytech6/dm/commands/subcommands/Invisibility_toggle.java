package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Invisibility_toggle extends SubCommand {

    private static DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "invisibility_toggle";
    }

    @Override
    public String getDescription() {
        return "Toggle your invisibility as a dm or the invisibility of another dm.";
    }

    @Override
    public String getSyntax() {
        return "/dm invisibility_toggle [player]";
    }

    @Override
    public void perform(Player p, String[] args) {

        if (dmh.isPlayerDm(p.getName()) && p.hasPermission("dm.mode.invisibility")) {
            Dm targetDm;
            if (args.length == 2) {
                if (dmh.isPlayerDm(args[1])) {
                    targetDm = dmh.getDm(args[1]);
                    targetDm.invisibilityToggle();
                    p.sendMessage("Invisibility state of " + args[1] + " : " + targetDm.isInvisible());
                } else {
                    p.sendMessage("Only dm can toggle invisibilty.");
                }
            } else {
                targetDm = dmh.getDm(p.getName());
                targetDm.invisibilityToggle();
            }
        } else {
            p.sendMessage("You need to be DM to toggle invisibility.");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                if (dmh.isPlayerDm(players[i].getName())) {
                    playerNames.add(players[i].getName());
                }

            }

            return playerNames;
        }
        return null;
    }


}

