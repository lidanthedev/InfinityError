package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Infinityerror;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BlackHole {
    public static void active(Player p){
        Arrow arrow = p.launchProjectile(Arrow.class);
        new BukkitRunnable(){

            @Override
            public void run() {
                arrow.getWorld().spawnParticle(Particle.CRIT_MAGIC, arrow.getLocation(), 100);
                if (arrow.isDead()){
                    cancel();
                }
            }

        }.runTaskTimer(Infinityerror.getInstance(),1L,1L);
    }
}
