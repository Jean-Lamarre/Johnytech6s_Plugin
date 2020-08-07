package io.github.johnytech6;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.UUID;

public class OfflineDndPlayer {

    private OfflinePlayer offlinePlayerRef;

    public OfflineDndPlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayerRef = offlinePlayer;
    }

    public UUID getUniqueId() {
        return offlinePlayerRef.getUniqueId();
    }

    public String getName() {
        return offlinePlayerRef.getName();
    }

    public boolean wasDm(FileConfiguration config) {
        Set<String> listDmsNames = config.getConfigurationSection("Dnd_player.Dms").getKeys(false);
        if (listDmsNames != null) {
            for (String name : listDmsNames) {
                String id = config.getString("Dnd_player.Dms." + name + ".PlayerUUID");
                if (!(id.equals("defaultID"))) {
                    if (((UUID.fromString(id)).equals(offlinePlayerRef.getUniqueId()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
