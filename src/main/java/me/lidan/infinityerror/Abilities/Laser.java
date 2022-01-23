package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Infinityerror;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Laser {
    public static void active(Player p){
        Arrow arrow = p.launchProjectile(Arrow.class);
        arrow.addScoreboardTag("power-stone");
        new BukkitRunnable(){

            @Override
            public void run() {
                arrow.getWorld().spawnParticle(Particle.SPELL_WITCH, arrow.getLocation(), 10);
                if (arrow.isOnGround() || arrow.isDead()){
                    arrow.remove();
                    cancel();
                }
            }

        }.runTaskTimer(Infinityerror.getInstance(),1L,1L);
    }
}
