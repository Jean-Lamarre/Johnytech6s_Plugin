package io.github.johnytech6.dm;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class DMHandler {

	// ---------------------------SINGLETON-IMPLEMENTATION-------------------------------------------
	// static variable single_instance of type Singleton
	private static DMHandler single_instance = null;

	// private constructor restricted to this class itself
	private DMHandler() {
	}

	public static DMHandler getInstance() {
		if (single_instance == null)
			single_instance = new DMHandler();

		return single_instance;
	}
	// --------------------------------------------------------------------------------------------

	private Location dndRoomLocation;

	// list of Dm
	private ArrayList<Player> dms = new ArrayList<Player>();

	/*
	 * Toggle Dm mode
	 */
	public boolean ToggleDmMode(Player p) {
		return setDmMode(p, !(isPlayerDm(p.getName())));
	}

	public boolean setDmMode(Player p, boolean beDm) {

		if (beDm) {
			AddDm(p);
			p.setInvulnerable(true);
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage("***You are now the DM***");
			p.sendMessage("You are invicible.");
			
		} else if (!beDm) {
			RemoveDm(getDm(p.getName()));
			p.setInvulnerable(false);
			p.sendMessage("***You are not the DM anymore***");
			p.sendMessage("You are not invicible anymore.");
		} else {
			return false;
		}

		return true;
	}

	/*
	 * Toggle Dm invisibility
	 */
	public boolean DmInvisibility(Player p) {		
		return setDmInvisibility(p, !(p.hasPotionEffect(PotionEffectType.INVISIBILITY)));
	}
	public boolean setDmInvisibility(Player p, boolean beInvisible) {		
		
		if (beInvisible) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, true));
			p.sendMessage("You are now invisible");
		} else if (!beInvisible) {
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			p.sendMessage("You are not invisible anymore");
		} else {
			return false;
		}
		
		return true;
	}

	/*
	 * Toggle Dm night vision
	 */
	public boolean DmVision(Player p) {
		return setDmVision(p, !(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)));
	}
	public boolean setDmVision(Player p, boolean hasNightVision) {

		if (hasNightVision) {
			p.addPotionEffect(
					new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false, true));
			p.sendMessage("You have night vision");
		} else if (!hasNightVision) {
			p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			p.sendMessage("You lost night vision");
		} else {
			return false;
		}

		return true;
	}

	/*
	 * Add a Dm
	 */
	public void AddDm(Player p) {
		if(!(isPlayerDm(p.getName()))) {
			dms.add(p);
		}
	}

	/*
	 * Remove Dm
	 */
	public void RemoveDm(Player pp) {
		dms.remove(pp);
	}

	public boolean isPlayerDm(String name) {
		if (dms.size() > 0) {
			for (Player p : dms) {
				if (p.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Get Dm reference with his name
	 */
	public Player getDm(String name) {
		if (dms.size() > 0) {
			for (Player p : dms) {
				if (p.getName() == name) {
					return p;
				}
			}
		}
		return null;
	}

	/*
	 * Get reference of the list of all the dms
	 */
	public ArrayList<Player> getDms() {
		return dms;
	}

	public Location getDndRoomLocation(){
		return dndRoomLocation;
	}

	public boolean setDndRoomLocation(Location newLocation){
		if(newLocation == null){return false;}
		this.dndRoomLocation = newLocation;
		return true;
	}
}
