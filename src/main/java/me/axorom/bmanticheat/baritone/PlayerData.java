package me.axorom.bmanticheat.baritone;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class PlayerData {
    private Double maxMarginX;
    private Double maxMarginZ;
    private Double maxMarginXZ;
    private int yawPitch;
    private Integer timer;

    public PlayerData() {
        maxMarginX = 0d;
        maxMarginZ = 0d;
        maxMarginXZ = 0d;
        yawPitch = 0;
    }

    public void updateMaxX(double margin) {
        if (Double.compare(margin, maxMarginX) > 0)
            maxMarginX = margin;
    }

    public void updateMaxZ(double margin) {
        if (Double.compare(margin, maxMarginZ) > 0)
            maxMarginZ = margin;
    }

    public void updateMaxXZ(double margin) {
        if (Double.compare(margin, maxMarginXZ) > 0)
            maxMarginXZ = margin;
    }
}

