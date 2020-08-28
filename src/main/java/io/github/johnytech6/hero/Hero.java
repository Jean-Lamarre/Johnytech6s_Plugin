package io.github.johnytech6.hero;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.Handler.DMHandler;
import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.dm.Dm;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Hero implements DndPlayer {

    private static Plugin plugin = JohnytechPlugin.getPlugin();

    private Player playerRef;

    private Location checkpoint;
    private Location chairPosition;
    private boolean isVerbose;

    public Hero(Player p) {
        playerRef = p;
        playerRef.setGameMode(GameMode.ADVENTURE);
    }

    public Hero(Dm oldDm) {
        playerRef = oldDm.getPlayer();
        playerRef.setGameMode(GameMode.ADVENTURE);
        checkpoint = oldDm.getCheckpoint();
        chairPosition = oldDm.getChairPosition();

        Location oldCheckpoint = oldDm.getCheckpoint();
        if(oldCheckpoint != null){
            checkpoint = oldCheckpoint;
        }

        Location oldChairPosition = oldDm.getChairPosition();
        if(oldChairPosition !=null){
            chairPosition = oldChairPosition;
        }

        DMHandler.getInstance().removeDm(oldDm);
    }

    @Override
    public boolean isVerbose(){
        return isVerbose;
    }

    @Override
    public void setVerbose(boolean state){
        isVerbose = state;
    }
    
    // Right click with saddle
    public void rideHero(Entity e) {
        e.addPassenger(playerRef.getPlayer());
        if (e instanceof Player) {
            playerRef.sendMessage("You are the passenger of " + e.getName());
            e.sendMessage(playerRef.getName() + " is your passenger.");
        } else if (e.getName().contentEquals("Villager")) {
            playerRef.sendMessage("You are the passenger of a villager.");
        }
    }

    /**
     * Freeze position and jump of hero.
     */
    public void freezeHero() {
        if (playerRef.getWalkSpeed() != 0) {
            playerRef.setWalkSpeed(0);
            playerRef.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false, true));
            playerRef.sendMessage("You cannot move anymore.");
        }
    }

    /**
     * Unfreeze position and jump of hero.
     */
    public void unfreezeHero() {
        if (playerRef.getWalkSpeed() == 0) {
            playerRef.setWalkSpeed(0.2f);
            playerRef.removePotionEffect(PotionEffectType.JUMP);
        }
    }

    @Override
    public void loadConfig(Player p) {
        this.playerRef = p;
        setCheckpoint(plugin.getConfig().getLocation("Dnd_player.Heros." + playerRef.getName() + ".checkpoint"));
        setChairPosition(plugin.getConfig().getLocation("Dnd_player.Heros." + playerRef.getName() + ".chair_position"));
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        playerRef.setGameMode(gameMode);
    }

    @Override
    public Location getCheckpoint() {
        return checkpoint;
    }

    @Override
    public void setCheckpoint(Location checkpoint) {
        this.checkpoint = checkpoint;
        plugin.getConfig().set("Dnd_player.Heros." + playerRef.getName() + ".checkpoint", checkpoint);
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
        plugin.getConfig().set("Dnd_player.Heros." + playerRef.getName() + ".chair_position", chairPosition);
        plugin.saveConfig();
    }

    @Override
    public boolean hasChair() {
        if (chairPosition != null) {
            return true;
        }
        return false;
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

    public float getWalkSpeed() {
        return playerRef.getWalkSpeed();
    }

    public void setWalkSpeed(float f) {
        playerRef.setWalkSpeed(f);
    }
}
