package me.axorom.bmanticheat.utils;

import org.bukkit.block.Block;

public class BlockAnalyzer {

    public static boolean isAirOrPartial(Block clickedBlock) {
        return isAir(clickedBlock) || isPartial(clickedBlock);
    }

    public static boolean isAir(Block clickedBlock) {
        return clickedBlock.getType().isAir();
    }


    public static boolean isPartial(Block clickedBlock) {
        return PartialBlockChecker.isPartialBlock(clickedBlock.getType());
    }
}
