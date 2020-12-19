package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.OfflineDndPlayer;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.puppeter.Puppeter;
import org.bukkit.plugin.Plugin;

public class PluginHandler {

    private final HeroHandler heroHandler;
    private final DMHandler dmHandler;
    private final PuppeterHandler puppeterHandler;
    private FileConfiguration config;

    private final HashMap<UUID, DndPlayer> dndPlayers = new HashMap<>();

    private final HashMap<UUID, OfflineDndPlayer> offlineDndPlayers = new HashMap<>();
    private final Plugin plugin;

    public PluginHandler(Plugin plugin, FileConfiguration config) {
        this.heroHandler = new HeroHandler(this);
        this.dmHandler = new DMHandler(this);
        this.puppeterHandler = new PuppeterHandler(this);
        this.plugin = plugin;
        this.config = config;
    }

    public void savePlayerCheckpoint(DndPlayer dndPlayer, Location checkpoint) {
        try {
            config.set("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".checkpoint", checkpoint);
            plugin.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePlayerChairPosition(DndPlayer dndPlayer, Location chairPosition) {
        try {
            config.set("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".chair_position", chairPosition);
            plugin.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveInvisbility(DndPlayer dndPlayer, boolean state) {
        try {
            config.set("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".isInvisible", state);
            plugin.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveNightVision(DndPlayer dndPlayer, Boolean state) {
        try {
            config.set("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".hasNightVision", state);
            plugin.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePuppeterPower(Dm dm, Boolean state) {
        config.set("Dnd_player.Dms." + dm.getName() + ".hasPuppeterPower", state);
        plugin.saveConfig();
    }

    public void saveTeftPower(DndPlayer dndPlayer, Boolean state) {
        try {
            config.set("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".hasTeftPower", state);
            plugin.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFrozenState(DndPlayer dndPlayer, Boolean state){
        if(dndPlayer instanceof Hero) {
            config.set("Dnd_player.Heros." + dndPlayer.getName() + ".frozen_state", state);
            plugin.saveConfig();
        }
        else{
            sendMessageToOperator(ChatColor.GRAY + "Trying to save frozen state but target was not a hero.");
        }
    }

    public void saveAllDmPower(Dm dm, Boolean state) {
        saveTeftPower(dm, state);
        saveInvisbility(dm, state);
        saveNightVision(dm, state);
        savePuppeterPower(dm, state);
    }

    /**
     * Send Message to all operator
     * @param s
     */
    public void sendMessageToOperator(String s) {
        Collection<DndPlayer> dndPlayersCollection = dndPlayers.values();
        for(DndPlayer dndPlayer: dndPlayersCollection){
            if(dndPlayer.getPlayer().isOp()){
                dndPlayer.sendMessage(s);
            }
        }
    }

    /**
     * Load config for all player at start. It create OfflineDndPlayer
     *
     * @param config
     */
    public void loadOfflinePlayer(FileConfiguration config) {
        Set<String> listDmsNames = config.getConfigurationSection("Dnd_player.Dms").getKeys(false);
        Set<String> listHerosNames = config.getConfigurationSection("Dnd_player.Heros").getKeys(false);

        if (listDmsNames != null) {
            for (String name : listDmsNames) {
                String id = config.getString("Dnd_player.Dms." + name + ".PlayerUUID");
                if (!(id.equals("defaultID"))) {
                    UUID playerId = UUID.fromString(id);
                    offlineDndPlayers.put(playerId, new OfflineDndPlayer(Bukkit.getOfflinePlayer(playerId)));
                }
            }
        }
        if (listHerosNames != null) {
            for (String name : listHerosNames) {
                String id = config.getString("Dnd_player.Heros." + name + ".PlayerUUID");
                if (!(id.equals("defaultID"))) {
                    UUID playerId = UUID.fromString(id);
                    offlineDndPlayers.put(playerId, new OfflineDndPlayer(Bukkit.getOfflinePlayer(playerId)));
                }
            }
        }
    }

    /**
     * set player config from saved data
     *
     * @param dndPlayer
     */
    public void loadPlayerConfig(DndPlayer dndPlayer) {

        dndPlayer.updatePlayerReference(dndPlayer.getPlayer());

        dndPlayer.setVerbose(false);
        try {
            dndPlayer.setCheckpoint(config.getLocation("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".checkpoint"));
            dndPlayer.setChairPosition(config.getLocation("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".chair_position"));
            dndPlayer.setNightVision(config.getBoolean("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".hasNightVision"));
            dndPlayer.setInvisibility(config.getBoolean("Dnd_player." + dndPlayer.getRole() + "." + dndPlayer.getName() + ".isInvisible"));
            if (dndPlayer instanceof Dm) {
                ((Dm) dndPlayer).setPuppeterPower(config.getBoolean("Dnd_player.Dms." + dndPlayer.getName() + ".hasPuppeterPower"));
                puppeterHandler.setPuppeterMode(dndPlayer.getPlayer(), ((Dm)dndPlayer).havePuppeterPower(), false);
                ((Dm) dndPlayer).setTeftPower(config.getBoolean("Dnd_player.Dms." + dndPlayer.getName() + ".hasTeftPower"));
                //TODO SET TEFT POWER
            }else if(dndPlayer instanceof Hero){
                ((Hero) dndPlayer).setFrozenState(config.getBoolean("Dnd_player.Heros." + dndPlayer.getName() + ".frozen_state"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dndPlayer.setVerbose(true);
    }

    public HashMap<UUID, DndPlayer> getDndPlayers() {
        return dndPlayers;
    }

    public HashMap<UUID, OfflineDndPlayer> getOfflineDndPlayers() {
        return offlineDndPlayers;
    }

    public void addDndPlayer(DndPlayer dndPlayer) {
        dndPlayers.put(dndPlayer.getUniqueId(), dndPlayer);
    }

    public void removeDndPlayer(DndPlayer dndPlayer) {
        dndPlayers.remove(dndPlayer.getUniqueId());
    }

    public void removeOfflineDndPlayers(OfflineDndPlayer oDndP) {
        offlineDndPlayers.remove(oDndP.getUniqueId());
    }

    public boolean isPlayerDndPlayer(UUID id) {
        if (dndPlayers.containsKey(id)) {
            return true;
        }
        return false;
    }

    public boolean isOfflineDndPlayers(UUID id) {
        if (offlineDndPlayers.containsKey(id)) {
            return true;
        }

        return false;
    }

    public DndPlayer getDndPlayer(UUID uuid) {
        return dndPlayers.get(uuid);
    }

    public OfflineDndPlayer getOfflineDndPlayer(UUID uuid) {
        return offlineDndPlayers.get(uuid);
    }


    //-------------GET HANDLER------------
    public HeroHandler getHeroHandler() {
        return this.heroHandler;
    }

    public DMHandler getDmHandler() {
        return this.dmHandler;
    }

    public PuppeterHandler getPuppeterHandler() {
        return this.puppeterHandler;
    }
    //----------------------------------

    public Plugin getPlugin() {
        return this.plugin;
    }

    public FileConfiguration getConfig(){
        return this.config;
    }



}
