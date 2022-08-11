package me.lidan.infinityerror.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class InfoClickCommand implements TabExecutor {

    public static HashMap<Player,Boolean> players = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player ){
            Player player = (Player) sender;
            boolean currentState = players.getOrDefault(player, false);
            players.put(player,!currentState);
            player.sendMessage(ChatColor.AQUA + "Set InfoClick to " + !currentState);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
