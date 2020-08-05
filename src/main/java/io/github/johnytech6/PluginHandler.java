package io.github.johnytech6;

import java.util.ArrayList;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.johnytech6.dm.DMHandler;
import io.github.johnytech6.dm.puppeter.Puppeter;
import io.github.johnytech6.dm.puppeter.PuppeterHandler;
import io.github.johnytech6.theft.Teft;
import io.github.johnytech6.theft.TeftHandler;
import io.github.johnytech6.theft.Victim;

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

	private HeroHandler hh = HeroHandler.getInstance();
	private DMHandler dmh = DMHandler.getInstance();
	private PuppeterHandler pph = PuppeterHandler.getInstance();
	private TeftHandler th = TeftHandler.getInstance();
	
	public void johnytech6Stat(CommandSender p) {
		
		ArrayList<String> namesPuppeter = new ArrayList<String>();
		ArrayList<String> namesMorphedPuppeter = new ArrayList<String>();
		ArrayList<String> namesDm = new ArrayList<String>();
		ArrayList<String> namesHero = new ArrayList<String>();
		ArrayList<String> namesTeft = new ArrayList<String>();

		ArrayList<Puppeter> listPuppeter = pph.getPuppeters();
		ArrayList<Puppeter> listMorphedPuppeter = pph.getMorphPlayers();
		ArrayList<Player> listDm = dmh.getDms();
		ArrayList<OfflinePlayer> listOfflineDm = dmh.getAwaitedDms();
		ArrayList<Player> listHero = hh.getHeros();
		ArrayList<OfflinePlayer> listOfflineHero = hh.getAwaitedHeros();
		ArrayList<Teft> listTeft = th.getTeftPlayers();

		for (Puppeter cp : listPuppeter) {
			namesPuppeter.add(cp.getName());
		}
		for (Puppeter cp : listMorphedPuppeter) {
			namesMorphedPuppeter.add(cp.getName());
		}
		for (Player cp : listDm) {
			namesDm.add(cp.getName());
		}
		for (OfflinePlayer op : listOfflineDm){
			namesDm.add(op.getName());
		}
		for (Player cp : listHero) {
			namesHero.add(cp.getName());
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
