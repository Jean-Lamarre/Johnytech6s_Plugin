package io.github.johnytech6.Handler;

import java.util.*;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.OfflineDndPlayer;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.dm.theft.Teft;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.puppeter.Puppeter;

public class PluginHandler {

    // ---------------------------SINGLETON
    // IMPLEMENTATION-------------------------------------------
    // static variable single_instance of type Singleton
    private static PluginHandler single_instance = null;

    // private constructor restricted to this class itself
    private PluginHandler() {
    }

    public static PluginHandler getInstance() {
        if (single_instance == null)
            single_instance = new PluginHandler();

        return single_instance;
    }
    // --------------------------------------------------------------------------------------------

    private static HeroHandler hh = HeroHandler.getInstance();
    private static DMHandler dmh = DMHandler.getInstance();
    private static PuppeterHandler pph = PuppeterHandler.getInstance();
    private static TeftHandler th = TeftHandler.getInstance();

    private HashMap<UUID, DndPlayer> dndPlayers = new HashMap<UUID, DndPlayer>();

    private HashMap<UUID, OfflineDndPlayer> offlineDndPlayers = new HashMap<UUID, OfflineDndPlayer>();

    public void loadConfig(FileConfiguration config) {
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

    public HashMap<UUID, DndPlayer> getDndPlayers() {
        return dndPlayers;
    }

    public HashMap<UUID, OfflineDndPlayer> getofflineDndPlayers() {
        return offlineDndPlayers;
    }

    public void addDndPlayer(DndPlayer dndPlayer) {
        dndPlayers.put(dndPlayer.getUniqueId(), dndPlayer);
    }

    public void removeDndPlayer(DndPlayer dndPlayer) {
        dndPlayers.remove(dndPlayer.getUniqueId());
    }

    public void removeOfflineDndPlayers(OfflineDndPlayer oDndP) {
        offlineDndPlayers.remove(oDndP);
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

    public void johnytech6Stat(CommandSender p) {

        ArrayList<String> offlineDndPlayerName = new ArrayList<String>();
        ArrayList<String> dndPlayerName = new ArrayList<String>();
        ArrayList<String> namesPuppeter = new ArrayList<String>();
        ArrayList<String> namesMorphedPuppeter = new ArrayList<String>();
        ArrayList<String> namesDm = new ArrayList<String>();
        ArrayList<String> namesHero = new ArrayList<String>();
        ArrayList<String> namesTeft = new ArrayList<String>();

        HashMap<UUID, Puppeter> listPuppeter = pph.getPuppeters();
        HashMap<UUID, Puppeter> listMorphedPuppeter = pph.getMorphPlayers();
        HashMap<UUID, Dm> listDm = dmh.getDms();
        HashMap<UUID, Hero> listHero = hh.getHeros();
        HashMap<UUID, Teft> listTeft = th.getTeftPlayers();

        dndPlayers.forEach((id, dndPlayer) ->
                dndPlayerName.add(dndPlayer.getName())
        );

        offlineDndPlayers.forEach((id,offlineDndPlayer) ->
                offlineDndPlayerName.add(offlineDndPlayer.getName())
        );

        listPuppeter.forEach((id, puppeter) ->
                namesPuppeter.add(puppeter.getName())
        );

        listMorphedPuppeter.forEach((id, mPuppeter) ->
                namesMorphedPuppeter.add(mPuppeter.getName())
        );

        listDm.forEach((id, dm) ->
                namesDm.add(dm.getName())
        );

        listHero.forEach((id, h) ->
                namesHero.add(h.getName())
        );

        listTeft.forEach((id, teft) ->
                namesTeft.add(teft.getName())
        );

        p.sendMessage("DnD players: " + dndPlayerName.toString());
        p.sendMessage("Offline DnD players: " + offlineDndPlayerName.toString());
        p.sendMessage("Puppeters: " + namesPuppeter.toString());
        p.sendMessage("Morph puppeters: " + namesMorphedPuppeter.toString());
        p.sendMessage("Dms: " + namesDm.toString());
        p.sendMessage("Heros: " + namesHero.toString());
        p.sendMessage("Tefts: " + namesTeft.toString());

    }
}
