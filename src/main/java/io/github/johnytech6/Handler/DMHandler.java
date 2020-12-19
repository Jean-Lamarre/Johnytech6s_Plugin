package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.JohnytechPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class DMHandler {

    private HeroHandler hh;
    private PluginHandler ph;
    private PuppeterHandler pph;

    // list of Dm
    private HashMap<UUID, Dm> dms = new HashMap<>();

    private boolean isSessionStarted = false;

    public DMHandler(PluginHandler pluginHandler) {
        ph = pluginHandler;
        hh = pluginHandler.getHeroHandler();
        pph = pluginHandler.getPuppeterHandler();
    }

    public boolean setDmMode(UUID id, boolean beDm, boolean verbose) {
        Player p = Bukkit.getPlayer(id);

        if (beDm) {
            Dm newDm;
            if (hh.isPlayerHero(p.getUniqueId())) {
                Hero oldHero = hh.getHero(p.getUniqueId());
                newDm = new Dm(oldHero, verbose);
                hh.removeHero(oldHero);
            } else {
                if (hh.isPlayerHero(p.getUniqueId())) {
                    hh.removeHero(hh.getHero(p.getUniqueId()));
                }
                newDm = new Dm(p, verbose);
            }
            newDm.setAllPower(true);
            ph.saveAllDmPower(newDm, true);

            addDm(newDm);
        } else {
            Dm dm = getDm(id);
            if (verbose) {
                p.sendMessage("***You are not the DM anymore***");
            }
            dm.setAllPower(false);
            ph.saveAllDmPower(dm, false);
            removeDm(dm);

            Hero newHero = new Hero(dm);
            hh.addHero(newHero);
            newHero.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
        return true;
    }

    /*
     * Add a Dm
     */
    public void addDm(Dm newDm) {

        dms.put(newDm.getUniqueId(), newDm);
        ph.addDndPlayer(newDm);

        ph.getConfig().set("Dnd_player.Dms." + newDm.getName() + ".PlayerUUID", newDm.getUniqueId().toString());
        ph.getPlugin().saveConfig();
    }

    /*
     * Remove Dm
     */
    public void removeDm(Dm dm) {

        dms.remove(dm.getUniqueId());
        ph.removeDndPlayer(dm);
        ph.getConfig().set("Dnd_player.Dms." + dm.getName(), null);
        ph.getPlugin().saveConfig();
    }

    public boolean isPlayerDm(UUID id) {
        if (dms.containsKey(id)) {
            return true;
        }
        return false;
    }

    /*
     * Get Dm reference with his id
     */
    public Dm getDm(UUID id) {
        if (dms.containsKey(id)) {
            return dms.get(id);
        }

        return null;
    }

    /*
     * Get reference of the map of all the dms
     */
    public HashMap<UUID, Dm> getDms() {
        return dms;
    }

    public boolean isSessionStarted() {
        return isSessionStarted;
    }

    public void startSession(Dm dmSender) {
        isSessionStarted = true;

        HashMap<UUID, DndPlayer> dndPlayers = ph.getDndPlayers();

        //teleport if had checkpoint
        if (dmSender.hasCheckpoint()) {

            Iterator<Map.Entry<UUID, Dm>> allDm = dms.entrySet().iterator();
            while (allDm.hasNext()) {
                HashMap.Entry<UUID, Dm> entry = allDm.next();
                entry.getValue().sendMessage("Teleporting all hero to last checkpoint.");
            }

            Iterator<Map.Entry<UUID, DndPlayer>> allDndPlayer = dndPlayers.entrySet().iterator();
            while (allDndPlayer.hasNext()) {
                HashMap.Entry<UUID, DndPlayer> entry = allDndPlayer.next();

                DndPlayer dndP = entry.getValue();

                if (!(dndP.hasChair())) {

                    dndP.setChairPosition(dndP.getLocation());

                    ph.savePlayerChairPosition(dndP, dndP.getLocation());

                    dndP.sendMessage("Chair location saved");
                }

                if (dndP.hasCheckpoint()) {
                    dndP.teleport(dndP.getCheckpoint());
                } else {
                    dndP.teleport(dmSender.getCheckpoint());
                }
                dndP.sendTitle("Minecraft DnD", "The adventure may continue...", 10, 70, 20);
            }

        } else {
            dmSender.sendMessage("No checkpoint was saved. Teleport all player manually.");

            Iterator<Map.Entry<UUID, DndPlayer>> allDndPlayer = dndPlayers.entrySet().iterator();
            while (allDndPlayer.hasNext()) {
                HashMap.Entry<UUID, DndPlayer> entry = allDndPlayer.next();

                DndPlayer dndP = entry.getValue();

                dndP.sendTitle("Minecraft DnD", "The adventure may begin...", 10, 70, 20);
                if (!(dndP.hasChair())) {

                    dndP.setChairPosition(dndP.getLocation());

                    ph.savePlayerChairPosition(dndP, dndP.getLocation());

                }
            }
        }


        dmSender.sendMessage("Session started");
    }

    public void endSession(Dm dmSender) {
        HashMap<UUID, DndPlayer> dndPlayers = ph.getDndPlayers();

        Iterator<Map.Entry<UUID, DndPlayer>> allDndPlayer = dndPlayers.entrySet().iterator();
        while (allDndPlayer.hasNext()) {
            HashMap.Entry<UUID, DndPlayer> entry = allDndPlayer.next();

            DndPlayer dndP = entry.getValue();

            ph.savePlayerCheckpoint(dndP, dndP.getLocation());
            dndP.setCheckpoint(dndP.getLocation());

            if (dndP.hasChair()) {
                dndP.teleport(dndP.getChairPosition());
            }
            dndP.teleport(dmSender.getChairPosition());
            dndP.sendTitle("To be continued...", "End of the session", 10, 70, 20);
        }

        Iterator<Map.Entry<UUID, Dm>> allDm = dms.entrySet().iterator();
        while (allDm.hasNext()) {
            HashMap.Entry<UUID, Dm> entry = allDm.next();
            entry.getValue().sendMessage("Saved checkpoint and teleported heros to the dnd room.");
        }

        isSessionStarted = false;
        dmSender.sendMessage("Session ended");
    }
}
