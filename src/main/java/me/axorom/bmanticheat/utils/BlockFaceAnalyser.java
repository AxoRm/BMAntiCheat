package me.axorom.bmanticheat.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class BlockFaceAnalyser {
    private static final BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};

    public static boolean isAirOrPartialNear(Block clickedBlock) {
        return isAirNear(clickedBlock) || isPartialNear(clickedBlock);
    }

    public static boolean isAirNear(Block clickedBlock) {
        return Arrays.stream(faces)
                .map(clickedBlock::getRelative)
                .map(Block::getType)
                .anyMatch(Material::isAir);
    }


    public static boolean isPartialNear(Block clickedBlock) {
        return Arrays.stream(faces)
                .map(clickedBlock::getRelative)
                .map(Block::getType)
                .anyMatch(PartialBlockChecker::isPartialBlock);
    }

    public static List<Block> getValuableBlocks(Block digedBlock, String name, List<String> valuableMaterials) {
        return Arrays.stream(faces)
                .map(digedBlock::getRelative)
                .filter(block -> !isAirOrPartialNear(block))
                .filter(block -> valuableMaterials.contains(block.getType().toString().toLowerCase()))
                .collect(Collectors.toList());
    }
}
