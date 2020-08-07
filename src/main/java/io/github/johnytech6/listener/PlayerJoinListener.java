package io.github.johnytech6.listener;


import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.OfflineDndPlayer;
import io.github.johnytech6.PluginHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.theft.TeftHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.johnytech6.hero.HeroHandler;
import io.github.johnytech6.dm.DMHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListener implements Listener {

    private static DMHandler dmh = DMHandler.getInstance();
    private static HeroHandler hh = HeroHandler.getInstance();
    private static PluginHandler ph = PluginHandler.getInstance();

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

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
            if (dmh.isPlayerDm(p.getName())) {
                dmh.getDm(p.getName()).loadConfig(p);
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("dm_welcome_message"));
            } else if (hh.isPlayerHero(p.getName())) {
                hh.getHero(p.getName()).loadConfig(p);
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("hero_welcome_message"));
            } else {
                p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("default_welcome_message"));
                hh.addHero(new Hero(event.getPlayer()));
            }
        }
    }
}
