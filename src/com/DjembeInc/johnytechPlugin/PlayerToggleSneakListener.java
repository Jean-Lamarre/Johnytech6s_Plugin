package com.DjembeInc.johnytechPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerToggleSneakListener implements Listener {
	
	
	@EventHandler
	public void OnPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if(event.getPlayer().isSneaking()) {
			
			ControlEntityHandler ceh = ControlEntityHandler.getInstance();
			
			Player ep = event.getPlayer();
			
			if(ceh.isPlayerMorph(ep.getName()) && ceh.isPlayerPuppeter(ep.getName())) {
				
				ControllingPlayer cp = ceh.GetMorphPlayer(ep.getName());
				
				Player p = cp.getPlayer();
				
				p.chat("/unmorph");
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 1, false, false, true));
				
				Entity e = cp.getEntity();
				e.teleport(cp.getPlayer().getLocation());
				e.setInvulnerable(false);
				((LivingEntity)e).removePotionEffect(PotionEffectType.INVISIBILITY);
				
				ControlEntityHandler.getInstance().RemoveMorphPlayer(cp);
				
			}
		}
	}
}
