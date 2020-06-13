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
import com.DjembeInc.johnytechPlugin.theft.TeftHandler;

public class ClickEntityListener implements Listener {

	PuppeterHandler ph = PuppeterHandler.getInstance();
	HeroHandler hh = HeroHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();
	TeftHandler th = TeftHandler.getInstance();
	
	@EventHandler
	public void onPlayerClickEntity(PlayerInteractAtEntityEvent event) {
		
		//For only one hand
		if (event.getHand() == EquipmentSlot.OFF_HAND) {

			Player p = event.getPlayer();
			Entity e = event.getRightClicked();
			
			//-------RightClick Listener for Puppeter player-------
			if ((e instanceof Entity) && ph.isPlayerPuppeter(p.getName()) && !(e instanceof Player)) {
				ph.Morph(p, e);
			}
			
			/*
			//----------Right Click for control Inventory--------------
			else if(e instanceof Player && dmh.isPlayerDm(p.getName()) && th.isPlayerTeft(p.getName())) {
				try {
					th.openPlayerInventory(th.getTeft(p.getName()), (Player)e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			*/
			
			//-------Right click Listener for saddle on player or Villager-------
			else if (e instanceof Player || e.getName().contentEquals("Villager")) {
				if (p.getInventory().getItemInMainHand().toString().contentEquals("ItemStack{SADDLE x 1}")) {
					hh.RidePlayer(p, e);
				}
			}
		}
	}
}
