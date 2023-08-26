package me.axorom.baritonechecker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BaritoneChecker extends JavaPlugin {
    public static BaritoneChecker instance;
    public static Config config;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        config = new Config();
        new Runnables();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getLogger().info("Plugin enabled and start checking...");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Plugin disabled and stopped checking...");
    }
}
