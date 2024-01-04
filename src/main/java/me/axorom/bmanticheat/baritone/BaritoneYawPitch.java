package me.axorom.bmanticheat.baritone;

public class BaritoneYawPitch {
    /* The simplest attempt to prevent Baritone (if player is very stupid) */
    public static boolean check(float yawFrom, float pitchFrom, float yawTo, float pitchTo) {
        int yawChanged = Float.compare(yawFrom, yawTo);
        int pitchChanged = Float.compare(pitchFrom, pitchTo);
        return pitchChanged == 0 && yawChanged != 0;
    }
}
