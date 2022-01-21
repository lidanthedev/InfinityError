package me.lidan.infinityerror.Abilities;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

@Data
public class Ability {
    private String name;
    private Long cooldown;
    private Long lastused;
    private BossBar bossbar;

    public Ability(String name, Long cooldown) {
        this.name = name;
        this.cooldown = cooldown;
        this.bossbar = Bukkit.createBossBar("Draconic", BarColor.BLUE, BarStyle.SOLID);
        this.lastused = 0L;
    }
}
