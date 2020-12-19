package io.github.johnytech6.dm.commands;

import io.github.johnytech6.Handler.HeroHandler;
import io.github.johnytech6.Handler.PluginHandler;
import io.github.johnytech6.dm.commands.subcommands.*;
import io.github.johnytech6.hero.Hero;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import io.github.johnytech6.Handler.DMHandler;
import org.bukkit.entity.Player;

import java.util.*;

public class DmCommand implements TabExecutor {

    DMHandler dmh;
    HeroHandler hh;

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public DmCommand(PluginHandler pluginHandler) {
        subcommands.add(new Mode_toggleDm(pluginHandler));
        subcommands.add(new StartSession(pluginHandler));
        subcommands.add(new EndSession(pluginHandler));
        subcommands.add(new Invisibility_toggle(pluginHandler));
        subcommands.add(new NightVision_toggle(pluginHandler));
        subcommands.add(new PuppeterMode_toggle(pluginHandler));
        subcommands.add(new TeftMode_toggle(pluginHandler));
        subcommands.add(new FreezeHero(pluginHandler));
        subcommands.add(new UnfreezeHero(pluginHandler));
        subcommands.add(new SetChair(pluginHandler));

        dmh = pluginHandler.getDmHandler();
        hh = pluginHandler.getHeroHandler();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            UUID id = ((Player) sender).getUniqueId();

            if (args.length > 0) {
                for (int i = 0; i < subcommands.size(); i++) {
                    if (args[0].equalsIgnoreCase(subcommands.get(i).getName())) {
                        subcommands.get(i).perform(Bukkit.getPlayer(id), args);
                    }
                }
            }
        }

        return true;
    }
    

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if ((sender instanceof Player) && dmh.isPlayerDm(((Player) sender).getUniqueId())) {
            if (args.length == 1) {
                ArrayList<String> subcommandsArguments = new ArrayList<>();
                for (int i = 0; i < subcommands.size(); i++) {
                    SubCommand subcommand = subcommands.get(i);
                    if (subcommand instanceof EndSession) {
                        if (dmh.isSessionStarted()) {
                            subcommandsArguments.add(subcommands.get(i).getName());
                        }
                    } else if (subcommand instanceof StartSession) {
                        if (!dmh.isSessionStarted()) {
                            subcommandsArguments.add(subcommands.get(i).getName());
                        }
                    } else if (subcommand instanceof UnfreezeHero) {
                        HashMap<UUID, Hero> heros = hh.getHeros();

                        boolean foundOneHeroFrozen = false;

                        Iterator<Map.Entry<UUID, Hero>> heroI = heros.entrySet().iterator();
                        while (!foundOneHeroFrozen && heroI.hasNext()) {
                            if (heroI.next().getValue().isFrozen()) {
                                foundOneHeroFrozen = true;
                                subcommandsArguments.add(subcommands.get(i).getName());
                            }
                        }
                    } else {
                        subcommandsArguments.add(subcommands.get(i).getName());
                    }
                }
                return subcommandsArguments;
            } else if (args.length >= 2) {
                for (int i = 0; i < subcommands.size(); i++) {
                    if (args[0].equalsIgnoreCase(subcommands.get(i).getName())) {
                        return subcommands.get(i).getSubcommandArguments((Player) sender, args);
                    }
                }
            }
        }else{
            ArrayList<String> tab = new ArrayList<String>();
            tab.add("mode_toggle");
            return tab;
        }

        return null;
    }
}
