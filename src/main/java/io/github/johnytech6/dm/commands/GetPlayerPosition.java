package io.github.johnytech6.dm.commands;

import io.github.johnytech6.Handler.DMHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetPlayerPosition implements TabExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (DMHandler.getInstance().isPlayerDm(p.getName()) && p.hasPermission("dm.mode")) {
                if (args.length == 1) {
                    Player targetPlayer = Bukkit.getPlayer(args[0]);
                    p.sendMessage(targetPlayer.getName() + " is at " + (int) targetPlayer.getLocation().getX() + ", " + (int) targetPlayer.getLocation().getY() + ", " + (int) targetPlayer.getLocation().getZ());
                } else {
                    p.sendMessage("Usage: /getPlayerPosition <player>");
                }
            } else {
                p.sendMessage("You must be dm to locate a player.");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

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
