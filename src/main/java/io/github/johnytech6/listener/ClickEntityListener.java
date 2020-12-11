package io.github.johnytech6.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.PuppeterHandler;
import io.github.johnytech6.Handler.TeftHandler;

public class ClickEntityListener implements Listener {

    PuppeterHandler ph = PuppeterHandler.getInstance();
    HeroHandler hh = HeroHandler.getInstance();
    DMHandler dmh = DMHandler.getInstance();
    TeftHandler th = TeftHandler.getInstance();

    @EventHandler
    public void onPlayerClickEntity(PlayerInteractAtEntityEvent event) {

        //For only one hand
        if (event.getHand() == EquipmentSlot.OFF_HAND) {

            Player p = event.getPlayer();
            Entity e = event.getRightClicked();

            //-------RightClick Listener for Puppeter player-------
            if (ph.isPlayerPuppeter(p.getUniqueId()) && !(e instanceof Player)) {
                ph.Morph(p, e);
            }

            //----------Right Click for control Inventory--------------
            else if (e instanceof Player && dmh.isPlayerDm(p.getUniqueId()) && th.isPlayerTeft(p.getUniqueId())) {
                String playerName = e.getName();
                Player otherPlayer = Bukkit.getServer().getPlayerExact(playerName);
                if (otherPlayer == null) {
                    p.sendMessage(ChatColor.RED + "Error: Player name (" + playerName + ") is invalid! Is the player online?");
                }
                assert otherPlayer != null;
                p.openInventory(otherPlayer.getInventory());
            }

            //-------Right click Listener for saddle on player or Villager-------
            else if (e instanceof Player || e.getName().contentEquals("Villager")) {
                if (p.getInventory().getItemInMainHand().toString().contentEquals("ItemStack{SADDLE x 1}")) {
                    if (hh.isPlayerHero(p.getUniqueId())) {
                        hh.getHero(p.getUniqueId()).rideHero(e);
                    }
                }
            }
        }
    }
}
