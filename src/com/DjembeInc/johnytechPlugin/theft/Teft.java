package com.DjembeInc.johnytechPlugin.theft;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Teft {

	private Player playerRef;

	ArrayList<Victim> victims;

	public Teft(Player p) {
		this.playerRef = p;
		victims = new ArrayList<Victim>();
	}

	public String getName() {
		return playerRef.getName();
	}

	public Player getPlayer() {
		return this.playerRef;
	}

	public void addVictim(Player victim) {
			victims.add(new Victim(victim, this));
	}

	public void removeVictim(Victim victim) {
		victims.remove(victim);
	}
	
	protected boolean hasVictim(Player p) {
		for(Victim victim : victims){
			if(victim.getName() == p.getName()) {
				return true;
			}
		}
		return false;
	}
	
	protected Victim getVictim(Player p) throws Exception{
		for(Victim victim : victims){
			if(victim.getName() == p.getName()) {
				return victim;
			}
		}
		throw new Exception(p.getName().toString() + " is not a victim of " +playerRef.getName().toString());
	}

	public ArrayList<Victim> getVictims() {
		return this.victims;
	}
}
