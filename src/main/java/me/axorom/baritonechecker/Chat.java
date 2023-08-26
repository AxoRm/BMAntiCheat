package me.axorom.baritonechecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {
    public static String format(String message, String player, int currentPunishes, int maxPunishes) {
        return ChatColor.translateAlternateColorCodes(
                '&', message.replaceAll("\\{player}", player)
                        .replaceAll("\\{count}", String.valueOf(currentPunishes))
                        .replaceAll("\\{max}", String.valueOf(maxPunishes))
        );
    }

    public static void sendAdminAndConsole(String message) {
        Bukkit.getLogger().info(message);
        Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(player -> player.sendMessage(message));
    }
}
