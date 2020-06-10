package com.DjembeInc.johnytechPlugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

public class PlayerToggleSneakListener implements Listener {

	PuppeterHandler ph = PuppeterHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();

	@EventHandler
	public void OnPlayerToggleSneak(PlayerToggleSneakEvent event) {

		// Sneaking Listener
		if (event.getPlayer().isSneaking()) {

			Player ep = event.getPlayer();

			//Sneak listener for DM
			if (dmh.isPlayerDm(ep.getName())) {
				// Unmorph player if is morph AND a puppeter
				if (ph.isPlayerMorph(ep.getName()) && ph.isPlayerPuppeter(ep.getName())) {
					ph.Unmorph(ep);
					
				}
			}
		}
	}
}
