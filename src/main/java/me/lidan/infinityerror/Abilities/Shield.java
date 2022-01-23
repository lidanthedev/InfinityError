package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Infinityerror;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

public class Shield {
    public static void active(Player p){
        p.setInvulnerable(true);
        GlowAPI.setGlowing(p, GlowAPI.Color.AQUA, Bukkit.getOnlinePlayers());
        new BukkitRunnable(){
            @Override
            public void run() {
                p.setInvulnerable(false);
                GlowAPI.setGlowing(p, null, Bukkit.getOnlinePlayers());
            }
        }.runTaskLater(Infinityerror.getInstance(),100L);
    }
}
