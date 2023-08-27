package me.axorom.bmanticheat;

import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BMAntiCheat extends JavaPlugin {
    public static BMAntiCheat instance;
    public static Config config;
    public static ProtocolManager manager;
    @Override
    public void onEnable() {
        instance = this;
        manager = ProtocolLibrary.getProtocolManager();
        saveDefaultConfig();
        config = new Config();
        new RunnableContainer();
        Bukkit.getPluginManager().registerEvents(new BaritoneListener(), this);
        new IllegalClickListener();
        Bukkit.getLogger().info("Plugin BMAntiCheat enabled");
        Bukkit.getLogger().info("Starting checking...");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stopping checking...");
        Bukkit.getLogger().info("Plugin BMAntiCheat disabled");
    }
}
