package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NightVision_toggle extends SubCommand {

    private static DMHandler dmh = DMHandler.getInstance();

    @Override
    public String getName() {
        return "night_vision_toggle";
    }

    @Override
    public String getDescription() {
        return "Toggle your night vision as a dm or the night vision of another dm.";
    }

    @Override
    public String getSyntax() {
        return "/dm night_vision_toggle [player]";
    }

    @Override
    public void perform(Player p, String[] args) {
        if(DMHandler.getInstance().isPlayerDm(p.getName()) && p.hasPermission("dm.mode.vision")) {
            if(args.length == 2) {
                Dm targetDm = dmh.getDm(args[1]);
                dmh.dmVision(targetDm, true);
            }
            else{
                dmh.dmVision(dmh.getDm(p.getName()), true);
            }
        }
        else {
            p.sendMessage("You need to be DM to toggle night vision.");
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