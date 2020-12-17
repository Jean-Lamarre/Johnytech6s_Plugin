package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = PluginHandler.getInstance().getDndPlayer(playerID);

            if (p.getPlayer().hasPermission("dm.mode")) {
                if (args.length == 2) {
                    UUID targetPlayerID = UUID.fromString(args[1]);

                    dmh.ToggleDmMode(targetPlayerID, true);

                    if (dmh.isPlayerDm(targetPlayerID)) {
                        p.sendMessage(args[1] + " is now DM.");
                    } else {
                        p.sendMessage(args[1] + " is now a hero.");
                    }
                } else {
                    dmh.ToggleDmMode(p.getUniqueId(), true);
                }
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

