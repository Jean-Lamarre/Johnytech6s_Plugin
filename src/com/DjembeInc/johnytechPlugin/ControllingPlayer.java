package com.DjembeInc.johnytechPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ControllingPlayer{

	private Player playerRef;
	private Entity controlledEntity;
	private int index;
	
	public ControllingPlayer(Player p, Entity e) {
		this.playerRef = p;
		this.controlledEntity = e;
	}
	
	public ControllingPlayer() {}
	
	public String getName() {
		return playerRef.getName();
	}
	
	public int getIndex() {
		return this.index;
	}
	protected void setIndex(int i) {
		this.index = i;
	}
	
	public Player getPlayer() {
		return this.playerRef;
	}
	public Entity getEntity() {
		return this.controlledEntity;
	}
}
