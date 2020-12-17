package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.JohnytechPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class DMHandler {

    HeroHandler hh = HeroHandler.getInstance();

    // ---------------------------SINGLETON-IMPLEMENTATION-------------------------------------------
    // static variable single_instance of type Singleton
    private static DMHandler single_instance = null;

    // private constructor restricted to this class itself
    private DMHandler() {
    }

    public static DMHandler getInstance() {
        if (single_instance == null)
            single_instance = new DMHandler();

        return single_instance;
    }
    // --------------------------------------------------------------------------------------------

    private static PluginHandler ph = PluginHandler.getInstance();
    private static PuppeterHandler pph = PuppeterHandler.getInstance();
    private static TeftHandler th = TeftHandler.getInstance();

    private Plugin plugin = JohnytechPlugin.getPlugin();

    // list of Dm
    private HashMap<UUID, Dm> dms = new HashMap<UUID, Dm>();

    private boolean isSessionStarted = false;

    /*
     * Toggle Dm mode
     */
    public boolean ToggleDmMode(UUID id, boolean verbose) {
        return setDmMode(id, !(isPlayerDm(id)), verbose);
    }

    public boolean setDmMode(UUID id, boolean beDm, boolean verbose) {
        Player p = Bukkit.getPlayer(id);

        if (beDm) {
            Dm newDm;
            if (hh.isPlayerHero(p.getUniqueId())) {
                newDm = new Dm(hh.getHero(p.getUniqueId()), verbose);
            } else {
                newDm = new Dm(p, verbose);
            }
            newDm.setAllPower(true);
            addDm(newDm);
        } else {
            Dm dm = getDm(id);
            if (verbose) {
                p.sendMessage("***You are not the DM anymore***");
            }
            dm.setAllPower(false);
            removeDm(getDm(id));
            hh.addHero(new Hero(dm));
        }
        return true;
    }

    /*
     * Add a Dm
     */
    public void addDm(Dm newDm) {

        dms.put(newDm.getUniqueId(), newDm);
        ph.addDndPlayer(newDm);

        plugin.getConfig().set("Dnd_player.Dms." + newDm.getName() + ".PlayerUUID", newDm.getUniqueId().toString());
        plugin.saveConfig();

    }

    /*
     * Remove Dm
     */
    public void removeDm(Dm dm) {

        dms.remove(dm.getUniqueId());
        ph.removeDndPlayer(dm);
        plugin.getConfig().set("Dnd_player.Dms." + dm.getName(), null);
        plugin.saveConfig();

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
    public HashMap<UUID,Dm> getDms() {
        return dms;
    }

    public boolean isSessionStarted() {
        return isSessionStarted;
    }

    public void startSession(Dm dmSender) {
        isSessionStarted = true;

        HashMap<UUID, DndPlayer> dndPlayers = PluginHandler.getInstance().getDndPlayers();

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
                }
            }
        }


        dmSender.sendMessage("Session started");
    }

    public void endSession(Dm dmSender) {
        HashMap<UUID, DndPlayer> dndPlayers = PluginHandler.getInstance().getDndPlayers();

        Iterator<Map.Entry<UUID, DndPlayer>> allDndPlayer = dndPlayers.entrySet().iterator();
        while (allDndPlayer.hasNext()) {
            HashMap.Entry<UUID, DndPlayer> entry = allDndPlayer.next();

            DndPlayer dndP = entry.getValue();

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
