package com.DjembeInc.johnytechPlugin;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.DjembeInc.johnytechPlugin.dm.DMHandler;
import com.DjembeInc.johnytechPlugin.dm.puppeter.Puppeter;
import com.DjembeInc.johnytechPlugin.dm.puppeter.PuppeterHandler;

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
	
	public void johnytech6Stat(Player p) {
		
		ArrayList<String> namesPuppeter = new ArrayList<String>();
		ArrayList<String> namesMorphedPuppeter = new ArrayList<String>();
		ArrayList<String> namesDm = new ArrayList<String>();
		ArrayList<String> namesHero = new ArrayList<String>();

		ArrayList<Puppeter> listPuppeter = pph.getPuppeters();
		ArrayList<Puppeter> listMorphedPuppeter = pph.getMorphPlayers();
		ArrayList<Player> listDm = dmh.getDms();
		ArrayList<Player> listHero = hh.getHeros();

		for (Puppeter cp : listPuppeter) {
			namesPuppeter.add(cp.getName());
		}
		for (Puppeter cp : listMorphedPuppeter) {
			namesMorphedPuppeter.add(cp.getName());
		}
		for (Player cp : listDm) {
			namesDm.add(cp.getName());
		}
		for (Player cp : listHero) {
			namesHero.add(cp.getName());
		}

		p.sendMessage("Puppeters: " + namesPuppeter.toString());
		p.sendMessage("Morph puppeters: " + namesMorphedPuppeter.toString());
		p.sendMessage("Dms: " + namesDm.toString());
		p.sendMessage("Heros: " + namesHero.toString());

	}
}
