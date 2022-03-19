package me.lidan.infinityerror.Events;

import me.lidan.infinityerror.Infinityerror;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class ChatMessage implements Listener {

    public static HashMap<Player,Long> players = new HashMap<>();
    public static HashMap<Player,String> lastInput = new HashMap<>();
    public static FileConfiguration config = Infinityerror.getInstance().getConfig();

    @EventHandler
    public void OnChatMessage(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if (players.getOrDefault(p ,0L) > 0){
            e.setCancelled(true);
            lastInput.put(p, e.getMessage());
            players.put(p, 0L);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void OnChatMessageHigh(AsyncPlayerChatEvent e){
        if (config.getBoolean("Cancel_Chat")){
            e.setCancelled(true);
        }
    }
}
