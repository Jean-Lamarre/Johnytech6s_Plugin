package io.github.johnytech6.dm.puppeter;

import org.bukkit.entity.Player;

/*
 * 
 */
public class Puppeter{

	private Player playerRef;
	private Puppet puppet;
	private int index;
	
	public Puppeter(Player p) {
		this.playerRef = p;
	}
	
	public String getName() {
		return playerRef.getName();
	}
	
	public int getIndex() {
		return this.index;
	}
	public void setIndex(int i) {
		this.index = i;
	}
	
	public Player getPlayer() {
		return this.playerRef;
	}
	public Puppet getPuppet() {
		return this.puppet;
	}
	public void setPuppet(Puppet pu) {
		this.puppet = pu;
	}
}
