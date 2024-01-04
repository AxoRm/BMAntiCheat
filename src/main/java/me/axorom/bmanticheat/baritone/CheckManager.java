package me.axorom.bmanticheat.baritone;

import me.axorom.bmanticheat.BMAntiCheat;
import me.axorom.bmanticheat.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class CheckManager implements Listener {
    private final Config config = BMAntiCheat.config;
    HashMap<String, PlayerData> playerDataMap = new HashMap<>();

    @EventHandler
    public void moveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (optimization(player)) return;
        PlayerData playerData = playerDataMap.getOrDefault(player.getName(), new PlayerData());
        Location to = event.getTo();
        Location from = event.getFrom();
        boolean isSprint = player.isSprinting();
        double xTo = to.getX();
        double yTo = to.getY();
        double zTo = to.getZ();
        double xFrom = from.getX();
        double yFrom = from.getY();
        double zFrom = from.getZ();
        float yawTo = to.getYaw();
        float yawFrom = from.getYaw();
        float pitchTo = to.getPitch();
        float pitchFrom = from.getPitch();
        if (BaritoneYawPitch.check(yawFrom, pitchFrom, yawTo, pitchTo)) {
            playerData.setYawPitch(playerData.getYawPitch() + 1);
            if (playerData.getYawPitch() >= 10) //CONFIG ADD DONT CHANGED PITCH COUNT
                player.sendMessage("ДОЛОЕБ");
            return;
        } else {
            playerData.setYawPitch(0);
        }
        if (LineCheck.isXLine(isSprint, xFrom, xTo, zFrom, zTo)) {
            playerData.setYawPitch(playerData.getYawPitch() + 1);
        }
        playerDataMap.put(player.getName(), playerData);
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

