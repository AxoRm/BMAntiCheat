package me.axorom.bmanticheat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;

public class BaritoneListener implements Listener {
    public static HashMap<String, Pair<Integer, Double>> players = new HashMap<>();
    public static HashMap<String, Integer> punishments = new HashMap<>();
    public static HashMap<String, Integer> times = new HashMap<>();
    public static HashMap<String, Integer> arithmetical = new HashMap<>();
    public static HashMap<String, Boolean> use = new HashMap<>();
    private final Config config = BMAntiCheat.config;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        /*
        OPTIMIZATIONS
         */
        Player player = event.getPlayer();
        if (player.hasPermission("baritone.bypass")) return;
        if (player.isSwimming() && !config.isCheckWhenSwimming()) return;
        if (player.isInWater() && !config.isCheckInWater()) return;
        if (!player.isSprinting() && config.isCheckOnlyWhenSprinting() && !player.isInWater()) return;
        if (player.isFlying() && !config.isCheckInFlight()) return;
        if (player.isSneaking() && !config.isCheckInSneak() && !player.isInWater() && !player.isFlying()) return;
        if (config.isCheckCameraYawForOptimization()) {
            float yaw = player.getLocation().getYaw();
            double angle = getAngleDistance(yaw);
            if (angle > config.getCameraYawRangeCheck()) return;
        }
        String playerName = player.getName();

        /*
        RANGE CHECKERS AND SAVES PER PLAYER
         */
        Location to = event.getTo();
        Location from = event.getFrom();
        if (to == null) return;
        double angle;
        if (config.isUseCameraYawInsteadVector()) {
            angle = player.getLocation().getYaw();
        } else {
            Vector velocity = new Vector(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());
            double yaw = getVectorAngle(velocity.getX(), velocity.getZ());
            angle = getAngleDistance(yaw);
        }
        if (angle > config.getYawRange()) {
            if (use.getOrDefault(playerName, false)) {
                if (players.getOrDefault(playerName, new Pair<>(0, 0d)).getSecond() > config.getArithmeticalLowerValue() && players.getOrDefault(playerName, new Pair<>(0, 0d)).getSecond() < config.getArithmeticalHigherValue() && players.getOrDefault(playerName, new Pair<>(0, 0d)).getFirst() >= config.getArithmeticalPunish()) {
                    arithmetical.put(playerName, arithmetical.getOrDefault(playerName, 0) + 1);
                    Chat.sendAdminAndConsole(Chat.format(config.getArithmeticalMeanMessage(), playerName, arithmetical.get(playerName), config.getArithmeticalMean()));
                    if (arithmetical.get(playerName) >= config.getArithmeticalMean()) {
                        arithmetical.put(playerName, 0);
                        player.kickPlayer(Chat.format(config.getKickMessage(), playerName, 0, 0));
                        times.put(playerName, 0);
                        punishments.put(playerName, 0);
                        Chat.sendAdminAndConsole("Игрок: " + playerName + "был кикнут за использование баритона[ARITH]");
                    }
                }
                players.put(playerName, new Pair<>(0, 0d));
                times.put(playerName, 0);
                use.put(playerName, false);
            }
            return;
        }

        /*
        ADDITIONAL MOVEMENT CHECK
         */
        if (config.isAdditionalMovementCheck()) {
            double fx = Math.abs((from.getX() - (int) from.getX()));
            double fz = Math.abs((from.getZ() - (int) from.getZ()));
            double tx = Math.abs((to.getX() - (int) to.getX()));
            double tz = Math.abs((to.getZ() - (int) to.getZ()));
            double range = config.getAdditionalMovementRange();
            double multiplier = config.getAdditionalMovementDiagonalMulti();
            if (!((Math.abs(fx - fz) <= range * multiplier && Math.abs(tx - tz) <= range * multiplier) ||
                    (Math.abs(fx + fz) <= 1 + range * multiplier && Math.abs(fx + fz) >= 1 - range * multiplier && Math.abs(tx + tz) <= 1 + range * multiplier && Math.abs(tx + tz) >= 1 - range * multiplier) ||
                    (Math.abs(fx - 0.5) <= range && Math.abs(tx - 0.5) <= range) ||
                    (Math.abs(fz - 0.5) <= range && Math.abs(tz - 0.5) <= range))) {
                return;
            }
        }
        use.put(playerName, true);
        addTime(playerName);
        kickPlayer(playerName);

        /*
        ADDITIONAL CHECK ARITHMETICAL MEAN
         */
        Pair<Integer, Double> playerEntry = players.getOrDefault(playerName, new Pair<>(0, 0d));
        if (playerEntry.getSecond() != angle) {
            playerEntry.setSecond((playerEntry.getFirst() * playerEntry.getSecond() + angle) / (playerEntry.getFirst() + 1));
            playerEntry.setFirst(playerEntry.getFirst() + 1);
            players.put(playerName, playerEntry);
            if (playerEntry.getFirst() % 10 == 0 && playerEntry.getFirst() >= 10) {
                Bukkit.getLogger().info(Chat.format("&cИгрок &a{player} &cподозревается в Baritone", playerName, 0, 0));
            }
        }

        /*
        DEBUGGER
         */
        if (config.isDebug()) {
            Bukkit.getLogger().info("СРЕДНЕЕ АРИФМЕТИЧСКОЕ: " + playerEntry.getSecond()
                    + " Текущий угол отклонения: " + angle + " Игрок: " + playerName);
        }
    }

    public double getVectorAngle(double x, double z) {
        double rad = Math.atan2(x, z); // In radians
        return rad * (180 / Math.PI);
    }

    private double getAngleDistance(double angle) {
        double maxAngle = Math.ceil(angle / 45) * 45 - angle;
        double minAngle = angle - Math.floor(angle / 45) * 45;
        return Math.min(maxAngle, minAngle);
    }

    private void addTime(String playerName) {
        int time = times.getOrDefault(playerName, 0) + 1;
        times.put(playerName, time);
        int punishes = punishments.getOrDefault(playerName, 0);
        if (punishes >= config.getCountPunishmentKick()) {
            return;
        }
        if ((double) time == config.getAdminPunishment() * 20) {
            punishes++;
            Chat.sendAdminAndConsole(Chat.format(config.getPunishMessage(), playerName, punishes, config.getCountPunishmentKick()));
            time = 0;
            times.put(playerName, time);
            punishments.put(playerName, punishes);
        }
    }

    private void kickPlayer(String playerName) {
        int time = times.getOrDefault(playerName, 0);
        if ((double) time == config.getPlayerKick() * 20 && config.getCountPunishmentKick() == punishments.get(playerName)) {
            Objects.requireNonNull(Bukkit.getPlayer(playerName)).sendMessage("&7Вы были кикнуты за использование &c&lBariton. Он запрещен на сервере, советуем его удалить пока не поздно :)");
            Objects.requireNonNull(Bukkit.getPlayer(playerName)).kickPlayer(Chat.format(config.getKickMessage(), playerName, 0, 0));
            Chat.sendAdminAndConsole("Игрок: " + playerName + "был кикнут за использование баритона");
            times.put(playerName, 0);
            punishments.put(playerName, 0);
            use.put(playerName, false);
            arithmetical.put(playerName, 0);
        }
    }
}