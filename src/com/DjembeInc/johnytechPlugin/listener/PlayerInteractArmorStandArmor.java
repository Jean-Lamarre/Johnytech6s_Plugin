package com.DjembeInc.johnytechPlugin.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

public class PlayerInteractArmorStandArmor implements Listener {

	PuppeterHandler ph = PuppeterHandler.getInstance();
	DMHandler dmh = DMHandler.getInstance();

	@EventHandler
	public void OnPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		
		Player p = event.getPlayer();
		ArmorStand armorStand;
		
		EquipmentSlot equipementSelected = event.getSlot();
		ItemStack itemPlayerHelding = event.getArmorStandItem();
		
		// -------interactArmorListener for Puppeter player-------
		if (ph.isPlayerPuppeter(p.getName())) {
			//event.setCancelled(true);
			armorStand = event.getRightClicked();
			ph.MorphInArmorStand(p, armorStand, equipementSelected, itemPlayerHelding);
		}
		
	}

}
