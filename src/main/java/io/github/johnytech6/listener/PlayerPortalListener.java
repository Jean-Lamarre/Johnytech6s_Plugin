package io.github.johnytech6.listener;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.Handler.PuppeterHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerPortalListener implements Listener {

	PuppeterHandler ph;
	DMHandler dmh;
	PluginHandler pluginHandler;

	public PlayerPortalListener(PluginHandler pluginHandler){
		this.pluginHandler = pluginHandler;
		dmh = pluginHandler.getDmHandler();
		ph = pluginHandler.getPuppeterHandler();
	}

	@EventHandler
	public void OnPlayerPortal(PlayerPortalEvent event) {

		event.setCancelled(true);

		Player p = event.getPlayer();

		if (dmh.isSessionStarted()) {

			dmh.sendAllDMessage("The session is already started");

		} else {

			if(!pluginHandler.teleportPlayerToCheckpoint(pluginHandler.getDndPlayer(p.getUniqueId()))){

			}
		}

	}
}
