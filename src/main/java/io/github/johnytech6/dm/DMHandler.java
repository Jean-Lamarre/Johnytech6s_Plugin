package io.github.johnytech6.dm;

import java.util.*;

import io.github.johnytech6.HeroHandler;
import io.github.johnytech6.JohnytechPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

    private Plugin plugin = JohnytechPlugin.getPlugin();

    private HashMap<UUID, Location> playerCheckpoint = new HashMap<UUID, Location>();
    private HashMap<UUID, Location> dmsCheckpoint = new HashMap<UUID, Location>();
    private HashMap<UUID, Location> playersChairs = new HashMap<UUID, Location>();
    private HashMap<UUID, Location> dmsChairs = new HashMap<UUID, Location>();

    // list of Dm
    private ArrayList<Player> dms = new ArrayList<Player>();
    private ArrayList<OfflinePlayer> awaitedDms = new ArrayList<OfflinePlayer>();

    private boolean isSessionStarted = false;

    public void loadConfig(FileConfiguration config) {
        ArrayList<String> listDmsUuid = (ArrayList<String>) config.getList("Dms");
        if (listDmsUuid != null) {
            for (String id : listDmsUuid) {
                awaitedDms.add((Bukkit.getOfflinePlayer(UUID.fromString(id))));
            }
        }

        if (config.contains("player_checkpoint")) {
            playerCheckpoint = (HashMap) config.getConfigurationSection("player_checkpoint").getValues(false);
        }
        if (config.contains("dms_checkpoint")) {
            dmsCheckpoint = (HashMap) config.getConfigurationSection("dms_checkpoint").getValues(false);
        }
        if (config.contains("players_chairs")) {
            playersChairs = (HashMap) config.getConfigurationSection("players_chairs").getValues(false);
        }
        if (config.contains("dms_chairs")) {
            dmsChairs = (HashMap) config.getConfigurationSection("dms_chairs").getValues(false);
        }
    }

    /*
     * Toggle Dm mode
     */
    public boolean ToggleDmMode(Player p) {
        return setDmMode(p, !(isPlayerDm(p.getName())));
    }

    public boolean setDmMode(Player p, boolean beDm) {
        if (beDm) {
            hh.removeHero(p);
            AddDm(p);
            p.setInvulnerable(true);
            p.setGameMode(GameMode.CREATIVE);
            if (!isAwaitedDm(p.getUniqueId())) {
                p.sendMessage("***You are now the DM***");
                p.sendMessage("You are invicible.");
            }
        } else {
            RemoveDm(getDm(p.getName()));
            hh.addHero(p);
            p.setInvulnerable(false);
            p.sendMessage("***You are not the DM anymore***");
            p.sendMessage("You are not invicible anymore.");
        }

        return true;
    }

    /*
     * Toggle Dm invisibility
     */
    public boolean DmInvisibility(Player p) {
        return setDmInvisibility(p, !(p.hasPotionEffect(PotionEffectType.INVISIBILITY)));
    }

    public boolean setDmInvisibility(Player p, boolean beInvisible) {

        if (beInvisible) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, true));
            if (!isAwaitedDm(p.getUniqueId())) {
                p.sendMessage("You are now invisible");
            }
        } else {
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.sendMessage("You are not invisible anymore");
        }

        return true;
    }

    /*
     * Toggle Dm night vision
     */
    public boolean DmVision(Player p) {
        return setDmVision(p, !(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)));
    }

    public boolean setDmVision(Player p, boolean hasNightVision) {

        if (hasNightVision) {
            p.addPotionEffect(
                    new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false, true));
            if (!isAwaitedDm(p.getUniqueId())) {
                p.sendMessage("You have night vision");
            }
        } else if (!hasNightVision) {
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            p.sendMessage("You lost night vision");
        } else {
            return false;
        }

        return true;
    }

    /*
     * Add a Dm
     */
    public void AddDm(Player p) {
        if (!(isPlayerDm(p.getName()))) {
            dms.add(p);

            updateDmsUuid();
        }
    }

    /*
     * Remove Dm
     */
    public void RemoveDm(Player p) {
        if ((isPlayerDm(p.getName()))) {
            dms.remove(p);

            updateDmsUuid();
        }
    }

    public void RemoveAwaitingDm(Player p) {
        if ((isAwaitedDm(p.getUniqueId()))) {
            awaitedDms.remove(p);
        }
    }

    private void updateDmsUuid() {
        ArrayList<String> uuids = new ArrayList<String>();
        for (Player dm : dms) {
            uuids.add(dm.getUniqueId().toString());
        }
        plugin.getConfig().set("Dms", uuids);
        plugin.saveConfig();
    }

    public boolean isPlayerDm(String name) {
        if (dms.size() > 0) {
            for (Player p : dms) {
                if (p.getName().equals(name)) {
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
    public Player getDm(String name) {
        if (dms.size() > 0) {
            for (Player p : dms) {
                if (p.getName().equalsIgnoreCase(name)) {
                    return p;
                }
            }
        }
        return null;
    }

    /*
     * Get reference of the list of all the dms
     */
    public ArrayList<Player> getDms() {
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

    public void startSession(Player dmSender) {
        isSessionStarted = true;

        for (Player dm : dms) {
            dm.sendTitle("DnD", "The adventure may begin...", 10, 70, 20);
        }

        ArrayList<Player> heros = hh.getHeros();
        for (Player h : heros) {
            h.sendTitle("DnD", "The adventure may begin...", 10, 70, 20);
        }

        //teleport if had checkpoint
        if (playerCheckpoint.size() != 0 || dmsCheckpoint.size() != 0) {
            for (Player dm : dms) {
                dm.sendMessage("Teleporting all hero to last checkpoint.");
            }
            heros = hh.getHeros();
            for (Player h : heros) {
                Location targetLocation = playerCheckpoint.get(h.getUniqueId());
                h.teleport(targetLocation);
            }
            for (Player dm : dms) {
                Location targetLocation = dmsCheckpoint.get(dm.getUniqueId());
                dm.teleport(targetLocation);
            }
        } else {
            dmSender.sendMessage("No checkpoint saved, you need to teleport player manually.");
        }

        //save chairs
        for (Player h : heros) {
            playersChairs.put(h.getUniqueId(), h.getLocation());
            plugin.getConfig().createSection("players_chairs", playersChairs);
        }
        for (Player dm : dms) {
            dmsChairs.put(dm.getUniqueId(), dm.getLocation());
            plugin.getConfig().createSection("dms_chairs", dmsChairs);
        }

        dmSender.sendMessage("Session started");
        plugin.saveConfig();

    }

    public void endSession(Player dmSender) {

        for (Player dm : dms) {
            dm.sendMessage("Saving checkpoint and teleporting heros to the dnd room.");
        }

        ArrayList<Player> heros = hh.getHeros();
        for (Player h : heros) {
            playerCheckpoint.put(h.getUniqueId(), h.getLocation());
            plugin.getConfig().createSection("player_checkpoint", playerCheckpoint);

            Location targetLocation = playersChairs.get(h.getUniqueId());
            h.teleport(targetLocation);
        }

        for (Player dm : dms) {
            dmsCheckpoint.put(dm.getUniqueId(), dm.getLocation());
            plugin.getConfig().createSection("dms_checkpoint", dmsCheckpoint);

            Location targetLocation = dmsChairs.get(dm.getUniqueId());
            dm.teleport(targetLocation);
        }
        isSessionStarted = false;
        dmSender.sendMessage("Session ended");
        plugin.saveConfig();
    }
}
