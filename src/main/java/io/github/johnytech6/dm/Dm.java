package io.github.johnytech6.dm;

import io.github.johnytech6.DndPlayer;
import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.Handler.PuppeterHandler;
import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.TeftHandler;
import io.github.johnytech6.hero.Hero;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Dm implements DndPlayer {

    private static final Plugin plugin = JohnytechPlugin.getPlugin();
    private static final HeroHandler hh = HeroHandler.getInstance();

    private Player playerRef;

    private Location checkpoint;
    private Location chairPosition;

    private boolean isInvisible = false;
    private boolean hasNightVision = false;
    private boolean hasPuppeterPower = false;
    private boolean hasTeftPower = false;

    private boolean isVerbose;

    public Dm(Player p, boolean verbose) {
        isVerbose = verbose;
        if(isVerbose){
            p.sendMessage("***You are now DM***");
        }
        this.playerRef = p;
        if (hh.isPlayerHero(p.getName())) {
            hh.removeHero(hh.getHero(p.getName()));
        }
        p.setGameMode(GameMode.CREATIVE);

        isVerbose = true;
    }

    public Dm(Hero oldHero, boolean verbose){
        isVerbose = verbose;
        if(isVerbose){
            oldHero.sendMessage("***You are now DM***");
        }
        this.playerRef = oldHero.getPlayer();

        Location oldCheckpoint = oldHero.getCheckpoint();
        if(oldCheckpoint != null){
            checkpoint = oldCheckpoint;
        }

        Location oldChairPosition = oldHero.getChairPosition();
        if(oldChairPosition !=null){
            chairPosition = oldChairPosition;
        }

        if(oldHero.isFrozen()){
            oldHero.setFrozenState(false);
        }

        hh.removeHero(oldHero);

        playerRef.setGameMode(GameMode.CREATIVE);

        isVerbose = true;
    }

    @Override
    public boolean isVerbose(){
        return isVerbose;
    }

    @Override
    public void setVerbose(boolean state){
        isVerbose = state;
    }

    @Override
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
        isVerbose = true;
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
        if(checkpoint != null) {
            this.checkpoint = checkpoint;
            plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".checkpoint", checkpoint);
            plugin.saveConfig();
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
        if(chairPosition != null){
            this.chairPosition = chairPosition;
            playerRef.sendMessage("Chair position set to : " + chairPosition.getX() +", "+chairPosition.getY()+", "+chairPosition.getZ());
            plugin.getConfig().set("Dnd_player.Dms." + playerRef.getName() + ".chair_position", chairPosition);
            plugin.saveConfig();
        }
    }

    @Override
    public boolean hasChair() {
        return chairPosition != null;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    /*
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
