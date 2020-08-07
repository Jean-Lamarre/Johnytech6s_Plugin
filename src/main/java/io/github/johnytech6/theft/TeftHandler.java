package io.github.johnytech6.theft;

import java.util.ArrayList;

import io.github.johnytech6.dm.DMHandler;
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

	private ArrayList<Teft> tefts = new ArrayList<Teft>();


	public boolean ToggleTeftMode(Player player, boolean verbose) {
		if (!isPlayerTeft(player.getName())) {
			setTeftMode(player, true, verbose);
		} else if (isPlayerTeft(player.getName())) {
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
				RemoveTeftPlayer(getTeft(player.getName()));
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
			if (t.getPlayer().getName().equals(name)) {
				return t;
			}
		}
		throw new Exception("No teft with the name: " + name);
	}

	public ArrayList<Teft> getTeftPlayers() {
		return tefts;
	}

}
