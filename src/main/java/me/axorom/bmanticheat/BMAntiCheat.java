package me.axorom.bmanticheat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.yaml.YamlFormat;
import me.axorom.bmanticheat.illegalclick.IllegalClickListener;
import me.axorom.bmanticheat.illegaldig.IllegalDigListener;
import me.axorom.bmanticheat.listeners.MovementListener;
import me.axorom.bmanticheat.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class BMAntiCheat extends JavaPlugin {
    public static BMAntiCheat instance;
    public static Config config;
    public static ProtocolManager manager;
    public static HashMap<Player, PlayerData> playerDataMap;

    @Override
    public void onEnable() {
        instance = this;
        manager = ProtocolLibrary.getProtocolManager();
        playerDataMap = new HashMap<>();
        saveDefaultConfig();
        new RunnableContainer();
        new IllegalDigListener();
        new IllegalClickListener();
        Bukkit.getPluginManager().registerEvents(new MovementListener(), this);
        Bukkit.getLogger().info("Plugin BMAntiCheat enabled");
        Bukkit.getLogger().info("Starting checking...");
    }

    @Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();
        FileConfig fileConfig = FileConfig.of(new File(this.getDataFolder(), "config.yml"), YamlFormat.defaultInstance());
        fileConfig.load();
        config = new Config();
        ObjectConverter converter = new ObjectConverter();
        converter.toObject(fileConfig, config);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stopping checking...");
        Bukkit.getLogger().info("Plugin BMAntiCheat disabled");
    }

    public void clearData() {
        playerDataMap = new HashMap<>();
    }
}
