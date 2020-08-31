package io.github.johnytech6.listener;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.JohnytechPlugin;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class PlayerInteract implements Listener {

    DMHandler dmh = DMHandler.getInstance();

    Plugin plugin = JohnytechPlugin.getPlugin();

    @EventHandler
    public void OnPlayerInteract(final PlayerInteractEvent event) {

        final Player p = event.getPlayer();

        if (dmh.isPlayerDm(p.getName()) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Block block = event.getClickedBlock();
            Inventory inventory = null;
            final BlockState blockState = block.getState();

            switch (block.getType()) {
                case CHEST:
                    final Chest chest = (Chest) blockState;
                    inventory = plugin.getServer().createInventory(p, chest.getInventory().getSize());
                    inventory.setContents(chest.getInventory().getContents());
                    event.setCancelled(true);
                    p.openInventory(inventory);
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
                    inventory = plugin.getServer().createInventory(p, barrel.getInventory().getSize());
                    inventory.setContents(barrel.getInventory().getContents());
                    event.setCancelled(true);
                    p.openInventory(inventory);
                    break;
            }


        }
    }

}
