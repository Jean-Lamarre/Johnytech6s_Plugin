package io.github.johnytech6.dm.puppeter;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Puppeter {

    private final Player playerRef;
    private Puppet puppet;
    private boolean wasInvisibile;
    private boolean hadNightVision;

    /**
     * Contructor of a reference puppet and the player that controlling it.
     * @param p the player.
     */
    public Puppeter(Player p) {
        this.playerRef = p;
    }

    /**
     * Get the name of the stored player.
     * @return String (name of the player)
     */
    public String getName() {
        return playerRef.getName();
    }

    /**
     * Return the reference of the stroed player.
     * @return Player (stored player)
     */
    public Player getPlayer() {
        return this.playerRef;
    }

    /**
     * Get the controlled puppet
     * @return Puppet
     */
    public Puppet getPuppet() {
        return this.puppet;
    }

    /**
     * Set the controlled puppet
     * @param pu The new puppet.
     */
    public void setPuppet(Puppet pu) {
        this.puppet = pu;
    }

    /**
     * Store the invisibility state of the puppeter before morphing
     * @param state If true, he was invisble.
     */
    public void setInvisibilityState(boolean state) {
        wasInvisibile = state;
    }

    /**
     * Verify if the Puppeter was invisible.
     * @return boolean (true when was invisible)
     */
    public boolean wasInvisible() {
        return wasInvisibile;
    }

    /**
     * Store the night vision state of the puppeter before morphing
     * @param state  If true, he had night vision.
     */
    public void setNightVisionState(boolean state) {
        hadNightVision = state;
    }

    /**
     * Verify if the Puppeter had night vision.
     * @return boolean (true when had night vision)
     */
    public boolean hadNightVision() {
        return hadNightVision;
    }

    public UUID getUniqueId() {
        return playerRef.getUniqueId();
    }

}
