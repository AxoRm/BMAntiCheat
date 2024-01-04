package me.axorom.bmanticheat.baritone;

public class LineCheck {
    public static boolean isXLine(boolean isSprint, double xFrom, double xTo, double zFrom, double zTo) {
        double xRange = Math.abs(xTo - xFrom);
        // double zRange = Math.abs(zTo - zFrom);
        if (isSprint && !(0.279 <= xRange && xRange <= 0.281)) return false;
        if (!(0.214 <= xRange && xRange <= 0.216)) return false;
        if ((int) zFrom == (int) zTo) return false;
        double XtoDeviation = Math.abs(Math.abs(xTo) - (int) Math.abs(xTo) - 0.5);
        double ZtoDeviation = Math.abs(Math.abs(xTo) - (int) Math.abs(xTo) - 0.5);
        if (moduleXto - (int) moduleXto)
            return true;
    }

    public boolean isZLine(boolean isSprint, double xFrom, double xTo, double zFrom, double zTo) {
        return false;
    }

    public boolean isXZLine(boolean isSprint, double xFrom, double xTo, double zFrom, double zTo) {
        return false;
    }
}
