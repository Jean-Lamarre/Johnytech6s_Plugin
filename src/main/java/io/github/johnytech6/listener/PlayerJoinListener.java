package io.github.johnytech6.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.johnytech6.HeroHandler;
import io.github.johnytech6.dm.DMHandler;

public class PlayerJoinListener implements Listener {

    DMHandler dmh = DMHandler.getInstance();
    HeroHandler hh = HeroHandler.getInstance();

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        if (!(hh.isPlayerHero(p.getName()))) {
            hh.addHero(event.getPlayer());
        } else if (dmh.isPlayerDm(p.getName())) {
            p.sendMessage("Welcome Dungeon Master.");
        } else if (hh.isPlayerHero(p.getName())) {
            p.sendMessage("Welcome Hero!");
        }

    }
}
