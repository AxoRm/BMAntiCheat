package me.axorom.baritonechecker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BaritoneChecker extends JavaPlugin {
    public static BaritoneChecker instance;
    public static Config config;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        config = new Config();
        new RunnableContainer();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getLogger().info("Plugin enabled");
        Bukkit.getLogger().info("Starting checking...");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stopping checking...");
        Bukkit.getLogger().info("Plugin disabled");
    }
}
