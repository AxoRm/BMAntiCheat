package me.axorom.bmanticheat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.yaml.YamlFormat;
import me.axorom.bmanticheat.baritone.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BMAntiCheat extends JavaPlugin {
    public static BMAntiCheat instance;
    public static Config config;
    public static ProtocolManager manager;

    @Override
    public void onEnable() {
        instance = this;
        manager = ProtocolLibrary.getProtocolManager();
        saveDefaultConfig();
        FileConfig fileConfig = FileConfig.of(new File(this.getDataFolder(), "config.yml"), YamlFormat.defaultInstance());
        System.out.println(this.getDataFolder().getAbsolutePath());
        config = new Config();
        ObjectConverter converter = new ObjectConverter();
        fileConfig.load();
        converter.toObject(fileConfig, config);
        new RunnableContainer();
        IllegalDigListener digListener = new IllegalDigListener();
        Bukkit.getPluginManager().registerEvents(new CheckManager(), this);
        Bukkit.getPluginManager().registerEvents(digListener, this);
        manager.addPacketListener(digListener);
        manager.addPacketListener(new IllegalClickListener());
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
