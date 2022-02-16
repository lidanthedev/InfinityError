package me.lidan.infinityerror.Abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StopTime{
    public static void active(Player p){
        String gameRule = p.getWorld().getGameRuleValue("doDaylightCycle");
        if (gameRule.equals("true")){
            p.getWorld().setGameRuleValue("doDaylightCycle", "false");
            p.sendMessage("");
        }
        else {
            p.getWorld().setGameRuleValue("doDaylightCycle", "true");
        }
    }
}
