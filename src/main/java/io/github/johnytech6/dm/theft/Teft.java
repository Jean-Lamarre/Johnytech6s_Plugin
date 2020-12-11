package io.github.johnytech6.dm.theft;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Teft {

	private final Player playerRef;

	public Teft(Player p) {
		this.playerRef = p;
	}

	public String getName() {
		return playerRef.getName();
	}

	public Player getPlayer() {
		return this.playerRef;
	}

    public UUID getUniqueId() {
		return playerRef.getUniqueId();
    }
}
