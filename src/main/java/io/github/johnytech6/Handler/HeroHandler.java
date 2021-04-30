package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.dm.commands.StatJohnytech;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.util.PluginStat;
import org.bukkit.Bukkit;

public class HeroHandler {

    private PluginHandler ph;

    // map of Hero (all player by default)
    private HashMap<UUID, Hero> heros = new HashMap<>();

    public HeroHandler(PluginHandler pluginHandler){
        ph = pluginHandler;
    }

    /*
     * Add a Hero
     */
    public void addHero(Hero h) {
        heros.put(h.getUniqueId(), h);
        ph.addDndPlayer(h);

        ph.getConfig().set("Dnd_player.Heros." + h.getName() + ".PlayerUUID", h.getUniqueId().toString());
        ph.getPlugin().saveConfig();
    }

    /*
     * Remove Hero
     */
    public void removeHero(Hero h) {
        heros.remove(h.getUniqueId());
        ph.removeDndPlayer(h);

        ph.getConfig().set("Dnd_player.Heros." + h.getName(), null);
        ph.getPlugin().saveConfig();
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
            ph.saveFrozenState(entry.getValue(), true);
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
            ph.saveFrozenState(entry.getValue(), false);
        }
    }


    public UUID getHeroUUIDByName(String name) {
        Iterator<Map.Entry<UUID, Hero>> heroEntries = heros.entrySet().iterator();
        while (heroEntries.hasNext()) {
            Hero nextHero = heroEntries.next().getValue();
            if(nextHero.getName().equals(name)){
                return nextHero.getUniqueId();
            }
        }

        return null;
    }
}
