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

public class PlayerJoinListener implements Listener {

    private static final DMHandler dmh = DMHandler.getInstance();
    private static final HeroHandler hh = HeroHandler.getInstance();
    private static final PluginHandler ph = PluginHandler.getInstance();

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        //TODO remove after updated on Alec's server
        p.setInvulnerable(false);

        if (ph.isOfflineDndPlayers(p.getUniqueId())) {
            OfflineDndPlayer offlineDndPlayer = ph.getOfflineDndPlayer(p.getUniqueId());

            DndPlayer dndPlayer;
            if (offlineDndPlayer.wasDm(JohnytechPlugin.getPlugin().getConfig())) {
                dndPlayer = new Dm(p, false);
                dmh.addDm((Dm)dndPlayer);
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("dm_welcome_message"));
            } else {
                dndPlayer = new Hero(p);
                hh.addHero((Hero)dndPlayer);
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("hero_welcome_message"));
            }
            dndPlayer.loadConfig(p);
            ph.removeOfflineDndPlayers(offlineDndPlayer);

        } else {
            if (dmh.isPlayerDm(p.getUniqueId())) {
                dmh.getDm(p.getUniqueId()).loadConfig(p);
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("dm_welcome_message"));
            } else if (hh.isPlayerHero(p.getUniqueId())) {
                hh.getHero(p.getUniqueId()).loadConfig(p);
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("hero_welcome_message"));
            } else {
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("default_welcome_message"));
                hh.addHero(new Hero(event.getPlayer()));
            }
        }
    }
}
