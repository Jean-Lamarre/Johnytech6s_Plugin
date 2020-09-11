package io.github.johnytech6.dm;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class VirtualInventory {

    private Inventory inventoryRef;
    private Inventory virtualInventory;
    private Player viewer;

    public VirtualInventory(Inventory inventoryRef, Inventory virtualInventory, Player viewer){
        this.inventoryRef = inventoryRef;
        this.virtualInventory = virtualInventory;
        this.viewer = viewer;
    }

    public Inventory getInventory(){
        return inventoryRef;
    }

    public Inventory getVirtualInventory() {
        return virtualInventory;
    }

    public Player getViewer(){
        return viewer;
    }

}
