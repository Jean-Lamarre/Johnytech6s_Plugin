package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.hero.Hero;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UnfreezeHero extends SubCommand {

    private static HeroHandler hh = HeroHandler.getInstance();

    @Override
    public String getName() {
        return "unfreeze_hero";
    }

    @Override
    public String getDescription() {
        return "Unfreeze all the Hero players or a single one.";
    }

    @Override
    public String getSyntax() {
        return "/dm unfreeze_heros [hero]";
    }

    @Override
    public void perform(Player p, String[] args) {
        //TODO
        if (DMHandler.getInstance().isPlayerDm(p.getUniqueId()) /*&& p.hasPermission("dm.*****")*/) {
            if (args.length == 2) {
                if (hh.isPlayerHero(args[1])) {
                    hh.getHero(args[1]).setFrozenState(false);
                    p.sendMessage(args[1] + " is unfrozen.");
                } else {
                    p.sendMessage("A dm can never be frozen.");
                }
            } else {
                hh.unfreezeAllHeros();
                p.sendMessage("All heros are unfrozen.");
            }
        } else {
            p.sendMessage("You need to be DM to unfreeze hero(s).");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                Player current_player = players[i];
                if(hh.isPlayerHero(current_player.getName())){
                    Hero current_hero = hh.getHero(current_player.getName());
                    if(current_hero.isFrozen()){
                        playerNames.add(current_player.getName());
                    }
                }
            }

            return playerNames;
        }
        return null;
    }


}

