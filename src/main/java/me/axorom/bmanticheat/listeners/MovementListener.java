package me.axorom.bmanticheat.listeners;

import me.axorom.bmanticheat.BMAntiCheat;
import me.axorom.bmanticheat.Config;
import me.axorom.bmanticheat.baritone.LineCheck;
import me.axorom.bmanticheat.baritone.YawPitchCheck;
import me.axorom.bmanticheat.utils.Chat;
import me.axorom.bmanticheat.utils.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {
    Config config = BMAntiCheat.config;

    @EventHandler(priority = EventPriority.LOWEST)
    public void moveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (optimization(player)) return;
        PlayerData playerData = BMAntiCheat.playerDataMap.computeIfAbsent(player, PlayerData::new);
        Location to = event.getTo();
        if (to == null) return;
        Location from = event.getFrom();
        if (!(player.getVehicle() instanceof Boat) && YawPitchCheck.check(from, to)) {
            playerData.incrementBaritoneYawPitch();
            if (playerData.getBaritoneYawPitch() >= 15) { //CONFIG ADD DONT CHANGED PITCH COUNT
                playerData.setBaritoneYawPitch(0);
                Chat.sendAdminAndConsole("Player: " + player.getName() + " is suspicious in BARITONE[Y]");
                playerData.setBaritoneYawPitchCount(playerData.getBaritoneYawPitchCount() + 1);
                if (playerData.getBaritoneYawPitchCount() >= 2) {
                    playerData.setBaritoneYawPitchCount(0);
                }
            }
            BMAntiCheat.playerDataMap.put(player, playerData);
            return;
        }
        playerData.setBaritoneYawPitch(0);
        if (LineCheck.checkLine(from, to, player) && Float.compare(from.getYaw(), to.getYaw()) != 0) {
            playerData.incrementBaritoneLine();
            if (playerData.getBaritoneLine() >= 30) {
                playerData.setBaritoneLine(0);
                playerData.setBaritoneLineCount(playerData.getBaritoneLineCount() + 1);
                Chat.sendAdminAndConsole("Player: " + player.getName() + " is suspicious in BARITONE[L]");
                if (playerData.getBaritoneLineCount() >= 5) {
                    playerData.setBaritoneLineCount(0);
                }
            }
            BMAntiCheat.playerDataMap.put(player, playerData);
            return;
        }
        BMAntiCheat.playerDataMap.put(player, playerData);
    }

    private boolean optimization(Player player) {
        if (config.getBDisabledWorlds().contains(player.getWorld().getName())) return true;
        if (player.hasPermission("baritone.bypass")) return true;
        if (player.isSwimming() && !config.isBCheckWhenSwimming()) return true;
        if (player.isInWater() && !config.isBCheckInWater()) return true;
        if (player.isFlying() && !config.isBCheckInFlight()) return true;
        return player.isSneaking() && !config.isBCheckInSneak();
    }
}
