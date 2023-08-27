package me.axorom.bmanticheat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class RunnableContainer {
    public RunnableContainer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                BaritoneListener.players = new HashMap<>();
                BaritoneListener.times = new HashMap<>();
                BaritoneListener.punishments = new HashMap<>();
                BaritoneListener.arithmetical = new HashMap<>();
                BaritoneListener.use = new HashMap<>();
                Bukkit.getLogger().info(Chat.format(BMAntiCheat.config.getClearMessage(), "", 0, 0));
            }
        }.runTaskTimer(BMAntiCheat.instance, 0L, (long) (BMAntiCheat.config.getClearPunishments() * 20));
    }
}
