package io.github.johnytech6.listener;

import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.Handler.PuppeterHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerRespawnListener implements Listener {

	private DMHandler dmh;
	private HeroHandler hh;

	private PluginHandler ph;
	private Plugin plugin;

	public PlayerRespawnListener(PluginHandler pluginHandler){
		ph= pluginHandler;
		hh= pluginHandler.getHeroHandler();
		dmh = pluginHandler.getDmHandler();
		plugin = pluginHandler.getPlugin();
	}

	@EventHandler
	public void OnPlayerRespawn(PlayerRespawnEvent event) {

		Player p = event.getPlayer();

		Bukkit.getScheduler().scheduleSyncDelayedTask(ph.getPlugin(), new Runnable() {
			public void run() {
				if (dmh.isPlayerDm(p.getUniqueId())) { //if player DM
					ph.loadPlayerConfig(dmh.getDm(p.getUniqueId()));
				} else if (hh.isPlayerHero(p.getUniqueId())) { //if player Hero
					ph.loadPlayerConfig(hh.getHero(p.getUniqueId()));
				}
			}
		}, 1L);




	}
}
