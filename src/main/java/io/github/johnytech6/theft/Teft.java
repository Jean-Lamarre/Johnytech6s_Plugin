package io.github.johnytech6.theft;

import org.bukkit.entity.Player;

public class Teft {

	private Player playerRef;

	public Teft(Player p) {
		this.playerRef = p;
	}

	public String getName() {
		return playerRef.getName();
	}

	public Player getPlayer() {
		return this.playerRef;
	}


}
