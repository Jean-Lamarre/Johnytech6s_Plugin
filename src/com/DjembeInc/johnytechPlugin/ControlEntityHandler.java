package com.DjembeInc.johnytechPlugin;

import java.util.ArrayList;

import org.bukkit.Location;

public class ControlEntityHandler {

	// static variable single_instance of type Singleton
	private static ControlEntityHandler single_instance = null;

	// private constructor restricted to this class itself
	private ControlEntityHandler() {
	}

	public static ControlEntityHandler getInstance() {
		if (single_instance == null)
			single_instance = new ControlEntityHandler();

		return single_instance;
	}

	private ArrayList<ControllingPlayer> controllingPlayers = new ArrayList<ControllingPlayer>();
	private ArrayList<ControllingPlayer> puppeters = new ArrayList<ControllingPlayer>();

	private static Location trueEntityLocation;
	
	public void AddMorphPlayer(ControllingPlayer cp) {
		controllingPlayers.add(cp);
		cp.setIndex(controllingPlayers.size() - 1);
	}
	public void RemoveMorphPlayer(ControllingPlayer cp) {
		controllingPlayers.remove(cp);
	}
	public boolean isPlayerMorph(String name) {
		if (controllingPlayers.size() > 0) {
			for (ControllingPlayer cp : controllingPlayers) {
				if (cp.getName() == name) {
					return true;
				}
			}
		}
		return false;
	}
	public ControllingPlayer GetMorphPlayer(String name) {
		if (controllingPlayers.size() > 0) {
			for (ControllingPlayer cp : controllingPlayers) {
				if (cp.getName() == name) {
					return controllingPlayers.get(cp.getIndex());
				}
			}
		}
		return null;
	}

	public void AddPuppeter(ControllingPlayer cp) {
		puppeters.add(cp);
		cp.setIndex(puppeters.size() - 1);
	}
	public void RemovePuppeter(ControllingPlayer cp) {
		puppeters.remove(cp);
	}
	public boolean isPlayerPuppeter(String name) {
		if (puppeters.size() > 0) {
			for (ControllingPlayer cp : puppeters) {
				if (cp.getName() == name) {
					return true;
				}
			}
		}
		return false;
	}
	public ControllingPlayer getPuppeter(String name) {
		if (puppeters.size() > 0) {
			for (ControllingPlayer cp : puppeters) {
				if (cp.getName() == name) {
					return puppeters.get(cp.getIndex());
				}
			}
		}
		return null;
	}
	
	public ArrayList<ControllingPlayer> getMorphPlayers() {
		return controllingPlayers;
	}
	
	public ArrayList<ControllingPlayer> getPuppeters() {
		return puppeters;
	}
	
	public static Location getTrueEntityLocation() {
		return trueEntityLocation;
	}
	
	public static void setTrueEntityLocation(Location l) {
		trueEntityLocation = l;
	}
}
