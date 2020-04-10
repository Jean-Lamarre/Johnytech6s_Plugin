package com.DjembeInc.johnytechPlugin;

//import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	private boolean isFirst = true;

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		if (isFirst) {
			//Bukkit.broadcastMessage("Cage location to start location of : " + event.getPlayer().getName());
			//ControlEntityHandler.setTrueEntityLocation(event.getPlayer().getLocation());
			isFirst = !isFirst;
		}
	}
}
