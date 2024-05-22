package me.axorom.bmanticheat.baritone;

import me.axorom.bmanticheat.BMAntiCheat;
import me.axorom.bmanticheat.utils.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LineCheck {

    public static boolean checkLine(Location from, Location to, Player player) {
        PlayerData playerData = BMAntiCheat.playerDataMap.get(player);
        if (isXLine(playerData, player.isSprinting(), from.getX(), to.getX(), from.getZ(), to.getZ())
                || isZLine(playerData, player.isSprinting(), from.getX(), to.getX(), from.getZ(), to.getZ())
                || isXZLine(playerData, player.isSprinting(), from.getX(), to.getX(), from.getZ(), to.getZ())) {
            double angle = to.getYaw() % 360;
            angle = (angle + 360) % 360;
            angle %= 45;
            if (45 - angle < angle) {
                angle = -(45 - angle);
            }
            playerData.meanYaw += angle;
            playerData.countYaw++;
            return Math.abs(playerData.meanYaw / playerData.countYaw) < 5;
        } else {
            //System.out.println(playerData.meanYaw / playerData.countYaw);
            playerData.meanYaw = 0;
            playerData.countYaw = 0;
            return false;
        }
    }

    private static boolean isXLine(PlayerData data, boolean isSprint, double xFrom, double xTo, double zFrom, double zTo) {
        if (isLine(isSprint, xFrom, xTo, zFrom, zTo)) {
            data.meanDeltaX += Math.abs(xTo) - (int) Math.abs(xTo) - 0.5;
            data.countX++;
            return Math.abs(data.meanDeltaX / data.countX) < 0.02;
        } else {
            //System.out.println("srX: " + data.meanDeltaX / data.countX);
            data.meanDeltaX = 0;
            data.countX = 0;
            return false;
        }
    }

    private static boolean isLine(boolean isSprint, double lineFrom, double lineTo, double secondFrom, double secondTo) {
        double lineRange = Math.abs(lineTo - lineFrom);
        if (isSprint && !(0.279 <= lineRange && lineRange <= 0.281)) return false;
        if (!isSprint && !(0.214 <= lineRange && lineRange <= 0.216)) return false;
        double secondToDeviation = Math.abs(Math.abs(secondTo) - (int) Math.abs(secondTo) - 0.5);
        double secondFromDeviation = Math.abs(Math.abs(secondFrom) - (int) Math.abs(secondFrom) - 0.5);
        return secondToDeviation <= 0.1 && secondFromDeviation <= 0.1 && Math.abs(secondToDeviation - secondFromDeviation) > 0.0001;
    }

    private static boolean isZLine(PlayerData data, boolean isSprint, double xFrom, double xTo, double zFrom, double zTo) {
        if (isLine(isSprint, zFrom, zTo, xFrom, xTo)) {
            data.meanDeltaZ += Math.abs(zTo) - (int) Math.abs(zTo) - 0.5;
            data.countZ++;
            return true;
        } else {
            // System.out.println("srZ: " + data.meanDeltaZ / data.countZ);
            data.meanDeltaZ = 0;
            data.countZ = 0;
            return false;
        }
    }

    private static boolean isXZLine(PlayerData data, boolean isSprint, double xFrom, double xTo, double zFrom, double zTo) {
        double xRange = Math.abs(xTo - xFrom);
        double zRange = Math.abs(zTo - zFrom);
        if (isSprint && !(0.17 < xRange && xRange < 0.22)) {
            return false;
        }
        if (isSprint && !(0.17 < zRange && zRange < 0.22)) {
            return false;
        }
        if (!isSprint && !(0.13 < xRange && xRange < 0.18)) {
            return false;
        }
        if (!isSprint && !(0.13 < zRange && zRange < 0.18)) {
            return false;
        }
        double absXFrom = Math.abs(xFrom - (int) xFrom);
        double absXTo = Math.abs(xTo - (int) xTo);
        double absZFrom = Math.abs(zFrom - (int) zFrom);
        double absZTo = Math.abs(zTo - (int) zTo);
        double centerFrom = (absXFrom + absZFrom) / 2;
        double centerFromSecond = (1 + absXFrom - absZFrom) / 2;
        double centerTo = (absXTo + absZTo) / 2;
        double centerToSecond = (1 + absXTo - absZTo) / 2;
        double deviationFrom = Math.min(Math.sqrt(Math.pow(centerFrom - absXFrom, 2) + Math.pow(centerFrom - absZFrom, 2)), Math.sqrt(Math.pow(centerFromSecond - absXFrom, 2) + Math.pow(1 - centerFromSecond - absZFrom, 2)));
        double a = Math.pow(centerTo - absXTo, 2) + Math.pow(centerTo - absZTo, 2);
        double deviationTo = Math.min(Math.sqrt(a), Math.sqrt(Math.pow(centerToSecond - absXTo, 2) + Math.pow(1 - centerToSecond - absZTo, 2)));
        if (deviationTo > 0.05) return false;
        double deviationToSign = 1;
        if (deviationTo == Math.sqrt(a)) {
            if (absZTo - absXTo < 0) deviationToSign = -1;
        } else {
            if (absZTo + absXTo < 1) deviationToSign = -1;
        }
        double lastDeltaDeviation = data.getBaritonePrevDeviation();
        double deltaDeviation = deviationTo - deviationFrom;
        data.setBaritonePrevDeviation(deltaDeviation);
        if (Math.abs(deviationTo - deviationFrom) < 0.0001) return false;
        if (!(Math.abs(deltaDeviation - lastDeltaDeviation) < 0.00001)) {
            data.meanDeltaXZ += deviationToSign * Math.pow(deviationTo, 2);
            data.countXZ++;
            return Math.abs(data.meanDeltaXZ / data.countXZ) < 0.02;
        } else {
            // System.out.println("XZ: " + data.meanDeltaXZ / data.countXZ);
            data.meanDeltaXZ = 0;
            data.countXZ = 0;
            return false;
        }
    }
}
