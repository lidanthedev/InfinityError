package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Infinityerror;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Shield {
    public static void active(Player p){
        p.setInvulnerable(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                p.setInvulnerable(false);
            }
        }.runTaskLater(Infinityerror.getInstance(),100L);
    }
}
