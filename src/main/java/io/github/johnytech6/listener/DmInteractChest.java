package io.github.johnytech6.listener;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.dm.VirtualInventory;
import org.bukkit.Bukkit;
import org.bukkit.block.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DmInteractChest implements Listener {

    static final String INVENTORY_TITLE = "Dm";

    DMHandler dmh = DMHandler.getInstance();

    Plugin plugin = JohnytechPlugin.getPlugin();

    ArrayList<VirtualInventory> openChests = new ArrayList<VirtualInventory>();

    @EventHandler
    public void OnPlayerInteract(final PlayerInteractEvent event) {

        final Player p = event.getPlayer();

        if (dmh.isPlayerDm(p.getUniqueId()) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Block block = event.getClickedBlock();
            Inventory inventory = null;
            final BlockState blockState = block.getState();

            switch (block.getType()) {
                case CHEST:
                    final Chest chest = (Chest) blockState;
                    inventory = plugin.getServer().createInventory(p, chest.getInventory().getSize(), INVENTORY_TITLE + " - Chest");
                    inventory.setContents(chest.getInventory().getContents());
                    event.setCancelled(true);
                    p.openInventory(inventory);
                    openChests.add(new VirtualInventory(chest.getInventory(), inventory, p));
                    break;
                case DISPENSER:
                    inventory = ((Dispenser) blockState).getInventory();
                    event.setCancelled(true);
                    p.openInventory(inventory);
                    break;
                case HOPPER:
                    inventory = ((Hopper) blockState).getInventory();
                    event.setCancelled(true);
                    p.openInventory(inventory);
                    break;
                case DROPPER:
                    inventory = ((Dropper) blockState).getInventory();
                    event.setCancelled(true);
                    p.openInventory(inventory);
                    break;
                case BARREL:
                    final Barrel barrel = (Barrel) blockState;
                    inventory = plugin.getServer().createInventory(p, barrel.getInventory().getSize(), INVENTORY_TITLE + " - Barrel");
                    inventory.setContents(barrel.getInventory().getContents());
                    event.setCancelled(true);
                    p.openInventory(inventory);
                    openChests.add(new VirtualInventory(barrel.getInventory(), inventory, p));
                    break;
            }

        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (dmh.isPlayerDm(p.getUniqueId())) {
            updateChest(p, event.getView(), event.getInventory());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();
        if (dmh.isPlayerDm(p.getUniqueId())) {
            updateChest(p, event.getView(), event.getInventory());

            for (VirtualInventory vi : openChests) {
                if(vi.getViewer().equals(p)){
                    openChests.remove(vi);
                    continue;
                }
            }
            Bukkit.broadcastMessage(openChests.toString());
        }

    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (dmh.isPlayerDm(p.getUniqueId())) {
            updateChest(p, event.getView(), event.getInventory());
        }
    }

    private boolean updateChest(Player p, InventoryView iv, Inventory i) {
        if (iv.getTitle().contains(INVENTORY_TITLE)) {
            for (VirtualInventory vi : openChests) {
                List<HumanEntity> inventoryViewers = vi.getVirtualInventory().getViewers();
                for (HumanEntity he : inventoryViewers) {
                    if (he.getName().equals(p.getName())) {
                        Inventory trueInventory = vi.getInventory();
                        Inventory virtualInventory = vi.getVirtualInventory();
                        ItemStack[] itemStack = new ItemStack[trueInventory.getSize()];
                        System.arraycopy(virtualInventory.getContents(), 0, itemStack, 0, trueInventory.getSize());
                        trueInventory.setContents(itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
