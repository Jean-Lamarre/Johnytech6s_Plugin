package com.DjembeInc.johnytechPlugin.theft;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Victim {

	private Player playerRef;
	private Teft teft;

	private TeftGUI inventoryGUI;

	public Victim(Player p, Teft teft) {
		this.playerRef = p;
		this.teft = teft;
		inventoryGUI = new TeftGUI(teft, p);
	}

	public String getName() {
		return playerRef.getName();
	}

	public Player getPlayer() {
		return this.playerRef;
	}

	public TeftGUI getInventoryGUI() {
		
		PlayerInventory inv = playerRef.getInventory();
		
		for(int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if(!(item == null)) {
				inventoryGUI.getInventory().addItem(item);
			}
		}
		return this.inventoryGUI;
	}

	public Teft getTeft() {
		return this.teft;
	}

}
