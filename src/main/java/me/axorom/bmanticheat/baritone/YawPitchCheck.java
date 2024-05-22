package me.axorom.bmanticheat.baritone;

import org.bukkit.Location;

public class YawPitchCheck {
    /* The simplest attempt to prevent Baritone (if player is very stupid) */
    public static boolean check(Location from, Location to) {
        int yawChanged = Float.compare(from.getYaw(), to.getYaw());
        int pitchChanged = Float.compare(from.getPitch(), to.getPitch());
        return pitchChanged == 0 && yawChanged != 0 && to.getPitch() != 90f && from.getPitch() != 90f;
    }
}
