package me.axorom.bmanticheat;

import me.axorom.bmanticheat.utils.Chat;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class RunnableContainer {
    public RunnableContainer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (BMAntiCheat.config.isSendResetMessage())
                    Chat.sendAdminAndConsole(Chat.format(BMAntiCheat.config.getResetMessage(), "", 0, 0));
                IllegalClickListener.playersPunishments = new HashMap<>();
                IllegalDigListener.playerValuableBlocks = new HashMap<>();
                IllegalDigListener.playerAbortCounts = new HashMap<>();
                IllegalDigListener.playersPunishments = new HashMap<>();
            }
        }.runTaskTimer(BMAntiCheat.instance, 0L, BMAntiCheat.config.getPeriodResetPunishments() * 20L);
    }
}
