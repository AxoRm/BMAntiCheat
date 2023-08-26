package me.axorom.baritonechecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {
    public static String format(String message, String player, int currentPunishes, int maxPunishes) {
        message = message.replaceAll("\\{player}", player);
        message = message.replaceAll("\\{count}", String.valueOf(currentPunishes));
        message = message.replaceAll("\\{max}", String.valueOf(maxPunishes));
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void sendAdminAndConsole(String message) {
        Bukkit.getLogger().info(message);
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (onlinePlayers.isOp()) {
                onlinePlayers.sendMessage(message);
            }
        }
    }

}
