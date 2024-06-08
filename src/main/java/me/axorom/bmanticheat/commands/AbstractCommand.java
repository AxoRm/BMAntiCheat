package me.axorom.bmanticheat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public abstract class AbstractCommand implements TabExecutor {
    public AbstractCommand(String command) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        execute(sender, command, args);
        return true;
    }

    public abstract void execute(CommandSender sender, Command command, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
