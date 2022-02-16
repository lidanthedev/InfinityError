package me.lidan.infinityerror;

import me.lidan.infinityerror.Abilities.Ability;
import me.lidan.infinityerror.Commands.FileFind;
import me.lidan.infinityerror.Commands.MainCommand;
import me.lidan.infinityerror.Events.ChatMessage;
import me.lidan.infinityerror.Events.OnDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
        getServer().getPluginManager().registerEvents(new ChatMessage(), this);
        getServer().getPluginManager().registerEvents(new OnDamage(), this);
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
