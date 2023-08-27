package me.axorom.bmanticheat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class IllegalClickListener {

    public IllegalClickListener() {
        ProtocolManager manager = BMAntiCheat.manager;
        manager.addPacketListener(new PacketAdapter(BMAntiCheat.instance, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                    // Получить действие из пакета
                    EnumWrappers.PlayerDigType action = event.getPacket().getPlayerDigTypes().read(0);

                    // Проверить, является ли действие START_DESTROY_BLOCK или STOP_DESTROY_BLOCK (для BLOCK_DIG)
                    // или BLOCK_PLACE (для BLOCK_PLACE)
                    if (action == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK || action == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK || event.getPacketType() == PacketType.Play.Client.BLOCK_PLACE) {
                        // Получить координаты блока из пакета
                        BlockPosition blockPosition = event.getPacket().getBlockPositionModifier().read(0);

                        // Получить блок по координатам
                        Block clickedBlock = event.getPlayer().getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());

                        Block targetBlock = event.getPlayer().getTargetBlockExact(5);

                        if (!clickedBlock.equals(targetBlock)) {
                            Chat.sendAdminAndConsole("Игрок: " + event.getPlayer().getName());
                            Chat.sendAdminAndConsole(" Кликал по блоку/смотрел на блок: " + clickedBlock.getLocation() + " / " + (targetBlock == null ? "null" : targetBlock.getLocation()));
                            Chat.sendAdminAndConsole("RayTrace: (true -  not cheater)" + isLineOfSightClear(event.getPlayer(), clickedBlock));
                            Chat.sendAdminAndConsole("eye location: " + event.getPlayer().getEyeLocation());
//                        event.getPlayer().kickPlayer(Chat.format("&cВы подозреваетесь в использовании &l&4Anti-X-Ray Bypass&c, если это не так, пожалуйста, сообщите&a администрации проекта!", event.getPlayer().getName(), 0, 0));
                        }
                    }
                }
            }

        });
    }

    public boolean isLineOfSightClear(Player player, Block targetBlock) {
        Location playerEyeLocation = player.getEyeLocation();
        Location blockLocation = targetBlock.getLocation();
        World blockWorld = blockLocation.getWorld();
        double blockX = blockLocation.getX() + 0.5;
        double blockY = blockLocation.getY() + 0.5;
        double blockZ = blockLocation.getZ() + 0.5;
        double playerX = playerEyeLocation.getX();
        double playerY = playerEyeLocation.getY();
        double playerZ = playerEyeLocation.getZ();

        boolean[] faces = new boolean[6];
        faces[0] = playerY > blockY && playerY - blockY >= 0.5; // faceUp
        faces[1] = !faces[0] && blockX - playerX >= 0.5; // faceDown
        faces[2] = playerX > blockX && playerX - blockX >= 0.5; // facePositiveX
        faces[3] = !faces[2] && blockX - playerX >= 0.5; // faceNegativeX
        faces[4] = playerZ > blockZ && playerZ - blockZ >= 0.5; // facePositiveZ
        faces[5] = !faces[4] && blockZ - playerZ >= 0.5; // faceNegativeZ
        Bukkit.getLogger().info("АНАЛИЗ ПОЛУЧИЛСЯ, ОПРЕДЕЛЕНО ПОЛОЖЕНИЕ ИГРОКА");
        for (int i = 0; i < faces.length; i++) {
            if (faces[i]) {
                Bukkit.getLogger().info("i: " + i);
                if (rayTraceFace(blockLocation, blockX - 0.5, blockY - 0.5, blockZ - 0.5, i, playerEyeLocation, blockWorld)) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean rayTraceFace(Location blockLocation, double blockX, double blockY, double blockZ, int face, Location playerEyeLocation, World blockWorld) {

        double multX = 0.1;
        double multZ = 0.1;
        double multY = 0.1;
        double startX = blockX + 0.05;
        double startY = blockY + 0.05;
        double startZ = blockZ + 0.05;
        switch (face) {
            case 0:
                startY = blockY + 0.99999;
                multY = 0;
                break;
            case 1:
                startY -= 0.0499;
                multY = 0;
                break;
            case 2:
                startX = blockX + 0.99999;
                multX = 0;
                break;
            case 3:
                startX -= 0.0499;
                multX = 0;
                break;
            case 4:
                startZ = blockZ + 0.99999;
                multZ = 0;
                break;
            case 5:
                startZ -= 0.0499;
                multZ = 0;
                break;
        }
        Bukkit.getLogger().info("НАЧАТА ПРОВЕРКА ПЛОСКОСТИ" + " startX: " + startX + " startY: " + startY + " startZ: " + startZ + " multX: " + multX + " multY: " + multY + " multZ: " + multZ);
        double currentX = startX;
        double currentY = startY;
        double currentZ = startZ;
        do {
            do {
                do {
                    Bukkit.getLogger().info("X: " + currentX + ", Y: " + currentY + ", Z: " + currentZ);
                    Location pointLocation = new Location(blockWorld, currentX, currentY, currentZ);
                    Vector direction = pointLocation.toVector().subtract(playerEyeLocation.toVector()); // направление - от игрока к целевому блоку
                    RayTraceResult rayTraceResult = blockWorld.rayTraceBlocks(playerEyeLocation, direction, direction.length());
                    currentY += multY;
                    if (rayTraceResult == null || rayTraceResult.getHitBlock() == null) {
                        continue;
                    }
                    if (rayTraceResult.getHitBlock().getLocation().equals(blockLocation)) {
                        return true;
                    }
                } while (currentY < blockY + 1 && multY != 0);
                currentY = startY;
                currentZ += multZ;
            } while (currentZ < blockZ + 1 && multZ != 0);
            currentZ = startZ;
            currentX += multX;
        } while (currentX < blockX + 1 && multX != 0);
        return false;
    }
}
