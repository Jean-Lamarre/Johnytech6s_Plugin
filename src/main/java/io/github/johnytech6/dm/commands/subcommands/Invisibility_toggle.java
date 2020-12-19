package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Invisibility_toggle extends SubCommand {

    private PluginHandler pluginHandler;
    private DMHandler dmh;

    public Invisibility_toggle(PluginHandler pluginHandler){
        this.pluginHandler = pluginHandler;
        dmh = pluginHandler.getDmHandler();
    }

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
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = pluginHandler.getDndPlayer(playerID);

            if (dmh.isPlayerDm(playerID) /* && p.hasPermission("dm.mode.invisibility")*/) {
                Dm targetDm;
                if (args.length == 2) {
                    UUID targetPlayerID = UUID.fromString(args[1]);
                    if (dmh.isPlayerDm(targetPlayerID)) {
                        targetDm = dmh.getDm(targetPlayerID);

                        targetDm.setInvisibility(!targetDm.isInvisible());
                        pluginHandler.saveInvisbility(targetDm, targetDm.isInvisible());

                        p.sendMessage("Invisibility state of " + args[1] + " : " + targetDm.isInvisible());
                    } else {
                        p.sendMessage("Only dm can toggle invisibilty.");
                    }
                } else {
                    targetDm = dmh.getDm(playerID);
                    targetDm.setInvisibility(!targetDm.isInvisible());
                    pluginHandler.saveInvisbility(targetDm, targetDm.isInvisible());
                }
            } else {
                p.sendMessage("You need to be DM to toggle invisibility.");
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
                if (dmh.isPlayerDm(players[i].getUniqueId())) {
                    playerNames.add(players[i].getName());
                }
            }

            return playerNames;
        }
        return null;
    }


}

