package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Infinityerror;
import me.lidan.infinityerror.Util.Functions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Grab {

    public static HashMap<Player, Boolean> players = new HashMap<>();

    public static void grab(Player p){

        if (players.getOrDefault(p,false)){
            p.sendMessage("stop grab");
            players.put(p,false);
        }

        Entity grabbedEntity = Functions.getTargetEntity(p, 20, false);
        if (grabbedEntity == null) {
            p.sendMessage(ChatColor.RED + "ERROR! no entity found!");
            return;
        }
        players.put(p,true);
        new BukkitRunnable(){
            @Override
            public void run() {
                Location playerLocation = p.getLocation().clone();
                playerLocation.add(playerLocation.getDirection().multiply(5));
                grabbedEntity.teleport(playerLocation);
                if (!players.getOrDefault(p,true)){
                    cancel();
                }
                // Vector vector = p.getLocation().toVector();
            }
        }.runTaskTimer(Infinityerror.getInstance(),0L,1L);
    }
}
