package me.axorom.bmanticheat;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.axorom.bmanticheat.utils.BlockFaceAnalyser;
import me.axorom.bmanticheat.utils.Chat;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class IllegalDigListener extends BlockDigListener implements Listener {
    public static HashMap<String, HashSet<Block>> playerValuableBlocks = new HashMap<>();
    public static HashMap<String, Integer> playersPunishments = new HashMap<>();
    public static HashMap<String, Integer> playerAbortCounts = new HashMap<>();
    private final Config config = BMAntiCheat.config;

    public IllegalDigListener() {
        super(EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK);
    }

    @Override
    public void analyzePacket(Player player, Block digedBlock) {
        Location playerLocation = player.getLocation();
        if (playerLocation.getY() > config.getDMaxHeight())
            return;
        if (config.getDValuableBlocks().contains(digedBlock.getType().toString().toLowerCase())) return;
        String playerName = player.getName();
        playerAbortCounts.putIfAbsent(playerName, 0);
        playerAbortCounts.put(playerName, playerAbortCounts.get(player.getName()) + 1);
        List<Block> list = BlockFaceAnalyser.getValuableBlocks(digedBlock, playerName, config.getDValuableBlocks());
        playerValuableBlocks.putIfAbsent(playerName, new HashSet<>());
        playerValuableBlocks.get(playerName).addAll(list);
    }

    @EventHandler
    public void onDigEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        Block block = event.getBlock();
        if (!playerAbortCounts.containsKey(name) || !playerValuableBlocks.containsKey(name))
            return;
        HashSet<Block> set = playerValuableBlocks.get(name);
        int abortCount = playerAbortCounts.get(name);
        if (set.contains(block) && abortCount >= config.getDClickCount()) {
            int punishments = playersPunishments.getOrDefault(player.getName(), 0) + 1;
            if (config.isDebug()) {
                Chat.sendAdminAndConsole("player: " + player.getName() + " p: " + punishments);
            }
            if (punishments % config.getDAdminNotify() == 0 && punishments != 0) {
                Chat.sendAdminAndConsole(Chat.format(config.getDPunishMessage(), player.getName(), punishments, config.getDKick()));
            }
            if (punishments >= config.getDKick()) {
                punishments = 0;
                Chat.kickPlayer(player, config.getDKickMessage(), punishments, config.getDKick());
            }
            playersPunishments.put(player.getName(), punishments);
        }
    }
}