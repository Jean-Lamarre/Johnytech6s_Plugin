package com.DjembeInc.johnytechPlugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.DjembeInc.johnytechPlugin.HeroHandler;
import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

public class PlayerLeaveListener implements Listener {

	DMHandler dmh = DMHandler.getInstance();
	HeroHandler hh = HeroHandler.getInstance();
	PuppeterHandler ph = PuppeterHandler.getInstance();

	@EventHandler
	public void OnPlayerLeave(PlayerQuitEvent event) {

		Player p = event.getPlayer();

		boolean isDm = dmh.isPlayerDm(p.getName());
		if (isDm) {
			
			dmh.ToggleDmMode(p);
			
			dmh.setDmInvisibility(p, !isDm);
			
			dmh.setDmVision(p, !isDm);

			ph.setPuppeterMode(p, !isDm);
		} 
	}
}