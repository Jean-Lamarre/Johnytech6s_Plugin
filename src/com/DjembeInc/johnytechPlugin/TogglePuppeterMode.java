package com.DjembeInc.johnytechPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TogglePuppeterMode implements  CommandExecutor{

	private boolean isPuppeter;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		ControlEntityHandler ceh = ControlEntityHandler.getInstance();
		
		Player p = (Player) sender;
		
		isPuppeter = ceh.isPlayerPuppeter(p.getName());
		
		if(!isPuppeter) {
			ceh.AddPuppeter(new ControllingPlayer(p));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, true));
			p.sendMessage("You are now a puppeter");
		}
		else if(isPuppeter){

			if(ceh.isPlayerMorph(p.getName())) {
				
				p.chat("/unmorph");
				
				ControllingPlayer cp = ceh.GetMorphPlayer(p.getName());
				
				Entity e = cp.getEntity();
				
				p.removePassenger(e);
				e.teleport(p.getLocation());
				e.setInvulnerable(false);
				((LivingEntity)e).removePotionEffect(PotionEffectType.INVISIBILITY);
				e.setSilent(false);
				
				ceh.RemoveMorphPlayer(cp);
			}
			
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			ceh.RemovePuppeter(ceh.getPuppeter(p.getName()));
			p.sendMessage("You are not a puppeter anymore");
		}
		else {
			return false;
		}
		
		return true;
	}
	
}
