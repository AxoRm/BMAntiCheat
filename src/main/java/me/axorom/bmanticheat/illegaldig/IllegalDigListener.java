package me.axorom.bmanticheat.illegaldig;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.axorom.bmanticheat.BMAntiCheat;
import me.axorom.bmanticheat.Config;
import me.axorom.bmanticheat.listeners.BlockDigListener;
import me.axorom.bmanticheat.utils.BlockFaceAnalyser;
import me.axorom.bmanticheat.utils.Chat;
import me.axorom.bmanticheat.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.List;

public class IllegalDigListener extends BlockDigListener implements Listener {
    private final Config config = BMAntiCheat.config;

    public IllegalDigListener() {
        super(EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK);
        Bukkit.getPluginManager().registerEvents(this, BMAntiCheat.instance);
        BMAntiCheat.manager.addPacketListener(this);
    }

    @Override
    public void analyzePacket(Player player, Block digedBlock, PacketEvent event) {
        Location playerLocation = player.getLocation();
        if (playerLocation.getY() > config.getDMaxHeight())
            return;
        if (config.getDValuableBlocks().contains(digedBlock.getType().toString().toLowerCase())) return;
        PlayerData playerData = BMAntiCheat.playerDataMap.getOrDefault(player, new PlayerData(player));
        playerData.incrementIDigAbortCount();
        List<Block> list = BlockFaceAnalyser.getValuableBlocks(digedBlock, config.getDValuableBlocks());
        playerData.getIDigValuableBlocks().addAll(list);
        BMAntiCheat.playerDataMap.put(player, playerData);
    }

    @EventHandler
    public void onDigEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = BMAntiCheat.playerDataMap.getOrDefault(player, new PlayerData(player));
        Block block = event.getBlock();
        if (!BMAntiCheat.playerDataMap.containsKey(player))
            return;
        HashSet<Block> set = playerData.getIDigValuableBlocks();
        int abortCount = playerData.getIDigAbortCount();
        if (set.contains(block) && abortCount >= config.getDClickCount()) {
            int punishments = playerData.getIDigPunishments() + 1;
            if (config.isDebug()) {
                Chat.sendAdminAndConsole("player: " + player.getName() + " p: " + punishments);
            }
            if (punishments % config.getDAdminNotify() == 0 && punishments != 0) {
                Chat.sendAdminAndConsole(Chat.format(config.getDPunishMessage(), player.getName(), punishments, config.getDKick()));
            }
//            if (punishments >= config.getDKick()) {
//                punishments = 0;
//                Chat.kickPlayer(player, config.getDKickMessage(), punishments, config.getDKick());
//            }
            playerData.setIDigPunishments(punishments);
            BMAntiCheat.playerDataMap.put(player, playerData);
        }
    }
}