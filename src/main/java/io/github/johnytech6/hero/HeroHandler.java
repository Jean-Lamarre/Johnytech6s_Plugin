package io.github.johnytech6.hero;

import java.util.ArrayList;
import java.util.UUID;

import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.PluginHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroHandler {

    // ---------------------------SINGLETON
    // IMPLEMENTATION-------------------------------------------
    // static variable single_instance of type Singleton
    private static HeroHandler single_instance = null;

    // private constructor restricted to this class itself
    private HeroHandler() {
    }

    public static HeroHandler getInstance() {
        if (single_instance == null)
            single_instance = new HeroHandler();

        return single_instance;
    }
    // --------------------------------------------------------------------------------------------

    private static PluginHandler ph = PluginHandler.getInstance();

    private static Plugin plugin = JohnytechPlugin.getPlugin();

    // list of Hero (all player by default)
    private ArrayList<Hero> heros = new ArrayList<Hero>();
    private ArrayList<OfflinePlayer> awaitedHeros = new ArrayList<OfflinePlayer>();

    public void loadConfig(FileConfiguration config) {
        ArrayList<String> listHerosNames = (ArrayList<String>) config.getList("Heros");
        if (listHerosNames != null) {
            for (String name : listHerosNames) {
                String id = config.getString("Heros."+name+".PlayerUUID");
                awaitedHeros.add(Bukkit.getOfflinePlayer(UUID.fromString(id)));
            }
        }
    }

    // Right click with saddle
    public void RideHero(Player p, Entity e) {
        e.addPassenger(p.getPlayer());
        if (e instanceof Player) {
            p.sendMessage("You are the passenger of " + e.getName());
            e.sendMessage(p.getName() + " is your passenger.");
        } else if (e.getName().contentEquals("Villager")) {
            p.sendMessage("You are the passenger of a villager.");
        }
    }

    /*
     * Add a Hero
     */
    public void addHero(Hero h) {
        heros.add(h);
        ph.addDndPlayer(h);

        plugin.getConfig().set("Heros."+h.getName()+".PlayerUUID", h.getUniqueId().toString());
        plugin.saveConfig();
    }

    /*
     * Remove Hero
     */
    public void removeHero(Hero h) {
        heros.remove(h);
        ph.removeDndPlayer(h);

        plugin.getConfig().set("Heros."+h.getName(), null);
        plugin.saveConfig();
    }

    public void removeAwaitingHero(Player p) {
        if ((isAwaitedHero(p.getUniqueId()))) {
            awaitedHeros.remove(p);
        }
    }

    private void updateHerosUuid() {
        ArrayList<String> uuids = new ArrayList<String>();
        for (Hero h : heros) {
            uuids.add(h.getUniqueId().toString());
        }
        plugin.getConfig().set("Heros", uuids);
        plugin.saveConfig();
    }

    public boolean isPlayerHero(String name) {
        if (heros.size() > 0) {
            for (Hero h : heros) {
                if (h.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAwaitedHero(UUID id) {
        if (awaitedHeros.size() > 0) {
            for (OfflinePlayer p : awaitedHeros) {
                if (p.getUniqueId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Get hero reference with his name
     */
    public Hero getHero(String name) {
        if (heros.size() > 0) {
            for (Hero h : heros) {
                if (h.getName().equalsIgnoreCase(name)) {
                    return h;
                }
            }
        }
        return null;
    }

    /*
     * Get reference of the list of all the heros
     */
    public ArrayList<Hero> getHeros() {
        return heros;
    }

    public ArrayList<OfflinePlayer> getAwaitedHeros() {
        return awaitedHeros;
    }

    /**
     * Freeze position and jump of a target hero.
     *
     * @param targetHero
     */
    public void freezeHero(Hero targetHero) {
        if (!(targetHero.getWalkSpeed() == 0)) {
            targetHero.setWalkSpeed(0);
            targetHero.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false, true));
        }
    }

    /**
     * Freeze position and jump of all heros.
     */
    public void freezeAllHeros() {
        for (Hero h : heros) {
            if (!(h.getWalkSpeed() == 0)) {
                h.setWalkSpeed(0);
                h.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false, true));
            }
        }
    }

    /**
     * Unfreeze position and jump of a target hero.
     *
     * @param targetHero
     */
    public void unfreezeHero(Hero targetHero) {
        if (targetHero.getWalkSpeed() == 0) {
            targetHero.setWalkSpeed(0.2f);
            targetHero.removePotionEffect(PotionEffectType.JUMP);
        }
    }

    /**
     * Unfreeze position and jump of all heros.
     */
    public void unfreezeAllHeros() {
        for (Hero h : heros) {
            if (h.getWalkSpeed() == 0) {
                h.setWalkSpeed(0.2f);
                h.removePotionEffect(PotionEffectType.JUMP);
            }
        }
    }


}
