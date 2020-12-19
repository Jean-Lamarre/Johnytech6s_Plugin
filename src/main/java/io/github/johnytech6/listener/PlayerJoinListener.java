package io.github.johnytech6.listener;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.OfflineDndPlayer;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.DMHandler;
import org.bukkit.plugin.Plugin;

public class PlayerJoinListener implements Listener {

    private DMHandler dmh;
    private HeroHandler hh;

    private PluginHandler ph;
    private Plugin plugin;

    public PlayerJoinListener(PluginHandler pluginHandler){
        ph= pluginHandler;
        hh= pluginHandler.getHeroHandler();
        dmh = pluginHandler.getDmHandler();
        plugin = pluginHandler.getPlugin();
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        //TODO remove after updated on Alec's server 2020-12-15
        p.setInvulnerable(false);

        if (ph.isOfflineDndPlayers(p.getUniqueId())) { //if player have been on the server before (first join since plugin reloaded)
            OfflineDndPlayer offlineDndPlayer = ph.getOfflineDndPlayer(p.getUniqueId());

            DndPlayer dndPlayer;
            if (offlineDndPlayer.wasDm(plugin.getConfig())) {
                dndPlayer = new Dm(p, false);

                dmh.addDm((Dm) dndPlayer);
                p.sendMessage(plugin.getConfig().getString("dm_welcome_message"));
            } else {
                dndPlayer = new Hero(p);
                hh.addHero((Hero) dndPlayer);
                p.sendMessage(plugin.getConfig().getString("hero_welcome_message"));
            }
            ph.loadPlayerConfig(dndPlayer);

            ph.removeOfflineDndPlayers(offlineDndPlayer);
        } else {
            if (dmh.isPlayerDm(p.getUniqueId())) { //if player DM

                ph.loadPlayerConfig(dmh.getDm(p.getUniqueId()));

                p.sendMessage(plugin.getConfig().getString("dm_welcome_message"));
            } else if (hh.isPlayerHero(p.getUniqueId())) { //if player Hero

                ph.loadPlayerConfig(hh.getHero(p.getUniqueId()));

                p.sendMessage(plugin.getConfig().getString("hero_welcome_message"));
            } else { //if player never join
                p.sendMessage(plugin.getConfig().getString("default_welcome_message"));
                hh.addHero(new Hero(event.getPlayer()));
            }
        }
    }
}
