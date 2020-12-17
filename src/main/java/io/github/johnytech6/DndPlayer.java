package io.github.johnytech6;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public interface DndPlayer {

    Location getCheckpoint();

    void setCheckpoint(Location checkpoint);

    boolean hasCheckpoint();

    Location getChairPosition();

    void setChairPosition(Location chairPosition);

    boolean hasChair();

    boolean isVerbose();

    void setVerbose(boolean state);

    String getName();

    Player getPlayer();

    UUID getUniqueId();

    void sendTitle(String title, String s, int i, int i1, int i2);

    void sendMessage(String s);

    void teleport(Location targetLocation);

    Location getLocation();

    void addPotionEffect(PotionEffect potionEffect);

    void removePotionEffect(PotionEffectType potionEffect);

    boolean hasPotionEffect(PotionEffectType potionEffect);

    void loadConfig(Player player);

    void setGameMode(GameMode gameMode);

    String toString();
}
