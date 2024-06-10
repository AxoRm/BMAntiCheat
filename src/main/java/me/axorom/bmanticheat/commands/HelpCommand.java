package me.axorom.bmanticheat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("You cannot execute this command as console");
        }
    }
}
