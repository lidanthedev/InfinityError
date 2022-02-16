package me.lidan.infinityerror.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Flight {
    public static void active(Player p){
        boolean fly = !p.getAllowFlight();
        if (fly){
            p.setVelocity(new Vector(0,1,0));
        }
        p.setAllowFlight(fly);
        p.setFlying(fly);
    }
}
