package me.axorom.bmanticheat.GraphMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PlayerPath {
    private final Map<Player, Stack<Line3D>> playerPaths;

    public PlayerPath() {
        this.playerPaths = new HashMap<>();
    }

    public Stack<Line3D> getPlayerPath(Player player) {
        return playerPaths.get(player);
    }

    public void addPlayerPath(Player player, Location location) {
        Stack<Line3D> stack = new Stack<>();
        stack.add(new Line3D(location));
        playerPaths.put(player, new Stack<Line3D>());
    }
}
