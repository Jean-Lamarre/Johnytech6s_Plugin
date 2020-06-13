package com.DjembeInc.johnytechPlugin.theft;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

public class PlayerCloseInventoryListener implements Listener {

	TeftHandler th = TeftHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();

	@EventHandler
	public void OnPlayerCloseInventory(InventoryCloseEvent event) {
		
		HumanEntity he = event.getPlayer();
		
		Inventory i = event.getInventory();
		
	}
}
