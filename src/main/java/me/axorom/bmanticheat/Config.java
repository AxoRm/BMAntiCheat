package me.axorom.bmanticheat;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private final String clearMessage;
    private final String punishMessage;
    private final String kickMessage;
    private final String arithmeticalMeanMessage;
    private final double adminPunishment;
    private final int arithmeticalMean;
    private final int countPunishmentKick;
    private final double playerKick;
    private final double clearPunishments;
    private final double yawRange;
    private final double cameraYawRangeCheck;
    private final boolean useCameraYawInsteadVector;
    private final boolean checkCameraYawForOptimization;
    private final boolean checkInFlight;
    private final boolean checkOnlyWhenSprinting;
    private final boolean checkInWater;
    private final boolean checkInSneak;
    private final boolean checkWhenSwimming;
    private final boolean debug;
    private final boolean additionalMovementCheck;
    private final double additionalMovementRange;
    private final double additionalMovementDiagonalMulti;
    private final double arithmeticalPunish;
    private final double arithmeticalLowerValue;
    private final double arithmeticalHigherValue;

    public Config() {
        FileConfiguration config = BMAntiCheat.instance.getConfig();
        clearMessage = config.getString("clear-message");
        punishMessage = config.getString("baritone.punish-message");
        arithmeticalMeanMessage = config.getString("baritone.arithmetical-mean-message");
        kickMessage = config.getString("baritone.kick-message");
        adminPunishment = config.getDouble("baritone.after.admin-punishment");
        countPunishmentKick = config.getInt("baritone.after.count-punishment-kick");
        playerKick = config.getDouble("baritone.after.player-kick");
        clearPunishments = config.getDouble("baritone.after.clear-punishments");
        yawRange = config.getDouble("baritone.custom-settings.yaw-range");
        cameraYawRangeCheck = config.getDouble("baritone.custom-settings.camera-yaw-range-check");
        useCameraYawInsteadVector = config.getBoolean("baritone.custom-settings.use-camera-yaw-instead-vector");
        checkCameraYawForOptimization = config.getBoolean("baritone.custom-settings.check-camera-yaw-for-optimization");
        checkInFlight = config.getBoolean("baritone.custom-settings.check-in-flight");
        checkOnlyWhenSprinting = config.getBoolean("baritone.custom-settings.check-only-when-sprinting");
        checkWhenSwimming = config.getBoolean("baritone.custom-settings.check-when-swimming");
        checkInSneak = config.getBoolean("baritone.custom-settings.check-in-sneak");
        checkInWater = config.getBoolean("baritone.custom-settings.check-in-water");
        arithmeticalMean = config.getInt("baritone.arithmetical.mean");
        debug = config.getBoolean("baritone.debug");
        additionalMovementCheck = config.getBoolean("baritone.additional-movement-check");
        additionalMovementRange = config.getDouble("baritone.additional-movement-range");
        additionalMovementDiagonalMulti = config.getDouble("baritone.additional-movement-diagonal-multi");
        arithmeticalPunish = config.getDouble("baritone.arithmetical.punish");
        arithmeticalLowerValue = config.getDouble("baritone.arithmetical.lower-value");
        arithmeticalHigherValue = config.getDouble("baritone.arithmetical.higher-value");
    }

    public String getClearMessage() {
        return clearMessage;
    }

    public String getPunishMessage() {
        return punishMessage;
    }

    public String getKickMessage() {
        return kickMessage;
    }

    public double getAdminPunishment() {
        return adminPunishment;
    }

    public int getCountPunishmentKick() {
        return countPunishmentKick;
    }

    public double getPlayerKick() {
        return playerKick;
    }

    public double getClearPunishments() {
        return clearPunishments;
    }

    public double getYawRange() {
        return yawRange;
    }

    public double getCameraYawRangeCheck() {
        return cameraYawRangeCheck;
    }

    public boolean isUseCameraYawInsteadVector() {
        return useCameraYawInsteadVector;
    }

    public int getArithmeticalMean() {
        return arithmeticalMean;
    }

    public boolean isCheckCameraYawForOptimization() {
        return checkCameraYawForOptimization;
    }

    public boolean isCheckInFlight() {
        return checkInFlight;
    }

    public boolean isCheckOnlyWhenSprinting() {
        return checkOnlyWhenSprinting;
    }

    public boolean isCheckInWater() {
        return checkInWater;
    }

    public boolean isCheckInSneak() {
        return checkInSneak;
    }

    public boolean isCheckWhenSwimming() {
        return checkWhenSwimming;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getArithmeticalMeanMessage() {
        return arithmeticalMeanMessage;
    }

    public boolean isAdditionalMovementCheck() {
        return additionalMovementCheck;
    }

    public double getAdditionalMovementRange() {
        return additionalMovementRange;
    }

    public double getAdditionalMovementDiagonalMulti() {
        return additionalMovementDiagonalMulti;
    }

    public double getArithmeticalPunish() {
        return arithmeticalPunish;
    }

    public double getArithmeticalLowerValue() {
        return arithmeticalLowerValue;
    }

    public double getArithmeticalHigherValue() {
        return arithmeticalHigherValue;
    }
}
