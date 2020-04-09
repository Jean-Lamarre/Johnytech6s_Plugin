package com.DjembeInc.johnytechPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickEntityListener implements Listener {
	
	@EventHandler
	public void onPlayerClickEntity(PlayerInteractAtEntityEvent event) {
		if (event.getHand() == EquipmentSlot.OFF_HAND) {
			
			Player p = event.getPlayer();
			Entity e = event.getRightClicked();
			
			ControlEntityHandler.getInstance().AddController(new ControllingPlayer(p, e));

			Location eLocation = e.getLocation();
			
			e.teleport(new Location(p.getWorld(), 50,72,50));	
			p.teleport(eLocation);
			
			String entityName = e.toString();
			
			String entityNameTrim = entityName.replace("Craft", "");			
			String regex = "([a-z])([A-Z]+)";
	        String replacement = "$1_$2";
			
	        p.chat("/morph " + entityNameTrim.replaceAll(regex, replacement));
		}
	}
}
