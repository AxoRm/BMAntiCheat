package me.axorom.baritonechecker;

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

public class Listeners implements Listener {
    private final Config config = BaritoneChecker.config;
    public static HashMap<String, Pair<Integer, Double>> players = new HashMap<>();
    public static HashMap<String, Integer> punishments = new HashMap<>();
    public static HashMap<String, Integer> times = new HashMap<>();
    public static HashMap<String, Integer> arithmetical = new HashMap<>();
    public static HashMap<String, Boolean> use = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        /*
        OPTIMIZATIONS
         */
        Player player = event.getPlayer();
        if (player.hasPermission("baritone.bypass"))
            return;
        if (player.isSwimming() && !config.checkWhenSwimming())
            return;
        if (player.isInWater() && !config.checkWater())
            return;
        if (!player.isSprinting() && config.checkWhenSprinting() && !player.isInWater())
            return;
        if (player.isFlying() && !config.checkFlight())
            return;
        if (player.isSneaking() && !config.checkSneak() && !player.isInWater() && !player.isFlying())
            return;
        if (config.checkCamera()) {
            float yaw = player.getLocation().getYaw();
            double angle = getAngleDistance(yaw);
            if (angle > config.getYawRangeCheck())
                return;
        }
        String playerName = player.getName();
        /*
        RANGE CHECKERS AND SAVES PER PLAYER
         */
        Location to = event.getTo();
        Location from = event.getFrom();
        if (to == null)
            return;
        double angle;
        if (config.isUseCamera()) {
            angle = player.getLocation().getYaw();
        } else {
            Vector velocity = new Vector(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());
            double yaw = getVectorAngle(velocity.getX(), velocity.getZ());
            angle = getAngleDistance(yaw);
        }
        if (angle > config.getYawRange()) {
            if (use.getOrDefault(playerName, false)) {
                if (players.getOrDefault(playerName, new Pair<>(0, 0d)).getSecond() > config.getArithmeticalMinimum() && players.getOrDefault(playerName, new Pair<>(0, 0d)).getSecond() < config.getArithmeticalMaximum() && players.getOrDefault(playerName, new Pair<>(0, 0d)).getFirst() >= config.getArithmeticalPunish()) {
                    arithmetical.put(playerName, arithmetical.getOrDefault(playerName, 0) + 1);
                    Chat.sendAdminAndConsole(Chat.format(config.getKickArithMessage(), playerName, arithmetical.get(playerName), config.getArithmeticalMean()));
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
        Additional movement check
         */
        if (config.isMoveCheck()) {
            double fx = Math.abs((from.getX() - (int) from.getX()));
            double fz = Math.abs((from.getZ() - (int) from.getZ()));
            double tx = Math.abs((to.getX() - (int) to.getX()));
            double tz = Math.abs((to.getZ() - (int) to.getZ()));
            double range = config.getMoveRange();
            double multiplier = config.getMultiplier();
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
        ADDITION CHECK Arithmetical Mean
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
        DeBUGGER
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
        double maxAngle = Math.ceil(angle / 45)*45 - angle;
        double minAngle = angle - Math.floor(angle / 45)*45;
        return Math.min(maxAngle, minAngle);
    }

    private void addTime(String playerName) {
        int time = times.getOrDefault(playerName, 0) + 1;
        times.put(playerName, time);
        int punishes = punishments.getOrDefault(playerName, 0);
        if (punishes >= config.getCountPunish()) {
            return;
        }
        if ((double) time == config.getAdminPunish()*20) {
            punishes++;
            Chat.sendAdminAndConsole(Chat.format(config.getPunishMessage(), playerName, punishes, config.getCountPunish()));
            time = 0;
            times.put(playerName, time);
            punishments.put(playerName, punishes);
        }
    }

    private void kickPlayer(String playerName) {
        int time = times.getOrDefault(playerName, 0);
        if ((double) time == config.getKick()*20 && config.getCountPunish() == punishments.get(playerName)) {
            Bukkit.getPlayer(playerName).sendMessage("&7Вы были кикнуты за использование &c&lBariton. Он запрещен на сервере, советуем его удалить пока не поздно :)");
            Objects.requireNonNull(Bukkit.getPlayer(playerName)).kickPlayer(Chat.format(config.getKickMessage(), playerName, 0, 0));
            Chat.sendAdminAndConsole("Игрок: " + playerName + "был кикнут за использование баритона");
            times.put(playerName, 0);
            punishments.put(playerName, 0);
            use.put(playerName, false);
            arithmetical.put(playerName, 0);
        }
    }

}