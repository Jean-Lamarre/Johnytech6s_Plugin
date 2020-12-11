package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.dm.Dm;
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

    // amp of Hero (all player by default)
    private HashMap<UUID, Hero> heros = new HashMap<UUID, Hero>();

    /*
     * Add a Hero
     */
    public void addHero(Hero h) {
        heros.put(h.getUniqueId(), h);
        ph.addDndPlayer(h);

        plugin.getConfig().set("Dnd_player.Heros." + h.getName() + ".PlayerUUID", h.getUniqueId().toString());
        plugin.saveConfig();
    }

    /*
     * Remove Hero
     */
    public void removeHero(Hero h) {
        heros.remove(h);
        ph.removeDndPlayer(h);

        plugin.getConfig().set("Dnd_player.Heros." + h.getName(), null);
        plugin.saveConfig();
    }

    public boolean isPlayerHero(UUID id) {
        if (heros.containsKey(id)) {
            return true;
        }
        return false;
    }

    /*
     * Get hero reference with his id
     */
    public Hero getHero(UUID id) {
        if (heros.containsKey(id)) {
            return heros.get(id);
        }

        return null;
    }

    /*
     * Get reference of the list of all the heros
     */
    public HashMap<UUID,Hero> getHeros() {
        return heros;
    }

    /**
     * Freeze position and jump of all heros.
     */
    public void freezeAllHeros() {
        Iterator<Map.Entry<UUID, Hero>> heroEntries = heros.entrySet().iterator();
        while (heroEntries.hasNext()) {
            HashMap.Entry<UUID, Hero> entry = heroEntries.next();
            entry.getValue().setFrozenState(true);
        }
    }

    /**
     * Unfreeze position and jump of all heros.
     */
    public void unfreezeAllHeros() {
        Iterator<Map.Entry<UUID, Hero>> heroEntries = heros.entrySet().iterator();
        while (heroEntries.hasNext()) {
            HashMap.Entry<UUID, Hero> entry = heroEntries.next();
            entry.getValue().setFrozenState(false);
        }
    }


}
