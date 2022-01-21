package me.lidan.infinityerror;

import me.lidan.infinityerror.Abilities.Ability;
import me.lidan.infinityerror.Commands.MainCommand;
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
