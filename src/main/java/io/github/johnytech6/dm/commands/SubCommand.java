package io.github.johnytech6.dm.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    /**
     * @return String (the name of the subcommand)
     */
    public abstract String getName();

    /**
     * @return String (the description of the subcommand)
     */
    public abstract String getDescription();

    /**
     * @return String (the syntax of the subcommand)
     */
    public abstract String getSyntax();

    /**
     * Perfom the subcommand.
     * @param player The player that send the subcommand
     * @param args Arguments after the command
     */
    public abstract void perform(Player player, String[] args);

    /**
     * Gets the possible arguments of this subcommand.
     * @param player The player that typing the subcommand
     * @param args Arguments after the subcommand.
     * @return List<String> (list of the arguments)
     */
    public abstract List<String> getSubcommandArguments(Player player, String[] args);

    /**
     * Checking if a string is a Double.
     * @param str The string typed.
     * @return boolean (true if string is a Double)
     */
    protected boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

