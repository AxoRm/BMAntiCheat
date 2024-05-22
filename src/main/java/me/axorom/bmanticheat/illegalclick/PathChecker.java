package me.axorom.bmanticheat.illegalclick;

import me.axorom.bmanticheat.utils.BlockAnalyzer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathChecker {
    private static final BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};

    public static boolean isPathBetween(Location start, Location end) {
        Block startBlock = start.getBlock();
        Block endBlock = end.getBlock();
        Set<Block> checkedBlocks = new HashSet<>();
        Queue<Block> blocksForCheck = new LinkedList<>(getRelativeBlocks(startBlock));
        while (!blocksForCheck.isEmpty()) {
            Block block = blocksForCheck.poll();
            if (block == null || checkedBlocks.contains(block))
                continue;
            if (block.getLocation().equals(endBlock.getLocation()))
                return true;
            if (block.getLocation().add(0.5d, 0.5d, 0.5d).distanceSquared(end) > 27) // 5^2 + sqrt(1^2+1^2)
                continue;
            List<Block> relativeBlocks = getRelativeBlocks(block);
            checkedBlocks.add(block);
            blocksForCheck.addAll(relativeBlocks);
        }
        return false;
    }


    public static List<Block> getRelativeBlocks(Block block) {
        return Stream.of(faces)
                .map(block::getRelative)
                .filter(BlockAnalyzer::isAirOrPartial)
                .collect(Collectors.toList());
    }
}