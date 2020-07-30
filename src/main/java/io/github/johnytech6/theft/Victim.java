package io.github.johnytech6.theft;

import org.bukkit.entity.Player;

public class Victim {

	private Player playerRef;
	private Teft teft;

	public Victim(Player p, Teft teft) {
		this.playerRef = p;
		this.teft = teft;
	}

	public String getName() {
		return playerRef.getName();
	}

	public Player getPlayer() {
		return this.playerRef;
	}

	public Teft getTeft() {
		return this.teft;
	}

}
