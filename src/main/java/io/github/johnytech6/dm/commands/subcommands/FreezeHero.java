package io.github.johnytech6.dm.commands.subcommands;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.SubCommand;
import io.github.johnytech6.hero.Hero;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreezeHero extends SubCommand {

    private static HeroHandler hh = HeroHandler.getInstance();

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
    public void perform(CommandSender sender, String[] args) {
        //TODO
        if (sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = PluginHandler.getInstance().getDndPlayer(playerID);

            if (DMHandler.getInstance().isPlayerDm(playerID) /*&& p.hasPermission("dm.*****")*/) {
                if (args.length == 2) {

                    UUID targetUUID = hh.getHeroUUIDByName(args[1]);

                    if (hh.isPlayerHero(targetUUID)) {
                        hh.getHero(targetUUID).setFrozenState(true);
                        p.sendMessage(args[1] + " is frozen.");
                    } else {
                        p.sendMessage("You only can freeze heros.");
                    }
                } else {
                    hh.freezeAllHeros();
                    p.sendMessage("All heros are frozen.");
                }
            } else {
                p.sendMessage("You need to be DM to freeze hero(s).");
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
                Player current_player = players[i];
                if (hh.isPlayerHero(current_player.getUniqueId())) {
                    Hero current_hero = hh.getHero(current_player.getUniqueId());
                    if (!(current_hero.isFrozen())) {
                        playerNames.add(current_player.getName());
                    }
                }
            }

            return playerNames;
        }
        return null;
    }


}

