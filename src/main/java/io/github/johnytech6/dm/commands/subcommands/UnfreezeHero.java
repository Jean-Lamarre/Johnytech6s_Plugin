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

public class UnfreezeHero extends SubCommand {

    private PluginHandler ph;
    private HeroHandler hh;

    public UnfreezeHero(PluginHandler pluginHandler){
        ph = pluginHandler;
        hh = pluginHandler.getHeroHandler();
    }

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
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            UUID playerID = ((Player) sender).getUniqueId();

            DndPlayer p = ph.getDndPlayer(playerID);

            if (ph.getDmHandler().isPlayerDm(p.getUniqueId()) /*&& p.hasPermission("dm.*****")*/) {
                if (args.length == 2) {
                    UUID targetUUID = hh.getHeroUUIDByName(args[1]);

                    if (hh.isPlayerHero(targetUUID)) {
                        Hero targetHero = hh.getHero(targetUUID);
                        targetHero.setFrozenState(false);
                        ph.saveFrozenState(targetHero, false);
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
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                Player current_player = players[i];
                if(hh.isPlayerHero(current_player.getUniqueId())){
                    Hero current_hero = hh.getHero(current_player.getUniqueId());
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

