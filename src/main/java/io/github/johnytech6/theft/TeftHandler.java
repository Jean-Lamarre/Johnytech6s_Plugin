package io.github.johnytech6.theft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import io.github.johnytech6.dm.DMHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.puppeter.Puppeter;

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

	DMHandler dmh = DMHandler.getInstance();

	private ArrayList<Teft> tefts = new ArrayList<Teft>();

	public void openPlayerInventory(Teft teft, Player victim) throws Exception {

		if (!teft.hasVictim(victim)) {
			teft.addVictim(victim);
		}
		Victim v = teft.getVictim(victim);

	}

	public boolean ToggleTeftMode(Player player) {

		if (!isPlayerTeft(player.getName())) {
			setTeftMode(player, true);
		} else if (isPlayerTeft(player.getName())) {
			setTeftMode(player, false);
		} else {
			return false;
		}
		return true;
	}

	public boolean setTeftMode(Player player, boolean hasTeftPower) {
		if (hasTeftPower) {
			AddTeftPlayer(new Teft(player));
			if(!(dmh.isAwaitedDm(player.getUniqueId()))) {
				player.sendMessage("You have teft power.");
			}
		} else if (!hasTeftPower) {
			try {
				RemoveTeftPlayer(getTeft(player.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			player.sendMessage("You dont have teft power anymore");
		} else {
			return false;
		}
		return true;
	}

	public void AddTeftPlayer(Teft t) {
		if (!(isPlayerTeft(t.getName()))) {
			tefts.add(t);
		}
	}

	public void RemoveTeftPlayer(Teft t) {
		tefts.remove(t);
	}

	public boolean isPlayerTeft(String name) {
		if (tefts.size() > 0) {
			for (Teft t : tefts) {
				if (t.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public Teft getTeft(String name) throws Exception {
		for (Teft t : tefts) {
			if (t.getPlayer().getName() == name) {
				return t;
			}
		}
		throw new Exception("No teft with the name: " + name);
	}

	public ArrayList<Teft> getTeftPlayers() {
		return tefts;
	}

}
