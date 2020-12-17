package io.github.johnytech6;

import io.github.johnytech6.Handler.PuppeterHandler;
import io.github.johnytech6.Handler.TeftHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class DndPlayer {

    private static final Plugin plugin = JohnytechPlugin.getPlugin();

    private Player playerRef;

    private Location checkpoint;
    private Location chairPosition;
    private boolean frozenState;
    private boolean isVerbose;

    private boolean isInvisible = false;
    private boolean hasNightVision = false;
    private boolean hasPuppeterPower = false;
    private boolean hasTeftPower = false;

    private

    public void loadConfig(Player p) {
        this.playerRef = p;
        FileConfiguration config = plugin.getConfig();
        isVerbose = false;
        setCheckpoint(config.getLocation("Dnd_player.Dms." + playerRef.getName() + ".checkpoint"));
        setChairPosition(config.getLocation("Dnd_player.Dms." + playerRef.getName() + ".chair_position"));
        setInvisibility(config.getBoolean("Dnd_player.Dms." + playerRef.getName() + ".isInvisible"));
        setPuppeterPower(config.getBoolean("Dnd_player.Dms." + playerRef.getName() + ".hasPuppeterPower"));
        setTeftPower(config.getBoolean("Dnd_player.Dms." + playerRef.getName() + ".hasTeftPower"));
        setNightVision(config.getBoolean("Dnd_player.Dms." + playerRef.getName() + ".hasNightVision"));
        setFrozenState(plugin.getConfig().getBoolean("Dnd_player.Heros." + playerRef.getName() + ".frozen_state"));
        isVerbose = true;
    }

    /**
     * Freeze position and jump of hero.
     */
    private void freezeHero() {
        if (!frozenState) {
            playerRef.setWalkSpeed(0);
            playerRef.setFlySpeed(0);
            playerRef.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false, true));
            playerRef.sendMessage("You cannot move anymore.");
        }
    }

    /**
     * Unfreeze position and jump of hero.
     */
    private void unfreezeHero() {
        if (frozenState) {
            playerRef.setWalkSpeed(0.2f);
            playerRef.setFlySpeed(0.1f);
            playerRef.removePotionEffect(PotionEffectType.JUMP);
            playerRef.sendMessage("You can move.");
        }
    }

    public void setFrozenState(boolean state) {
        if (state) {
            freezeHero();
        } else {
            unfreezeHero();
        }
        frozenState = state;
        plugin.getConfig().set("Dnd_player.Heros." + playerRef.getName() + ".frozen_state", frozenState);
        plugin.saveConfig();
    }

    public boolean isFrozen() {
        return frozenState;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    /**
     * Toggle Dm invisibility
     */
    public void invisibilityToggle() {
        setInvisibility(!(playerRef.hasPotionEffect(PotionEffectType.INVISIBILITY)));
    }

    public void setInvisibility(boolean state) {
        isInvisible = state;
        plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".isInvisible", state);
        plugin.saveConfig();
        if (state) {
            playerRef.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, true));
            if (isVerbose) {
                playerRef.sendMessage("You are now invisible");
            }
        } else {
            removePotionEffect(PotionEffectType.INVISIBILITY);
            if (isVerbose) {
                playerRef.sendMessage("You are not invisible anymore");
            }
        }
    }

    public boolean hasNightVision() {
        return hasNightVision;
    }

    /**
     * Toggle Dm night vision
     */
    public void nightVisionToggle() {
        setNightVision(!(playerRef.hasPotionEffect(PotionEffectType.NIGHT_VISION)));
    }

    public void setNightVision(boolean state) {
        hasNightVision = state;
        plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".hasNightVision", state);
        plugin.saveConfig();
        if (state) {
            playerRef.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false, true));
            if (isVerbose) {
                playerRef.sendMessage("You have night vision");
            }
        } else {
            playerRef.removePotionEffect(PotionEffectType.NIGHT_VISION);
            if (isVerbose) {
                playerRef.sendMessage("You lost night vision");
            }
        }
    }


    public boolean hasPuppeterPower() {
        return hasPuppeterPower;
    }

    public void setPuppeterPower(boolean state) {
        PuppeterHandler.getInstance().setPuppeterMode(playerRef, state, isVerbose);
        hasPuppeterPower = state;
        plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".hasPuppeterPower", state);
        plugin.saveConfig();
    }

    public boolean hasTeftPower() {
        return hasTeftPower;
    }

    public void setTeftPower(boolean state) {
        TeftHandler.getInstance().setTeftMode(playerRef, state, isVerbose);
        hasTeftPower = state;
        plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".hasTeftPower", state);
        plugin.saveConfig();
    }

    public void setAllPower(boolean state) {
        setInvisibility(state);
        setNightVision(state);
        setPuppeterPower(state);
        setTeftPower(state);
    }

    public Location getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Location checkpoint) {
        if (checkpoint != null) {
            this.checkpoint = checkpoint;
            plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".checkpoint", checkpoint);
            plugin.saveConfig();
        }
    }

    public boolean hasCheckpoint() {
        return checkpoint != null;
    }

    public Location getChairPosition() {
        return chairPosition;
    }

    public void setChairPosition(Location chairPosition) {
        if (chairPosition != null) {
            this.chairPosition = chairPosition;
            playerRef.sendMessage("Chair position set to : " + chairPosition.getX() + ", " + chairPosition.getY() + ", " + chairPosition.getZ());
            plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".chair_position", chairPosition);
            plugin.saveConfig();
        }
    }

    public boolean hasChair() {
        return chairPosition != null;
    }

    public boolean isVerbose() {
        return isVerbose;
    }

    public void setVerbose(boolean state) {
        isVerbose = state;
    }

    public String getName() {
        return playerRef.getName();
    }

    public Player getPlayer() {
        return this.playerRef;
    }

    public UUID getUniqueId() {
        return playerRef.getUniqueId();
    }

    public void sendTitle(String title, String s, int i, int i1, int i2) {
        playerRef.sendTitle(title, s, i, i1, i2);
    }

    public void sendMessage(String s) {
        playerRef.sendMessage(s);
    }

    public void teleport(Location targetLocation) {
        playerRef.teleport(targetLocation);
    }

    public Location getLocation() {
        return playerRef.getLocation();
    }

    public void addPotionEffect(PotionEffect potionEffect) {
        playerRef.addPotionEffect(potionEffect);
    }

    public void removePotionEffect(PotionEffectType potionEffect) {
        playerRef.removePotionEffect(potionEffect);
    }

    public boolean hasPotionEffect(PotionEffectType potionEffect) {
        return playerRef.hasPotionEffect(potionEffect);
    }

    public void setGameMode(GameMode gameMode) {
        playerRef.setGameMode(gameMode);
    }
}
