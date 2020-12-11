package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.JohnytechPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class DMHandler{

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
    private ArrayList<Dm> dms = new ArrayList<Dm>();

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
            if(hh.isPlayerHero(p.getUniqueId())){
                newDm = new Dm(hh.getHero(p.getUniqueId()), verbose);
            }else{
                newDm = new Dm(p, verbose);
            }
            newDm.setAllPower(true);
            addDm(newDm);
        } else {
            Dm dm = getDm(id);
            if(verbose){
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
        dms.add(newDm);
        ph.addDndPlayer(newDm);

        plugin.getConfig().set("Dnd_player.Dms." + newDm.getName() + ".PlayerUUID", newDm.getUniqueId().toString());
        plugin.saveConfig();

    }

    /*
     * Remove Dm
     */
    public void removeDm(Dm dm) {

        dms.remove(dm);
        ph.removeDndPlayer(dm);
        plugin.getConfig().set("Dnd_player.Dms." + dm.getName(), null);
        plugin.saveConfig();

    }

    public boolean isPlayerDm(UUID id) {
        if (dms.size() > 0) {
            for (Dm dm : dms) {
                if (dm.getUniqueId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Get Dm reference with his name
     */
    public Dm getDm(UUID id) {
        if (dms.size() > 0) {
            for (Dm dm : dms) {
                if (dm.getUniqueId().equals(id)) {
                    return dm;
                }
            }
        }
        return null;
    }

    /*
     * Get reference of the list of all the dms
     */
    public ArrayList<Dm> getDms() {
        return dms;
    }

    public boolean isSessionStarted() {
        return isSessionStarted;
    }

    public void startSession(Dm dmSender) {
        isSessionStarted = true;

        ArrayList<DndPlayer> dndPlayers = PluginHandler.getInstance().getDndPlayers();

        //teleport if had checkpoint
        if (dmSender.hasCheckpoint()) {
            for (Dm dm : dms) {
                dm.sendMessage("Teleporting all hero to last checkpoint.");
            }

            for (DndPlayer dndP : dndPlayers) {
                if(!(dndP.hasChair())){
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
            for (DndPlayer dndP : dndPlayers) {
                dndP.sendTitle("Minecraft DnD", "The adventure may begin...", 10, 70, 20);
                if(!(dndP.hasChair())){
                    dndP.setChairPosition(dndP.getLocation());
                }
            }
        }



        dmSender.sendMessage("Session started");
    }

    public void endSession(Dm dmSender) {
        ArrayList<DndPlayer> dndPlayers = PluginHandler.getInstance().getDndPlayers();

        for (DndPlayer dndP : dndPlayers) {
            dndP.setCheckpoint(dndP.getLocation());
            if (dndP.hasChair()) {
                dndP.teleport(dndP.getChairPosition());
            }
            dndP.teleport(dmSender.getChairPosition());
            dndP.sendTitle("To be continued...", "End of the session", 10, 70, 20);
        }

        for (Dm dm : dms) {
            dm.sendMessage("Saved checkpoint and teleported heros to the dnd room.");
        }
        isSessionStarted = false;
        dmSender.sendMessage("Session ended");
    }
}
