package me.lidan.infinityerror.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class ChatMessage implements Listener {

    public static HashMap<Player,Long> players = new HashMap<>();
    public static HashMap<Player,String> lastinput = new HashMap<>();

    @EventHandler
    public void OnChatMessage(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if (players.getOrDefault(p ,0L) > 0){
            e.setCancelled(true);
            lastinput.put(p, e.getMessage());
            players.put(p, 0L);
        }
    }
}
