package com.DjembeInc.johnytechPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 1, false, false, true));
			p.sendMessage("You are now a puppeter");
		}
		else if(isPuppeter){
			ceh.RemovePuppeter(ceh.GetPuppeter(p.getName()));
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			p.sendMessage("You are not a puppeter anymore");
		}
		else {
			return false;
		}
		
		return true;
	}
	
}
