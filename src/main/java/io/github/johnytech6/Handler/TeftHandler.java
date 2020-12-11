package io.github.johnytech6.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import io.github.johnytech6.dm.theft.Teft;
import org.bukkit.entity.Player;

public class TeftHandler {

	// -------------SINGLETON IMPLEMENTATION-------
	// static variable single_instance of type Singleton
	private static TeftHandler single_instance = null;

	// private constructor restricted to this class itself
	private TeftHandler() {
	}

	public static TeftHandler getInstance() {
		if (single_instance == null)
			single_instance = new TeftHandler();

		return single_instance;
	}
	// ---------------------------------------------

	private static DMHandler dmh = DMHandler.getInstance();

	private HashMap<UUID,Teft> tefts = new HashMap<UUID,Teft>();


	public boolean ToggleTeftMode(Player player, boolean verbose) {
		if (!isPlayerTeft(player.getUniqueId())) {
			setTeftMode(player, true, verbose);
		} else if (isPlayerTeft(player.getUniqueId())) {
			setTeftMode(player, false, verbose);
		} else {
			return false;
		}
		return true;
	}

	public boolean setTeftMode(Player player, boolean hasTeftPower, boolean verbose) {
		if (hasTeftPower) {
			AddTeftPlayer(new Teft(player));
			if(verbose) {
				player.sendMessage("You have teft power.");
			}
		} else {
			try {
				RemoveTeftPlayer(getTeft(player.getUniqueId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(verbose) {
				player.sendMessage("You dont have teft power anymore");
			}
		}
		return true;
	}

	public void AddTeftPlayer(Teft t) {
		if (!(isPlayerTeft(t.getUniqueId()))) {
			tefts.put(t.getUniqueId(), t);
		}
	}

	public void RemoveTeftPlayer(Teft t) {
		tefts.remove(t.getUniqueId());
	}

	public boolean isPlayerTeft(UUID id) {
		if(tefts.containsKey(id)){
			return true;
		}
		return false;
	}

	public Teft getTeft(UUID id) throws Exception {
		return tefts.get(id);
	}

	public HashMap<UUID, Teft> getTeftPlayers() {
		return tefts;
	}

}
