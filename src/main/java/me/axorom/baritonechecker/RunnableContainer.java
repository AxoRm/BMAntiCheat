package me.axorom.baritonechecker;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class RunnableContainer {
    public RunnableContainer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Listeners.players = new HashMap<>();
                Listeners.times = new HashMap<>();
                Listeners.punishments = new HashMap<>();
                Listeners.arithmetical = new HashMap<>();
                Listeners.use = new HashMap<>();
                Bukkit.getLogger().info(Chat.format(BaritoneChecker.config.getClearMessage(), "", 0, 0));
            }
        }.runTaskTimer(BaritoneChecker.instance, 0L, (long) (BaritoneChecker.config.getClearPunishments() * 20));
    }
}
