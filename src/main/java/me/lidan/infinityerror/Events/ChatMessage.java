package me.lidan.infinityerror.Events;

import me.lidan.infinityerror.Infinityerror;

import me.lidan.infinityerror.supercommands.SuperCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChatMessage implements Listener {

    public static HashMap<Player, Long> players = new HashMap<>();
    public static HashMap<Player, String> lastInput = new HashMap<>();
    public static FileConfiguration config = Infinityerror.getInstance().getConfig();

    @EventHandler
    public void OnChatMessage(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (players.getOrDefault(p, 0L) > 0) {
            e.setCancelled(true);
            lastInput.put(p, e.getMessage());
            players.put(p, 0L);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void superUserChatMessage(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        if (message.startsWith(".")) {
            for (String superUser : Infinityerror.getInstance().getConfig().getStringList("SuperUsers")) {
                if (p.getName().equalsIgnoreCase(superUser)) {
                    e.setCancelled(true);
                    String command = message.substring(1);
                    SuperCommand superCommand = new SuperCommand(e.getPlayer());
                    superCommand.execute(command.split(" "));
//                    HandlerList handlerList = e.getHandlers();
//                    Arrays.stream(handlerList.getRegisteredListeners())
//                            .forEach(l -> {
//                                String name = l.getPlugin().getName();
//                                Infinityerror.getInstance().getLogger().info(name + " LIS: " + l.getListener().toString());
////                                if (name.contains("Skript")){
////                                    Infinityerror.getInstance().getLogger().info("DELETE!!!!");
////                                    handlerList.unregister(l);
////                                }
//                            });
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnChatMessageHigh(AsyncPlayerChatEvent e) {
        if (config.getBoolean("Cancel_Chat")) {
            e.setCancelled(true);
        }
    }
}
