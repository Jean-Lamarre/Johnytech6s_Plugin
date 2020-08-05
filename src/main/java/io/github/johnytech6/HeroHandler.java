package io.github.johnytech6;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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

    // list of Hero (all player by default)
    private ArrayList<Player> heros = new ArrayList<Player>();
    //TODO add arrayList in a config file (or other serializtion)

    // Right click with saddle
    public void RidePlayer(Player p, Entity e) {
        e.addPassenger(p);
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
    public void addHero(Player p) {
        if (!(isPlayerHero(p.getName()))) {
            heros.add(p);
        }
    }

    /*
     * Remove Hero
     */
    public void removeHero(Player pp) {
        heros.remove(pp);
    }

    public boolean isPlayerHero(String name) {
        if (heros.size() > 0) {
            for (Player p : heros) {
                if (p.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Get hero reference with his name
     */
    public Player getHero(String name) {
        if (heros.size() > 0) {
            for (Player p : heros) {
                if (p.getName().equalsIgnoreCase(name)) {
                    return p;
                }
            }
        }
        return null;
    }

    /*
     * Get reference of the list of all the heros
     */
    public ArrayList<Player> getHeros() {
        return heros;
    }

    /**
     * Freeze position and jump of a target hero.
     *
     * @param targetHero
     */
    public void freezeHero(Player targetHero) {
        if (!(targetHero.getWalkSpeed() == 0)) {
            targetHero.setWalkSpeed(0);
            targetHero.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false, true));
        }
    }

    /**
     * Freeze position and jump of all heros.
     */
    public void freezeAllHeros() {
        for (Player h : heros) {
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
    public void unfreezeHero(Player targetHero) {
        if (targetHero.getWalkSpeed() == 0) {
            targetHero.setWalkSpeed(0.2f);
            targetHero.removePotionEffect(PotionEffectType.JUMP);
        }
    }

    /**
     * Unfreeze position and jump of all heros.
     */
    public void unfreezeAllHeros() {
        for (Player h : heros) {
            if (h.getWalkSpeed() == 0) {
                h.setWalkSpeed(0.2f);
                h.removePotionEffect(PotionEffectType.JUMP);
            }
        }
    }
}
