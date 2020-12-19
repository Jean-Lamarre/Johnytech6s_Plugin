package io.github.johnytech6.util;

import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.Dm;
import io.github.johnytech6.dm.puppeter.Puppeter;
import io.github.johnytech6.hero.Hero;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PluginStat {

    PluginHandler pluginHandler;

    public PluginStat(PluginHandler pluginHandler){
        this.pluginHandler = pluginHandler;
    }

    public void johnytech6Stat(CommandSender p) {

        ArrayList<String> offlineDndPlayerName = new ArrayList<String>();
        ArrayList<String> dndPlayerName = new ArrayList<String>();
        ArrayList<String> namesPuppeter = new ArrayList<String>();
        ArrayList<String> namesMorphedPuppeter = new ArrayList<String>();
        ArrayList<String> namesDm = new ArrayList<String>();
        ArrayList<String> namesHero = new ArrayList<String>();

        HashMap<UUID, Puppeter> listPuppeter = pluginHandler.getPuppeterHandler().getPuppeters();
        HashMap<UUID, Puppeter> listMorphedPuppeter = pluginHandler.getPuppeterHandler().getMorphPlayers();
        HashMap<UUID, Dm> listDm = pluginHandler.getDmHandler().getDms();
        HashMap<UUID, Hero> listHero = pluginHandler.getHeroHandler().getHeros();

        pluginHandler.getDndPlayers().forEach((id, dndPlayer) ->
                dndPlayerName.add(dndPlayer.getName())
        );

        pluginHandler.getOfflineDndPlayers().forEach((id, offlineDndPlayer) ->
                offlineDndPlayerName.add(offlineDndPlayer.getName())
        );

        listPuppeter.forEach((id, puppeter) ->
                namesPuppeter.add(puppeter.getName())
        );

        listMorphedPuppeter.forEach((id, mPuppeter) ->
                namesMorphedPuppeter.add(mPuppeter.getName())
        );

        listDm.forEach((id, dm) ->
                namesDm.add(dm.getName())
        );

        listHero.forEach((id, h) ->
                namesHero.add(h.getName())
        );

        p.sendMessage("DnD players: " + dndPlayerName.toString());
        p.sendMessage("Offline DnD players: " + offlineDndPlayerName.toString());
        p.sendMessage("Puppeters: " + namesPuppeter.toString());
        p.sendMessage("Morph puppeters: " + namesMorphedPuppeter.toString());
        p.sendMessage("Dms: " + namesDm.toString());
        p.sendMessage("Heros: " + namesHero.toString());
    }
}
