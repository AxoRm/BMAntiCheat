package me.axorom.bmanticheat.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.axorom.bmanticheat.BMAntiCheat;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BlockDigListener extends PacketAdapter {
    EnumWrappers.PlayerDigType digType;

    public BlockDigListener(EnumWrappers.PlayerDigType digType) {
        super(BMAntiCheat.instance, ListenerPriority.LOWEST, PacketType.Play.Client.BLOCK_DIG);
        this.digType = digType;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        EnumWrappers.PlayerDigType action = event.getPacket().getPlayerDigTypes().read(0);
        if (action != digType) return;
        Player player = event.getPlayer();
        Block block = getClickedBlock(event);
        analyzePacket(player, block, event);
    }

    public abstract void analyzePacket(Player player, Block digedBlock, PacketEvent event);

    private Block getClickedBlock(PacketEvent event) {
        BlockPosition blockPosition = event.getPacket().getBlockPositionModifier().read(0);
        return event.getPlayer().getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }
}
