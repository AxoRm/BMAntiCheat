package me.axorom.bmanticheat.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;

@Setter
@Getter
public class PlayerData {
    public double meanYaw = 0;
    public int countYaw = 0;
    public double meanDeltaX = 0;
    public int countX = 0;
    public double meanDeltaZ = 0;
    public int countZ = 0;
    public double meanDeltaXZ = 0;
    public int countXZ = 0;
    int iDigPunishments;
    int iDigAbortCount;
    int iClickPunishments;
    int baritoneLine;
    int baritoneLineCount;
    int baritoneYawPitch;
    int baritoneYawPitchCount;
    HashSet<Block> iDigValuableBlocks;
    double baritonePrevDeviation;

    public PlayerData(Player player) {
        iDigValuableBlocks = new HashSet<>();
        iDigPunishments = 0;
        iDigAbortCount = 0;
        iClickPunishments = 0;
        baritoneLine = 0;
        baritonePrevDeviation = 0;
        baritoneLineCount = 0;
        baritoneYawPitch = 0;
        baritoneYawPitchCount = 0;
    }

    public void incrementIDigAbortCount() {
        iDigAbortCount++;
    }

    public void incrementIDigPunishments() {
        iDigPunishments++;
    }

    public void incrementIClickPunishments() {
        iClickPunishments++;
    }

    public void incrementBaritoneLine() {
        baritoneLine++;
    }

    public void incrementBaritoneYawPitch() {
        baritoneYawPitch++;
    }
}

