package io.github.johnytech6.listener;


import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.theft.TeftHandler;
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

        if(dmh.isAwaitedDm(p.getUniqueId())){
            dmh.ToggleDmMode(p);
            dmh.setDmInvisibility(p, (dmh.isPlayerDm(p.getName())));
            dmh.setDmVision(p, (dmh.isPlayerDm(p.getName())));
            PuppeterHandler.getInstance().setPuppeterMode(p, (dmh.isPlayerDm(p.getName())));
            TeftHandler.getInstance().setTeftMode(p, true);
            dmh.RemoveAwaitingDm(p);
        }else if(hh.isAwaitedHero(p.getUniqueId())){
            hh.addHero(p);
            hh.RemoveAwaitingHero(p);
        }

        if (dmh.isPlayerDm(p.getName())) {
            dmh.setDmInvisibility(p, (dmh.isPlayerDm(p.getName())));
            dmh.setDmVision(p, (dmh.isPlayerDm(p.getName())));
            p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("dm_welcome_message"));
        }else if (hh.isPlayerHero(p.getName())) {
            p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("hero_welcome_message"));
        }else{
            p.sendMessage(JohnytechPlugin.getPlugin().getConfig().getString("default_welcome_message"));
            hh.addHero(event.getPlayer());
        }
    }
}
