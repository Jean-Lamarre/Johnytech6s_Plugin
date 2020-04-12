package com.DjembeInc.johnytechPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClickEntityListener implements Listener {

	@EventHandler
	public void onPlayerClickEntity(PlayerInteractAtEntityEvent event) {
		if (event.getHand() == EquipmentSlot.OFF_HAND) {

			Player p = event.getPlayer();
			Entity e = event.getRightClicked();

			String tagName = e.getCustomName();			
			
			if (ControlEntityHandler.getInstance().isPlayerPuppeter(p.getName())) {

				ControlEntityHandler.getInstance().AddMorphPlayer(new ControllingPlayer(p, e));

				Location eLocation = e.getLocation();

				e.setInvulnerable(true);
				((LivingEntity) e).addPotionEffect(
						new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
				e.setSilent(true);
				p.addPassenger(e);
				p.teleport(eLocation);

				String entityName = e.toString();

				entityName = entityName.replace("Craft", "");

				String regex = "([a-z])([A-Z]+)";
				String replacement = "$1_$2";

				entityName = entityName.replaceAll(regex, replacement).toLowerCase();

				p.chat("/morph " + entityName);

				Entity puppet = p.getNearbyEntities(1, 1, 1).get(0);

				puppet.setCustomName(tagName);
				puppet.setCustomNameVisible(true);
			}
			// Ride player and villager by clicking them with saddle
			else if (e instanceof Player || e.getName().contentEquals("Villager")) {
				if (p.getInventory().getItemInMainHand().toString().contentEquals("ItemStack{SADDLE x 1}")) {
					e.addPassenger(p);
					if (e instanceof Player) {
						p.sendMessage("You are the passenger of " + e.getName());
						e.sendMessage(e.getName() + " is your passenger.");
					} else if (e.getName().contentEquals("Villager")) {
						p.sendMessage("You are the passenger of a villager.");
					}
				}
			}

		}
	}
}
