package me.axorom.bmanticheat;

import com.electronwill.nightconfig.core.conversion.Path;
import lombok.Getter;

import java.util.List;

@Getter
public class Config {
    @Path("reset-message")
    public String resetMessage;

    @Path("send-reset-message")
    public boolean sendResetMessage;

    @Path("period-reset-punishments")
    public int periodResetPunishments;

    @Path("debug")
    public boolean debug;

    @Path("baritone.punish-message")
    public String bPunishMessage;

    @Path("baritone.kick-message")
    public String bKickMessage;

    @Path("baritone.arithmetical-punish-message")
    public String bArithmeticalPunishMessage;

    @Path("baritone.arithmetical-kick-message")
    public String bArithmeticalKickMessage;

    @Path("baritone.after.admin-notify")
    public int bAdminNotify;

    @Path("baritone.after.count-punishment-kick")
    public int bCountPunishmentKick;

    @Path("baritone.after.player-kick")
    public int bPlayerKick;

    @Path("baritone.arithmetical.mean")
    public int bMean;

    @Path("baritone.arithmetical.punish")
    public int bPunish;

    @Path("baritone.arithmetical.higher-value")
    public double bHigherValue;

    @Path("baritone.arithmetical.lower-value")
    public double bLowerValue;

    @Path("baritone.custom-settings.disabled-worlds")
    public List<String> bDisabledWorlds;

    @Path("baritone.custom-settings.yaw-range")
    public double bYawRange;

    @Path("baritone.custom-settings.use-camera-yaw-instead-vector")
    public boolean bUseCameraYawInsteadVector;

    @Path("baritone.custom-settings.check-camera-yaw-for-optimization")
    public boolean bCheckCameraYawForOptimization;

    @Path("baritone.custom-settings.camera-yaw-range-check")
    public int bCameraYawRangeCheck;

    @Path("baritone.custom-settings.check-in-flight")
    public boolean bCheckInFlight;

    @Path("baritone.custom-settings.check-in-water")
    public boolean bCheckInWater;

    @Path("baritone.custom-settings.check-in-sneak")
    public boolean bCheckInSneak;

    @Path("baritone.custom-settings.check-when-swimming")
    public boolean bCheckWhenSwimming;

    @Path("baritone.additional-movement-check")
    public boolean bAdditionalMovementCheck;

    @Path("baritone.additional-movement-range")
    public double bAdditionalMovementRange;

    @Path("baritone.additional-movement-diagonal-multi")
    public int bAdditionalMovementDiagonalMulti;

    @Path("click-through-wall.punish-message")
    public String cPunishMessage;

    @Path("click-through-wall.kick-message")
    public String cKickMessage;

    @Path("click-through-wall.admin-notify")
    public int cAdminNotify;

    @Path("click-through-wall.kick")
    public int cKick;

    @Path("click-through-wall.radius")
    public int cRadius;

    @Path("click-through-wall.blocks")
    public List<String> cBlocks;

    @Path("click-through-wall.disabled-worlds")
    public List<String> cDisabledWorlds;

    @Path("dig-through-wall.punish-message")
    public String dPunishMessage;

    @Path("dig-through-wall.kick-message")
    public String dKickMessage;

    @Path("dig-through-wall.valuable-blocks")
    public List<String> dValuableBlocks;

    @Path("dig-through-wall.click-count")
    public int dClickCount;

    @Path("dig-through-wall.kick")
    public int dKick;

    @Path("dig-through-wall.admin-notify")
    public int dAdminNotify;

    @Path("dig-through-wall.max-height")
    public int dMaxHeight;

    @Path("dig-through-wall.disabled-worlds")
    public List<String> dDisabledWorlds;

}
