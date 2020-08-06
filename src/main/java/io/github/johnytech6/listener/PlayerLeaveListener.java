package io.github.johnytech6.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.johnytech6.hero.HeroHandler;
import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;

public class PlayerLeaveListener implements Listener {

    DMHandler dmh = DMHandler.getInstance();
    HeroHandler hh = HeroHandler.getInstance();
    PuppeterHandler ph = PuppeterHandler.getInstance();

    @EventHandler
    public void OnPlayerLeave(PlayerQuitEvent event) {
/*
        Player p = event.getPlayer();

        boolean isDm = dmh.isPlayerDm(p.getName());
        if (isDm) {
            dmh.ToggleDmMode(p);

            dmh.setDmInvisibility(p, false);

            dmh.setDmVision(p, false);

            ph.setPuppeterMode(p, false);
        }

 */
    }
}