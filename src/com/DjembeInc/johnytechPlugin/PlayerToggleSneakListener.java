package com.DjembeInc.johnytechPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {
	
	@EventHandler
	public void OnPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if(event.getPlayer().isSneaking()) {
			if(ControlEntityHandler.getInstance().isPlayerPuppeter(event.getPlayer().getName())) {
				ControllingPlayer cp = ControlEntityHandler.getInstance().GetController(event.getPlayer().getName());
				cp.getPlayer().chat("/unmorph");
				cp.getEntity().teleport(cp.getPlayer().getLocation());
				ControlEntityHandler.getInstance().RemoveController(cp);
			}
		}
	}
}
