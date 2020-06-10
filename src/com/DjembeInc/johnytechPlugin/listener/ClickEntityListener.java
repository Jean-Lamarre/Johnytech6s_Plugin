package com.DjembeInc.johnytechPlugin.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.DjembeInc.johnytechPlugin.HeroHandler;
import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

public class ClickEntityListener implements Listener {

	PuppeterHandler ph = PuppeterHandler.getInstance();
	HeroHandler hh = HeroHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();
	
	@EventHandler
	public void onPlayerClickEntity(PlayerInteractAtEntityEvent event) {
		
		//For only one hand
		if (event.getHand() == EquipmentSlot.OFF_HAND) {

			Player p = event.getPlayer();
			Entity e = event.getRightClicked();
			
			//-------RightClick Listener for Puppeter player-------
			if ((e instanceof Entity) && ph.isPlayerPuppeter(p.getName())) {
				ph.Morph(p, e);
			}
			
			//----------Right Click for control Inventory
			else if(e instanceof Player && dmh.isPlayerDm(p.getName())) {
				//TODO right click avec un objeet pour controller inventaire
				/*
				if((p.getInventory().getItemInMainHand().toString().contentEquals("ItemStack{CHEST x 1}"))){
					Bukkit.getServer().getConsoleSender().sendMessage("Right click with chest");
				}*/
			}
			
			//-------Right click Listener for saddle on player or Villager-------
			else if (e instanceof Player || e.getName().contentEquals("Villager")) {
				if (p.getInventory().getItemInMainHand().toString().contentEquals("ItemStack{SADDLE x 1}")) {
					hh.RidePlayer(p, e);
				}
			}
		}
	}
}
