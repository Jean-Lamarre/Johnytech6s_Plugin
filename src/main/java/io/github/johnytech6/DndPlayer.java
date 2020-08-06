package io.github.johnytech6;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public interface DndPlayer {

    public Location getCheckpoint();

    public void setCheckpoint(Location checkpoint);

    public boolean hasCheckpoint();

    public Location getChairPosition();

    public void setChairPosition(Location chairPosition);

    public boolean hasChair();

    public String getName();

    public Player getPlayer();

    public UUID getUniqueId();

    public void sendTitle(String title, String s, int i, int i1, int i2);

    public void sendMessage(String s);

    public void teleport(Location targetLocation);

    public Location getLocation();

    public void addPotionEffect(PotionEffect potionEffect);

    public void removePotionEffect(PotionEffectType potionEffect);

    public boolean hasPotionEffect(PotionEffectType potionEffect);

    public void loadConfig();
}
