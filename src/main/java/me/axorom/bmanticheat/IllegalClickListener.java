package me.axorom.bmanticheat;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.axorom.bmanticheat.utils.BlockFaceAnalyser;
import me.axorom.bmanticheat.utils.Chat;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class IllegalClickListener extends BlockDigListener {
    public static HashMap<String, Integer> playersPunishments = new HashMap<>();
    private final Config config = BMAntiCheat.config;

    public IllegalClickListener() {
        super(EnumWrappers.PlayerDigType.START_DESTROY_BLOCK);
    }

    @Override
    public void analyzePacket(Player player, Block digedBlock) {
        if (config.getCDisabledWorlds().contains(player.getWorld().getName())) return;
        if (player.isOp() || player.hasPermission("illegalclick.bypass")) return;
        if (isInvalidMaterial(digedBlock)) return;
        Block targetBlock = player.getTargetBlockExact(5);
        if (config.isDebug()) {
            Chat.sendAdminAndConsole("Игрок: " + player.getName());
            Chat.sendAdminAndConsole(" Кликал по блоку/смотрел на блок: " + digedBlock.getLocation() + " / " + (targetBlock == null ? "null" : targetBlock.getLocation()));
        }
        if (!digedBlock.equals(targetBlock)) {
            handleIllegalClick(player, digedBlock);
        }
    }

    private void handleIllegalClick(Player player, Block clickedBlock) {
        double distance = clickedBlock.getLocation().clone().add(0.5, 0.5, 0.5).distanceSquared(player.getEyeLocation()) - 1; //-1 because player can click on face or corner
        if (config.isDebug())
            Chat.sendAdminAndConsole("distance to block: " + distance);
        if (distance > Math.pow(config.getCRadius(), 2) || !BlockFaceAnalyser.isAirOrPartialNear(clickedBlock)) {
            int punishments = playersPunishments.getOrDefault(player.getName(), 0) + 1;
            if (config.isDebug()) {
                Chat.sendAdminAndConsole("player: " + player.getName() + " p: " + punishments);
            }
            if (punishments % config.getCAdminNotify() == 0 && punishments != 0) {
                Chat.sendAdminAndConsole(Chat.format(config.getCPunishMessage(), player.getName(), punishments, config.getCKick()));
            }
            if (punishments >= config.getCKick()) {
                punishments = 0;
                Chat.kickPlayer(player, config.getCKickMessage(), punishments, config.getCKick());
            }
            playersPunishments.put(player.getName(), punishments);
        }
    }

    private boolean isInvalidMaterial(Block clickedBlock) {
        return config.getCBlocks().stream()
                .map(Material::matchMaterial)
                .noneMatch(mat -> clickedBlock.getType().equals(mat));
    }

}
