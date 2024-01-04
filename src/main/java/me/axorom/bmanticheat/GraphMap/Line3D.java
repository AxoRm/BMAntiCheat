package me.axorom.bmanticheat.GraphMap;

import org.bukkit.Location;
import org.bukkit.util.Vector;


public class Line3D {
    private final int x0;
    private final int y0;
    private final int z0;
    private final int a;
    private final int b;
    private final int c;
    private int x1;
    private int y1;
    private int z1;

    public Line3D(int x0, int y0, int z0, int x1, int y1, int z1) {
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        a = (y1 - y0);
        b = (x1 - x0);
        c = (z1 - z0);
    }

    public Line3D(Location from, Location to) {
        this.x0 = (int) from.getX();
        this.y0 = (int) from.getY();
        this.z0 = (int) from.getZ();
        this.x1 = (int) to.getX();
        this.y1 = (int) to.getY();
        this.z1 = (int) to.getZ();
        a = (y1 - y0);
        b = (x1 - x0);
        c = (z1 - z0);
    }

    public Line3D(Location to) {
        Location from = new Location(to.getWorld(), 0d, 0d, 0d);
        this.x0 = (int) from.getX();
        this.y0 = (int) from.getY();
        this.z0 = (int) from.getZ();
        this.x1 = (int) to.getX();
        this.y1 = (int) to.getY();
        this.z1 = (int) to.getZ();
        a = (y1 - y0);
        b = (x1 - x0);
        c = (z1 - z0);
    }

    public double getLength() {
        return Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow((y1 - y0), 2) + Math.pow((z1 - z0), 2));
    }

    public void updateLastCoords(int x1, int y1, int z1) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
    }

    public Vector getFirstVector() {
        return new Vector(this.x0, this.y0, this.z0);
    }

    public Vector getSecondVector() {
        return new Vector(this.x0, this.y0, this.z0);
    }

    public boolean isPointOnLine(int x, int y, int z) {
        return (x - x0) * a == (y - y0) * b && (y - y0) * c == (z - z0) * a;
    }
}
