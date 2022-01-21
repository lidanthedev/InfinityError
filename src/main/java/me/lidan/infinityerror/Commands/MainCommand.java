package me.lidan.infinityerror.Commands;

import me.lidan.infinityerror.Util.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage("ERROR! you must put more args");
                return false;
            }
            if (args[0].equalsIgnoreCase("give")){
                p.getInventory().addItem(Items.INFINITY_GAUNTLET);
                p.getInventory().addItem(Items.SPACE_STONE);
            }

        }
        return true;
    }
}
