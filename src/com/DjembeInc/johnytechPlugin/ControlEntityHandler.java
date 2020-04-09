package com.DjembeInc.johnytechPlugin;

import java.util.ArrayList;

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

	public void AddController(ControllingPlayer cp) {
		controllingPlayers.add(cp);
		cp.setIndex(controllingPlayers.size() - 1);
	}

	public void RemoveController(ControllingPlayer cp) {
		controllingPlayers.remove(cp);
	}

	public boolean isPlayerPuppeter(String name) {
		if (controllingPlayers.size() > 0) {
			for (ControllingPlayer cp : controllingPlayers) {
				if (cp.getName() == name) {
					return true;
				}
			}
		}
		return false;
	}

	public ControllingPlayer GetController(String name) {
		if (controllingPlayers.size() > 0) {
			for (ControllingPlayer cp : controllingPlayers) {
				if (cp.getName() == name) {
					return controllingPlayers.get(cp.getIndex());
				}
			}
		}
		return null;
	}

	public ArrayList<ControllingPlayer> getPuppeters() {
		return controllingPlayers;
	}
}
