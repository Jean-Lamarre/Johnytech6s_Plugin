package com.DjembeInc.johnytechPlugin.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.DjembeInc.johnytechPlugin.HeroHandler;
import com.DjembeInc.johnytechPlugin.dm.DMHandler;

public class PlayerJoinListener implements Listener {

	DMHandler dmh = DMHandler.getInstance();
	HeroHandler hh = HeroHandler.getInstance();

	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent event) {

		Player p = event.getPlayer();

		if (!(hh.isPlayerHero(p.getName()))) {
			
			hh.AddHero(event.getPlayer());
			
		} else if (hh.isPlayerHero(p.getName())) {
			p.sendMessage("Welcome Hero!");
		}
	}
}
