package me.axorom.baritonechecker;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private final FileConfiguration config;
    private final String clearMessage;
    private final String punishMessage;
    private final String kickMessage;
    private final String kickArithMessage;
    private final double adminPunish;
    private final int arithmeticalMean;
    private final int countPunish;
    private final double kick;
    private final double clearPunishments;
    private final double yawRange;
    private final double yawRangeCheck;
    private final boolean useCamera;
    private final boolean checkCamera;
    private final boolean checkFlight;
    private final boolean checkWhenSprinting;
    private final boolean checkWater;
    private final boolean checkSneak;
    private final boolean checkWhenSwimming;
    private final boolean debug;
    private final boolean moveCheck;
    private final double moveRange;
    private final double multiplier;
    private final double arithmeticalPunish;
    private final double arithmeticalMinimum;
    private final double arithmeticalMaximum;
    public FileConfiguration getConfig() {
        return config;
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

    public double getAdminPunish() {
        return adminPunish;
    }

    public int getCountPunish() {
        return countPunish;
    }

    public double getKick() {
        return kick;
    }

    public double getClearPunishments() {
        return clearPunishments;
    }

    public double getYawRange() {
        return yawRange;
    }

    public double getYawRangeCheck() {
        return yawRangeCheck;
    }

    public boolean isUseCamera() {
        return useCamera;
    }

    public int getArithmeticalMean() {
        return arithmeticalMean;
    }

    public boolean checkCamera() {
        return checkCamera;
    }

    public boolean checkFlight() {
        return checkFlight;
    }

    public boolean checkWhenSprinting() {
        return checkWhenSprinting;
    }

    public boolean checkWater() {
        return checkWater;
    }

    public boolean checkSneak() {
        return checkSneak;
    }

    public boolean checkWhenSwimming() {
        return checkWhenSwimming;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getKickArithMessage() {
        return kickArithMessage;
    }

    public boolean isMoveCheck() {
        return moveCheck;
    }

    public double getMoveRange() {
        return moveRange;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double getArithmeticalPunish() {
        return arithmeticalPunish;
    }

    public double getArithmeticalMinimum() {
        return arithmeticalMinimum;
    }

    public double getArithmeticalMaximum() {
        return arithmeticalMaximum;
    }

    public Config() {
        config = BaritoneChecker.instance.getConfig();
        clearMessage = config.getString("clear-message");
        punishMessage = config.getString("baritone.punish-message");
        kickArithMessage = config.getString("baritone.arithmetical-mean-message");
        kickMessage = config.getString("baritone.kick-message");
        adminPunish = config.getDouble("baritone.after.admin-punishment");
        countPunish = config.getInt("baritone.after.count-punishment-kick");
        kick = config.getDouble("baritone.after.player-kick");
        clearPunishments = config.getDouble("baritone.after.clear-punishments");
        yawRange = config.getDouble("baritone.custom-settings.yaw-range");
        yawRangeCheck = config.getDouble("baritone.custom-settings.camera-yaw-range-check");
        useCamera = config.getBoolean("baritone.custom-settings.use-camera-yaw-instead-vector");
        checkCamera = config.getBoolean("baritone.custom-settings.check-camera-yaw-for-optimization");
        checkFlight = config.getBoolean("baritone.custom-settings.check-in-flight");
        checkWhenSprinting = config.getBoolean("baritone.custom-settings.check-only-when-sprinting");
        checkWhenSwimming = config.getBoolean("baritone.custom-settings.check-when-swimming");
        checkSneak = config.getBoolean("baritone.custom-settings.check-in-sneak");
        checkWater = config.getBoolean("baritone.custom-settings.check-in-water");
        arithmeticalMean = config.getInt("baritone.arithmetical.mean");
        debug = config.getBoolean("baritone.debug");
        moveCheck = config.getBoolean("baritone.additional-movement-check");
        moveRange = config.getDouble("baritone.additional-movement-range");
        multiplier = config.getDouble("baritone.additional-movement-diagonal-multi");
        arithmeticalPunish = config.getDouble("baritone.arithmetical.punish");
        arithmeticalMinimum = config.getDouble("baritone.arithmetical.lower-value");
        arithmeticalMaximum = config.getDouble("baritone.arithmetical.higher-value");
    }

}
