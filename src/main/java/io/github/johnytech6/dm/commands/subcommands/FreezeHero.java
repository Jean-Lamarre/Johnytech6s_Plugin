package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.HeroHandler;
import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FreezeHero extends SubCommand {

    HeroHandler hh = HeroHandler.getInstance();

    @Override
    public String getName() {
        return "freeze_hero";
    }

    @Override
    public String getDescription() {
        return "Freeze all the Hero players or a single one.";
    }

    @Override
    public String getSyntax() {
        return "/dm freeze_hero [hero]";
    }

    @Override
    public void perform(Player p, String[] args) {
        //TODO
        if (DMHandler.getInstance().isPlayerDm(p.getName()) /*&& p.hasPermission("dm.*****")*/) {
            if (args.length == 2) {
                if (hh.isPlayerHero(args[1])) {
                    hh.freezeHero(hh.getHero(args[1]));
                    p.sendMessage(args[1] + " is frozen.");
                } else {
                    p.sendMessage("You cant freeze another dm.");
                }
            } else {
                hh.freezeAllHeros();
                p.sendMessage("All heros are frozen.");
            }
        } else {
            p.sendMessage("You need to be DM to freeze hero(s).");
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
