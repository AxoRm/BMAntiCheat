package me.axorom.bmanticheat;

import me.axorom.bmanticheat.utils.Chat;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnableContainer {
    public RunnableContainer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (BMAntiCheat.config.isSendResetMessage())
                    Chat.sendAdminAndConsole(Chat.format(BMAntiCheat.config.getResetMessage(), "", 0, 0));
                BMAntiCheat.instance.clearData();
            }
        }.runTaskTimer(BMAntiCheat.instance, 0L, BMAntiCheat.config.getPeriodResetPunishments() * 20L);
    }
}
