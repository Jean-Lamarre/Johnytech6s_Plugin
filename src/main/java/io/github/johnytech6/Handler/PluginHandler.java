package io.github.johnytech6.Handler;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

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

    private ArrayList<DndPlayer> dndPlayers = new ArrayList<DndPlayer>();

    private ArrayList<OfflineDndPlayer> offlineDndPlayers = new ArrayList<OfflineDndPlayer>();

    public void loadConfig(FileConfiguration config) {
        Set<String> listDmsNames = config.getConfigurationSection("Dnd_player.Dms").getKeys(false);
        Set<String> listHerosNames = config.getConfigurationSection("Dnd_player.Heros").getKeys(false);

        if (listDmsNames != null) {
            for (String name : listDmsNames) {
                String id = config.getString("Dnd_player.Dms." + name + ".PlayerUUID");
                if (!(id.equals("defaultID"))) {
                    offlineDndPlayers.add(new OfflineDndPlayer(Bukkit.getOfflinePlayer(UUID.fromString(id))));
                }
            }
        }
        if (listHerosNames != null) {
            for (String name : listHerosNames) {
                String id = config.getString("Dnd_player.Heros." + name + ".PlayerUUID");
                if (!(id.equals("defaultID"))) {
                    offlineDndPlayers.add(new OfflineDndPlayer(Bukkit.getOfflinePlayer(UUID.fromString(id))));
                }
            }
        }
    }

    public ArrayList<DndPlayer> getDndPlayers() {
        return dndPlayers;
    }

    public ArrayList<OfflineDndPlayer> getofflineDndPlayers() {
        return offlineDndPlayers;
    }

    public void addDndPlayer(DndPlayer dndPlayer) {
        dndPlayers.add(dndPlayer);
    }

    public void removeDndPlayer(DndPlayer dndPlayer) {
        dndPlayers.remove(dndPlayer);
    }

    public void removeOfflineDndPlayers(OfflineDndPlayer oDndP) {
        offlineDndPlayers.remove(oDndP);
    }

    public boolean isPlayerDndPlayer(Player p) {
        if (dndPlayers.size() > 0) {
            for (DndPlayer dndP : dndPlayers) {
                if (dndP.getUniqueId().equals(p.getUniqueId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOfflineDndPlayers(UUID id) {
        if (offlineDndPlayers.size() > 0) {
            for (OfflineDndPlayer p : offlineDndPlayers) {
                if (p.getUniqueId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public DndPlayer getDndPlayer(UUID uuid) {
        if (dndPlayers.size() > 0) {
            for (DndPlayer dndP : dndPlayers) {
                if (dndP.getUniqueId().equals(uuid)) {
                    return dndP;
                }
            }
        }
        return null;
    }

    public OfflineDndPlayer getOfflineDndPlayer(UUID uuid) {
        if (offlineDndPlayers.size() > 0) {
            for (OfflineDndPlayer oDndP : offlineDndPlayers) {
                if (oDndP.getUniqueId().equals(uuid)) {
                    return oDndP;
                }
            }
        }
        return null;
    }

    public void johnytech6Stat(CommandSender p) {

        ArrayList<String> offlineDndPlayerName = new ArrayList<String>();
        ArrayList<String> DndPlayerName = new ArrayList<String>();
        ArrayList<String> namesPuppeter = new ArrayList<String>();
        ArrayList<String> namesMorphedPuppeter = new ArrayList<String>();
        ArrayList<String> namesDm = new ArrayList<String>();
        ArrayList<String> namesHero = new ArrayList<String>();
        ArrayList<String> namesTeft = new ArrayList<String>();

        ArrayList<Puppeter> listPuppeter = pph.getPuppeters();
        ArrayList<Puppeter> listMorphedPuppeter = pph.getMorphPlayers();
        ArrayList<Dm> listDm = dmh.getDms();
        ArrayList<Hero> listHero = hh.getHeros();
        ArrayList<Teft> listTeft = th.getTeftPlayers();

        for (DndPlayer dndP : dndPlayers) {
            DndPlayerName.add(dndP.getName());
        }
        for (OfflineDndPlayer oDndP : offlineDndPlayers) {
            offlineDndPlayerName.add(oDndP.getName());
        }
        for (Puppeter cp : listPuppeter) {
            namesPuppeter.add(cp.getName());
        }
        for (Puppeter cp : listMorphedPuppeter) {
            namesMorphedPuppeter.add(cp.getName());
        }
        for (Dm dm : listDm) {
            namesDm.add(dm.getName());
        }
        for (Hero h : listHero) {
            namesHero.add(h.getName());
        }
        for (Teft cp : listTeft) {
            namesTeft.add(cp.getName());
        }

        p.sendMessage("DnD players: " + DndPlayerName.toString());
        p.sendMessage("Offline DnD players: " + offlineDndPlayerName.toString());
        p.sendMessage("Puppeters: " + namesPuppeter.toString());
        p.sendMessage("Morph puppeters: " + namesMorphedPuppeter.toString());
        p.sendMessage("Dms: " + namesDm.toString());
        p.sendMessage("Heros: " + namesHero.toString());
        p.sendMessage("Tefts: " + namesTeft.toString());

    }
}
