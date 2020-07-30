package io.github.johnytech6.theft;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;

public class PlayerCloseInventoryListener implements Listener {

	TeftHandler th = TeftHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();

	@EventHandler
	public void OnPlayerCloseInventory(InventoryCloseEvent event) {
		
		HumanEntity he = event.getPlayer();
		
		Inventory i = event.getInventory();
		
	}
}
