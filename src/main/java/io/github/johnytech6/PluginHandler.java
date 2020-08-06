package io.github.johnytech6;

import java.util.ArrayList;

import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.hero.Hero;
import io.github.johnytech6.hero.HeroHandler;
import io.github.johnytech6.theft.Teft;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.puppeter.Puppeter;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.theft.TeftHandler;

public class PluginHandler {
	
	// ---------------------------SINGLETON
		// IMPLEMENTATION-------------------------------------------
		// static variable single_instance of type Singleton
		private static PluginHandler single_instance = null;

		// private constructor restricted to this class itself
		private PluginHandler() {
		}

		public static PluginHandler getInstance() {
			if (single_instance == null)
				single_instance = new PluginHandler();

			return single_instance;
		}
		// --------------------------------------------------------------------------------------------

	private static HeroHandler hh = HeroHandler.getInstance();
	private static DMHandler dmh = DMHandler.getInstance();
	private static PuppeterHandler pph = PuppeterHandler.getInstance();
	private static TeftHandler th = TeftHandler.getInstance();

	private ArrayList<DndPlayer> dndPlayers = new ArrayList<DndPlayer>();

	public ArrayList<DndPlayer> getDndPlayers(){
		return dndPlayers;
	}

	public void addDndPlayer(DndPlayer dndPlayer){
		dndPlayers.add(dndPlayer);
	}

	public void removeDndPlayer(DndPlayer dndPlayer){
		dndPlayers.remove(dndPlayer);
	}

	public boolean isPlayerDndPlayer(Player p){
		if (dndPlayers.size() > 0) {
			for (DndPlayer dndP : dndPlayers) {
				if (dndP.getUniqueId().equals(p.getUniqueId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void johnytech6Stat(CommandSender p) {
		
		ArrayList<String> namesPuppeter = new ArrayList<String>();
		ArrayList<String> namesMorphedPuppeter = new ArrayList<String>();
		ArrayList<String> namesDm = new ArrayList<String>();
		ArrayList<String> namesHero = new ArrayList<String>();
		ArrayList<String> namesTeft = new ArrayList<String>();

		ArrayList<Puppeter> listPuppeter = pph.getPuppeters();
		ArrayList<Puppeter> listMorphedPuppeter = pph.getMorphPlayers();
		ArrayList<Dm> listDm = dmh.getDms();
		ArrayList<OfflinePlayer> listOfflineDm = dmh.getAwaitedDms();
		ArrayList<Hero> listHero = hh.getHeros();
		ArrayList<OfflinePlayer> listOfflineHero = hh.getAwaitedHeros();
		ArrayList<Teft> listTeft = th.getTeftPlayers();

		for (Puppeter cp : listPuppeter) {
			namesPuppeter.add(cp.getName());
		}
		for (Puppeter cp : listMorphedPuppeter) {
			namesMorphedPuppeter.add(cp.getName());
		}
		for (Dm dm : listDm) {
			namesDm.add(dm.getName());
		}
		for (OfflinePlayer op : listOfflineDm){
			namesDm.add(op.getName());
		}
		for (Hero h : listHero) {
			namesHero.add(h.getName());
		}
		for (OfflinePlayer op : listOfflineHero){
			namesHero.add(op.getName());
		}
		for (Teft cp : listTeft) {
			namesTeft.add(cp.getName());
		}

		p.sendMessage("Puppeters: " + namesPuppeter.toString());
		p.sendMessage("Morph puppeters: " + namesMorphedPuppeter.toString());
		p.sendMessage("Dms: " + namesDm.toString());
		p.sendMessage("Heros: " + namesHero.toString());
		p.sendMessage("Tefts: " + namesTeft.toString());

	}
}
