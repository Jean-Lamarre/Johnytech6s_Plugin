package io.github.johnytech6.Handler;

import java.util.ArrayList;

import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.hero.Hero;
import org.bukkit.plugin.Plugin;

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

    /*
     * Add a Hero
     */
    public void addHero(Hero h) {
        heros.add(h);
        ph.addDndPlayer(h);

        plugin.getConfig().set("Dnd_player.Heros."+h.getName()+".PlayerUUID", h.getUniqueId().toString());
        plugin.saveConfig();
    }

    /*
     * Remove Hero
     */
    public void removeHero(Hero h) {
        heros.remove(h);
        ph.removeDndPlayer(h);

        plugin.getConfig().set("Dnd_player.Heros."+h.getName(), null);
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

    /**
     * Freeze position and jump of all heros.
     */
    public void freezeAllHeros() {
        for (Hero h : heros) {
            h.freezeHero();
        }
    }

    /**
     * Unfreeze position and jump of all heros.
     */
    public void unfreezeAllHeros() {
        for (Hero h : heros) {
            h.unfreezeHero();
        }
    }


}
