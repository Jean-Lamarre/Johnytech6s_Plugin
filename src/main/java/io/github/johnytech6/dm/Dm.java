package io.github.johnytech6.dm;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.JohnytechPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Dm implements DndPlayer {

    private static Plugin plugin = JohnytechPlugin.getPlugin();

    private Player playerRef;

    private Location checkpoint;
    private Location chairPosition;

    private boolean isInvisible = false;
    private boolean hasNightVision = false;
    private boolean hasPuppeterPower = false;
    private boolean hasTeftPower = false;

    public Dm(Player p) {
        this.playerRef = p;
    }

    @Override
    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();

        setCheckpoint(config.getLocation("Dms."+playerRef.getName()+".checkpoint"));
        setChairPosition(config.getLocation("Dms."+playerRef.getName()+".chair_position"));
        setInvisibility(config.getBoolean("Dms."+playerRef.getName()+".isInvisible"));
        setPuppeterPower(config.getBoolean("Dms."+playerRef.getName()+".hasPuppeterPower"));
        setTeftPower(config.getBoolean("Dms."+playerRef.getName()+".hasTeftPower"));
        setNightVision(config.getBoolean("Dms."+playerRef.getName()+".hasNightVision"));
    }

    @Override
    public Location getCheckpoint() {
        return checkpoint;
    }

    @Override
    public void setCheckpoint(Location checkpoint) {
        this.checkpoint = checkpoint;

        plugin.getConfig().set("Dms."+playerRef.getName()+".checkpoint", checkpoint);
        plugin.saveConfig();
    }

    @Override
    public boolean hasCheckpoint() {
        if (checkpoint != null) {
            return true;
        }
        return false;
    }

    @Override
    public Location getChairPosition() {
        return chairPosition;
    }

    @Override
    public void setChairPosition(Location chairPosition) {
        this.chairPosition = chairPosition;
        plugin.getConfig().set("Dms."+playerRef.getName()+".chair_position", chairPosition);
        plugin.saveConfig();
    }

    @Override
    public boolean hasChair() {
        if (chairPosition != null) {
            return true;
        }
        return false;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisibility(boolean state) {
        isInvisible = state;
        plugin.getConfig().set("Dms."+playerRef.getName()+".isInvisible", state);
        plugin.saveConfig();
    }

    public boolean hasPuppeterPower() {
        return hasPuppeterPower;
    }

    public void setPuppeterPower(boolean state) {
        hasPuppeterPower = state;
        plugin.getConfig().set("Dms."+playerRef.getName()+".hasPuppeterPower", state);
        plugin.saveConfig();
    }

    public boolean hasTeftPower() {
        return hasTeftPower;
    }

    public void setTeftPower(boolean state) {
        hasTeftPower = state;
        plugin.getConfig().set("Dms."+playerRef.getName()+".hasTeftPower", state);
        plugin.saveConfig();
    }

    public boolean hasNightVision() {
        return hasNightVision;
    }

    public void setNightVision(boolean state) {
        hasNightVision = state;
        plugin.getConfig().set("Dms."+playerRef.getName()+".hasNightVision", state);
        plugin.saveConfig();
    }

    @Override
    public String getName() {
        return playerRef.getName();
    }

    @Override
    public Player getPlayer() {
        return this.playerRef;
    }

    @Override
    public UUID getUniqueId() {
        return playerRef.getUniqueId();
    }

    @Override
    public void sendTitle(String title, String s, int i, int i1, int i2) {
        playerRef.sendTitle(title, s, i, i1, i2);
    }

    @Override
    public void sendMessage(String s) {
        playerRef.sendMessage(s);
    }

    @Override
    public void teleport(Location targetLocation) {
        playerRef.teleport(targetLocation);
    }

    @Override
    public Location getLocation() {
        return playerRef.getLocation();
    }

    @Override
    public void addPotionEffect(PotionEffect potionEffect) {
        playerRef.addPotionEffect(potionEffect);
    }

    @Override
    public void removePotionEffect(PotionEffectType potionEffect) {
        playerRef.removePotionEffect(potionEffect);
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType potionEffect) {
        return playerRef.hasPotionEffect(potionEffect);
    }
}
