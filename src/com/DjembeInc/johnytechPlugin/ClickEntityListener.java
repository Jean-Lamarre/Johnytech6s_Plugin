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

			if (ControlEntityHandler.getInstance().isPlayerPuppeter(p.getName())) {

				Entity e = event.getRightClicked();

				ControlEntityHandler.getInstance().AddMorphPlayer(new ControllingPlayer(p, e));

				Location eLocation = e.getLocation();

				// e.teleport(ControlEntityHandler.getTrueEntityLocation());
				// e.teleport(new Location(p.getLocation().getWorld(), p.getLocation().getX(),
				// 250, p.getLocation().getZ()));
				e.setInvulnerable(true);
				((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
				e.setSilent(true);
				p.addPassenger(e);
				p.teleport(eLocation);

				String entityName = e.toString();

				String entityNameTrim = entityName.replace("Craft", "");
				String regex = "([a-z])([A-Z]+)";
				String replacement = "$1_$2";

				p.chat("/morph " + entityNameTrim.replaceAll(regex, replacement));
			}
		}
	}
}
