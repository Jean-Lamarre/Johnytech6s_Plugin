package io.github.johnytech6.hero;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.dm.Dm;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.UUID;

public class Hero implements DndPlayer {

    private Player playerRef;

    private Location checkpoint;
    private Location chairPosition;
    private boolean frozenState;
    private boolean nightVisionState;
    private boolean isVerbose;
    private boolean isInvisible;
    private boolean hasNightVision;

    /**
     * Contructor of the dnd hero with the player reference.
     * @param p the player.
     */
    public Hero(Player p) {
        playerRef = p;
    }

    /**
     * Contructor of the dnd player with an old dm reference.
     * @param oldDm the old dungeon master
     */
    public Hero(Dm oldDm) {
        playerRef = oldDm.getPlayer();
        checkpoint = oldDm.getCheckpoint();
        chairPosition = oldDm.getChairPosition();

        Location oldCheckpoint = oldDm.getCheckpoint();
        if (oldCheckpoint != null) {
            setCheckpoint(oldCheckpoint);
        }

        Location oldChairPosition = oldDm.getChairPosition();
        if (oldChairPosition != null) {
            setChairPosition(oldChairPosition);
        }
    }

    /**
     * Tell when state changed.
     * @return boolean (the state)
     */
    @Override
    public boolean isVerbose() {
        return isVerbose;
    }

    @Override
    public void setVerbose(boolean state) {
        isVerbose = state;
    }

    @Override
    public void updatePlayerReference(Player player) {
        this.playerRef = player;
    }

    // Right click with saddle
    //TODO for DM to
    public void rideHero(Entity e) {
        e.addPassenger(Objects.requireNonNull(playerRef.getPlayer()));
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
    private void freezeHero() {
        if (!frozenState) {
            playerRef.setWalkSpeed(0);
            playerRef.setFlySpeed(0);
            playerRef.setVelocity(new Vector(0,0,0));
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
    }

    public boolean isFrozen() {
        return frozenState;
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
    }

    @Override
    public boolean hasCheckpoint() {
        return checkpoint != null;
    }

    @Override
    public Location getChairPosition() {
        return chairPosition;
    }

    @Override
    public void setChairPosition(Location chairPosition) {
        if (chairPosition != null) {
            this.chairPosition = chairPosition;
            playerRef.sendMessage("Chair position set to : " + chairPosition.getX() + ", " + chairPosition.getY() + ", " + chairPosition.getZ());
        }
    }

    @Override
    public String getRole() throws Exception {
        return "Heros";
    }

    @Override
    public boolean hasChair() {
        return chairPosition != null;
    }

    @Override
    public void setNightVision(boolean state) {
        hasNightVision = state;
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

    @Override
    public boolean hasNightVision() {
        return nightVisionState;
    }

    @Override
    public void setInvisibility(boolean state) {
        isInvisible = state;

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

    @Override
    public boolean isInvisible() {
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

    public String toString(){
        return ("Hero: " + playerRef.getName());
    }
}
