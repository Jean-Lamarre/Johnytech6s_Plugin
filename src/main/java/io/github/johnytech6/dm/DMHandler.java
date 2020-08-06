package io.github.johnytech6.dm;

import java.util.*;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.PluginHandler;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.hero.HeroHandler;
import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.theft.TeftHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


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
    private ArrayList<Dm> dms = new ArrayList<Dm>();
    private ArrayList<OfflinePlayer> awaitedDms = new ArrayList<OfflinePlayer>();

    private boolean isSessionStarted = false;

    public void loadConfig(FileConfiguration config) {

        Set<String> listDmsNames = config.getConfigurationSection("Dms").getKeys(false);

        if (listDmsNames != null) {
            for (String name : listDmsNames) {

                String id = config.getString("Dms."+name+".PlayerUUID");
                if(!(id.equals("defaultID"))) {
                    awaitedDms.add(Bukkit.getOfflinePlayer(UUID.fromString(id)));
                }
            }
        }
        else{
        }
    }

    /*
     * Toggle Dm mode
     */
    public boolean ToggleDmMode(Player p, boolean verbose) {
        return setDmMode(p, !(isPlayerDm(p.getName())), verbose);
    }

    public boolean setDmMode(Player p, boolean beDm, boolean verbose) {

        if (beDm) {

            if(hh.isPlayerHero(p.getName())){
                hh.removeHero(hh.getHero(p.getName()));
            }
            Dm newDm = new Dm(p);
            addDm(newDm);
            p.setInvulnerable(true);
            p.setGameMode(GameMode.CREATIVE);

            if (verbose) {
                p.sendMessage("***You are now the DM***");
                p.sendMessage("You are invicible.");
            }

            setDmInvisibility(newDm, true, verbose);
            setDmVision(newDm, true, verbose);
            pph.setPuppeterMode(newDm.getPlayer(), true, verbose);
            newDm.setPuppeterPower(true);
            th.setTeftMode(newDm.getPlayer(), true, verbose);
            newDm.setTeftPower(true);

        } else {
            if (verbose) {
                p.sendMessage("***You are not the DM anymore***");
                p.sendMessage("You are not invicible anymore.");
            }
            Dm dm = getDm(p.getName());
            setDmInvisibility(dm, false, true);
            setDmVision(dm, false, true);
            pph.setPuppeterMode(dm.getPlayer(), false, true);
            dm.setPuppeterPower(false);
            th.setTeftMode(dm.getPlayer(), false, true);
            dm.setTeftPower(false);

            removeDm(getDm(p.getName()));
            hh.addHero(new Hero(p));
            p.setInvulnerable(false);
        }

        return true;
    }

    /*
     * Toggle Dm invisibility
     */
    public boolean dmInvisibility(Dm dm, boolean verbose) {
        return setDmInvisibility(dm, !(dm.hasPotionEffect(PotionEffectType.INVISIBILITY)), verbose);
    }

    public boolean setDmInvisibility(Dm dm, boolean beInvisible, boolean verbose) {

        if (beInvisible) {
            dm.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, true));
            if (verbose) {
                dm.sendMessage("You are now invisible");
            }
        } else {
            dm.removePotionEffect(PotionEffectType.INVISIBILITY);
            if (verbose) {
                dm.sendMessage("You are not invisible anymore");
            }
        }
        dm.setInvisibility(beInvisible);

        return true;
    }

    /*
     * Toggle Dm night vision
     */
    public boolean dmVision(Dm dm, boolean verbose) {
        return setDmVision(dm, !(dm.hasPotionEffect(PotionEffectType.NIGHT_VISION)), verbose);
    }

    public boolean setDmVision(Dm dm, boolean hasNightVision, boolean verbose) {

        if (hasNightVision) {
            dm.addPotionEffect(
                    new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false, true));
            if (verbose) {
                dm.sendMessage("You have night vision");
            }
        } else {
            dm.removePotionEffect(PotionEffectType.NIGHT_VISION);
            if (verbose) {
                dm.sendMessage("You lost night vision");
            }
        }
        dm.setNightVision(hasNightVision);

        return true;
    }

    /*
     * Add a Dm
     */
    public void addDm(Dm newDm) {
        dms.add(newDm);
        ph.addDndPlayer(newDm);

        plugin.getConfig().set("Dms."+newDm.getName()+".PlayerUUID", newDm.getUniqueId().toString());
        plugin.saveConfig();

    }

    /*
     * Remove Dm
     */
    public void removeDm(Dm dm) {
        dms.remove(dm);
        ph.removeDndPlayer(dm);

        plugin.getConfig().set("Dms."+dm.getName(), null);
        plugin.saveConfig();

    }

    public void removeAwaitingDm(Player p) {
        if ((isAwaitedDm(p.getUniqueId()))) {
            awaitedDms.remove(p);
        }
    }

    public boolean isPlayerDm(String name) {
        if (dms.size() > 0) {
            for (Dm dm : dms) {
                if (dm.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAwaitedDm(UUID id) {
        if (awaitedDms.size() > 0) {
            for (OfflinePlayer p : awaitedDms) {
                if (p.getUniqueId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Get Dm reference with his name
     */
    public Dm getDm(String name) {
        if (dms.size() > 0) {
            for (Dm dm : dms) {
                if (dm.getName().equalsIgnoreCase(name)) {
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

    public ArrayList<OfflinePlayer> getAwaitedDms() {
        return awaitedDms;
    }

/*
    public Location getDndRoomLocation() {
        return dndRoomLocation;
    }


    public boolean setDndRoom(Location targetLocation) {
        if (targetLocation == null) {
            return false;
        }
        this.dndRoomLocation = targetLocation;
        plugin.getConfig().set("dnd_room_location", dndRoomLocation);
        plugin.saveConfig();
        return true;
    }
*/

    public boolean isSessionStarted() {
        return isSessionStarted;
    }

    private boolean dmsHasCheckpoint() {
        for (Dm dm : dms) {
            if (dm.hasCheckpoint()) {
                return true;
            }
        }
        return false;
    }

    private boolean dmsHasChair() {
        for (Dm dm : dms) {
            if (dm.hasChair()) {
                return true;
            }
        }
        return false;
    }

    public void startSession(Dm dmSender) {
        isSessionStarted = true;

        ArrayList<DndPlayer> dndPlayers = PluginHandler.getInstance().getDndPlayers();

        for (DndPlayer dndP : dndPlayers) {
            dndP.sendTitle("DnD", "The adventure may begin...", 10, 70, 20);
        }

        //teleport if had checkpoint
        if (dmSender.hasCheckpoint()) {
            for (Dm dm : dms) {
                dm.sendMessage("Teleporting all hero to last checkpoint.");
            }

            for (DndPlayer dndP : dndPlayers) {
                if(dndP.hasCheckpoint()){
                    dndP.teleport(dndP.getCheckpoint());
                }
                else{
                    dndP.teleport(dmSender.getCheckpoint());
                }
            }

        } else {
            dmSender.sendMessage("No checkpoint was saved. Teleport all player manually.");
        }

        if (!(dmSender.hasChair())) {
            dmSender.setChairPosition(dmSender.getLocation());
            dmSender.sendMessage("Dnd room location saved");
        }

        dmSender.sendMessage("Session started");
    }

    public void endSession(Dm dmSender) {

        for (Dm dm : dms) {
            dm.sendMessage("Saving checkpoint and teleporting heros to the dnd room.");
        }

        ArrayList<DndPlayer> dndPlayers = PluginHandler.getInstance().getDndPlayers();

        for (DndPlayer dndP : dndPlayers) {
            dndP.setCheckpoint(dndP.getLocation());
            if (dndP.hasChair()) {
                dndP.teleport(dndP.getChairPosition());
            }
            dndP.teleport(dmSender.getChairPosition());
        }

        isSessionStarted = false;
        dmSender.sendMessage("Session ended");
    }
}
