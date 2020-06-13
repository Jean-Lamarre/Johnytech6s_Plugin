package com.DjembeInc.johnytechPlugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;
import com.DjembeInc.johnytechPlugin.theft.TeftHandler;

public class PlayerLeaveBedListener implements Listener {

	PuppeterHandler ph = PuppeterHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();
	TeftHandler th = TeftHandler.getInstance();

	@EventHandler
	public void OnPlayerLeaveBed(PlayerBedLeaveEvent event) {
		 /*
		Player p = event.getPlayer();
		if(th.isPlayerTeft(p.getName())) {
			try {
				Teft t = th.getTeft(p.getName());
				th.openPlayerInventory(t, p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		*/
	}
}
