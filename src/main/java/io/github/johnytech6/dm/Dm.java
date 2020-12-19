package io.github.johnytech6.dm;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.hero.Hero;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Dm implements DndPlayer {

    //private static final Plugin plugin = JohnytechPlugin.getPlugin();
    //private HeroHandler hh = HeroHandler.getInstance();

    private Player playerRef;

    private Location checkpoint;
    private Location chairPosition;

    private boolean isInvisible = false;
    private boolean hasNightVision = false;
    private boolean hasPuppeterPower = false;
    private boolean hasTeftPower = false;

    private boolean isVerbose;

    public Dm(Player playerRef, boolean verbose) {
        this.playerRef = playerRef;

        isVerbose = verbose;

        if (isVerbose) {
            playerRef.sendMessage("***You are now DM***");
        }

        playerRef.setGameMode(GameMode.CREATIVE);

        isVerbose = true;
    }

    public Dm(Hero oldHero, boolean verbose) {
        isVerbose = verbose;
        if (isVerbose) {
            oldHero.sendMessage("***You are now DM***");
        }
        this.playerRef = oldHero.getPlayer();

        Location oldCheckpoint = oldHero.getCheckpoint();
        if (oldCheckpoint != null) {
            setCheckpoint(oldCheckpoint);
        }

        Location oldChairPosition = oldHero.getChairPosition();
        if (oldChairPosition != null) {
            setChairPosition(oldChairPosition);
        }

        if (oldHero.isFrozen()) {
            oldHero.setFrozenState(false);
        }

        playerRef.setGameMode(GameMode.CREATIVE);

        isVerbose = true;
    }

    @Override
    public boolean isVerbose() {
        return isVerbose;
    }

    @Override
    public void setVerbose(boolean state) {
        isVerbose = state;
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
        if (checkpoint != null) {
            this.checkpoint = checkpoint;
        }
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
    public boolean hasChair() {
        return chairPosition != null;
    }

    @Override
    public void updatePlayerReference(Player player) {
        this.playerRef = player;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisibility(boolean state) {
        isInvisible = state;

        if (state) {
            playerRef.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, true));
            if (isVerbose) {
                playerRef.sendMessage("You are now invisible");
            }
        } else {
            playerRef.removePotionEffect(PotionEffectType.INVISIBILITY);
            if (isVerbose) {
                playerRef.sendMessage("You are not invisible anymore");
            }
        }

        //update state just to be sure
        isInvisible = playerRef.hasPotionEffect(PotionEffectType.INVISIBILITY);
    }

    public boolean hasNightVision() {
        return hasNightVision;
    }

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

        //update state just to be sure
        hasNightVision = playerRef.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }

    public boolean havePuppeterPower() {
        return hasPuppeterPower;
    }

    public void setPuppeterPower(boolean state) {
        hasPuppeterPower = state;
    }

    public boolean haveTeftPower() {
        return hasTeftPower;
    }

    public void setTeftPower(boolean state) {
        hasTeftPower = state;
    }

    public void setAllPower(boolean state) {
        setInvisibility(state);
        setNightVision(state);
        setPuppeterPower(state);
        setTeftPower(state);
    }

    @Override
    public String getRole() throws Exception {
        return "Dms";
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
        return ("DM: " +playerRef.getName());
    }
}
