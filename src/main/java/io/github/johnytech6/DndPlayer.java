package io.github.johnytech6;

import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
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

    void setNightVision(boolean aBoolean);

    boolean hasNightVision();

    void setInvisibility(boolean aBoolean);

    boolean isInvisible();

    boolean isVerbose();

    void setVerbose(boolean state);

    void updatePlayerReference(Player player);

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

    void setGameMode(GameMode gameMode);

    String getRole() throws Exception;

    String toString();

}
