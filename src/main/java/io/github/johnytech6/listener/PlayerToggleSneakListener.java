package io.github.johnytech6.listener;

import io.github.johnytech6.Handler.PluginHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PuppeterHandler;

public class PlayerToggleSneakListener implements Listener {

	PuppeterHandler ph;
	DMHandler dmh;

	public PlayerToggleSneakListener(PluginHandler pluginHandler){
		dmh = pluginHandler.getDmHandler();
		ph = pluginHandler.getPuppeterHandler();
	}

	@EventHandler
	public void OnPlayerToggleSneak(PlayerToggleSneakEvent event) {

		// Sneaking Listener
		if (event.getPlayer().isSneaking()) {

			Player ep = event.getPlayer();

			//Sneak listener for DM
			if (dmh.isPlayerDm(ep.getUniqueId())) {
				// Unmorph player if is morph AND a puppeter
				if (ph.isPlayerMorph(ep.getUniqueId()) && ph.isPlayerPuppeter(ep.getUniqueId())) {
					ph.Unmorph(ep);
				}
			}
		}
	}
}
