package io.github.johnytech6.dm.commands;

import io.github.johnytech6.util.PluginStat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.johnytech6.Handler.PluginHandler;

public class StatJohnytech implements CommandExecutor {

    private PluginStat pluginStat;

    public StatJohnytech(PluginStat pluginStat){
        this.pluginStat = pluginStat;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        pluginStat.johnytech6Stat(sender);

        return true;

    }

}
