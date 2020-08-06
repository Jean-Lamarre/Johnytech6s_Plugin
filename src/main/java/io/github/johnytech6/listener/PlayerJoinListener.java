package io.github.johnytech6.listener;


import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.theft.TeftHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.johnytech6.hero.HeroHandler;
import io.github.johnytech6.dm.DMHandler;

public class PlayerJoinListener implements Listener {

    DMHandler dmh = DMHandler.getInstance();
    HeroHandler hh = HeroHandler.getInstance();

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        if(dmh.isAwaitedDm(p.getUniqueId())){
            dmh.setDmMode(p, true, false);
            Dm awaitedDm = dmh.getDm(p.getName());
            awaitedDm.loadConfig();
            dmh.removeAwaitingDm(p);
        }else if(hh.isAwaitedHero(p.getUniqueId())){
            Hero awaitedHero = new Hero(p);
            hh.addHero(new Hero(p));
            awaitedHero.loadConfig();
            hh.removeAwaitingHero(p);
        }

        if (dmh.isPlayerDm(p.getName())) {
            p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("dm_welcome_message"));
        }else if (hh.isPlayerHero(p.getName())) {
            p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("hero_welcome_message"));
        }else{
            p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("default_welcome_message"));
            hh.addHero(new Hero(event.getPlayer()));
        }
    }
}
