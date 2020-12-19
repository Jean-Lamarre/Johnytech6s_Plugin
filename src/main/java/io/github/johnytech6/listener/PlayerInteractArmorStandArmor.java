package io.github.johnytech6.listener;

import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.Handler.PuppeterHandler;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractArmorStandArmor implements Listener {

	PuppeterHandler ph;

	public PlayerInteractArmorStandArmor(PluginHandler pluginHandler){
		ph = pluginHandler.getPuppeterHandler();
	}

	@EventHandler
	public void OnPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		
		Player p = event.getPlayer();
		ArmorStand armorStand;
		
		EquipmentSlot equipementSelected = event.getSlot();
		ItemStack itemPlayerHelding = event.getArmorStandItem();
		
		// -------interactArmorListener for Puppeter player-------
		if (ph.isPlayerPuppeter(p.getUniqueId())) {
			//event.setCancelled(true);
			armorStand = event.getRightClicked();
			ph.MorphInArmorStand(p, armorStand, equipementSelected, itemPlayerHelding);
		}
		
	}

}
