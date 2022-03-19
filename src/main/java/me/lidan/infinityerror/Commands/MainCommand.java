package me.lidan.infinityerror.Commands;

import me.lidan.infinityerror.Abilities.*;
import me.lidan.infinityerror.Events.ChatMessage;
import me.lidan.infinityerror.Infinityerror;
import me.lidan.infinityerror.Util.Functions;
import me.lidan.infinityerror.Util.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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
            if(args[0].equalsIgnoreCase("reload")){
                p.performCommand("plugman reload InfinityError");
            }
            if (args[0].equalsIgnoreCase("give")){
                p.getInventory().addItem(Items.INFINITY_GAUNTLET);
                p.getInventory().addItem(Items.SPACE_STONE);
            }
            if (args[0].equalsIgnoreCase("test-input")){
                Functions.chatInput(p,"Enter Something in chat:");
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if (!Objects.equals(ChatMessage.lastInput.get(p), "")) {
                            p.sendMessage("You Entered " + ChatMessage.lastInput.get(p));
                            cancel();
                        }
                    }
                }.runTaskTimer(Infinityerror.getInstance(),0L,1L);
            }
            if(args[0].equalsIgnoreCase("portal")){
                Portal.inputLocation(p);
            }
            if(args[0].equalsIgnoreCase("grab")){
                Grab.grab(p);
            }
            if(args[0].equalsIgnoreCase("shield")){
                Shield.active(p);
            }
            if(args[0].equalsIgnoreCase("black-hole")){
                BlackHole.active(p);
            }
            if(args[0].equalsIgnoreCase("laser")){
                Laser.active(p);
            }
            if(args[0].equalsIgnoreCase("saveblock")){
                SaveBlock.active(p);
            }
            if(args[0].equalsIgnoreCase("pasteblock")){
                PasteBlock.active(p);
            }
            if(args[0].equalsIgnoreCase("flight")){
                Flight.active(p);
            }
            if(args[0].equalsIgnoreCase("loop-error")){
                ArrayList<Block> blocks = Functions.loopBlocksCube(p.getLocation(),5);
                for (Block block: blocks) {
                    p.sendBlockChange(block.getLocation(), Material.BRICK,(byte) 0);
                }
            }
            if(args.length >= 2 && args[0].equalsIgnoreCase("look-at")){
                Player arg_player = Bukkit.getPlayer(args[1]);
                Location playerLoc = p.getLocation();
                Location entityLoc = arg_player.getLocation();
                float yaw = (float) Math.toDegrees(Math.atan2(
                        playerLoc.getZ() - entityLoc.getZ(), playerLoc.getX() - entityLoc.getX())) - 90;
                Location loc = arg_player.getLocation();
                loc.setYaw(yaw);
                arg_player.teleport(loc);
            }
        }
        return true;
    }
}
