package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Events.ChatMessage;
import me.lidan.infinityerror.Infinityerror;
import me.lidan.infinityerror.Util.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class Portal {
    public static void inputLocation(Player p){
        new BukkitRunnable(){
            int phase = 0;
            String input = "";
            Location tpLoc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
            @Override
            public void run() {
                if (phase == 0){
                    Functions.chatInput(p,"Enter Location in Chat (for example: world 0 100 0)");
                    phase++;
                }
                if (phase == 2){
                    String[] inputs = input.split(" ");
                    if (inputs.length != 4){
                        p.sendMessage("ERROR! invalid input!");
                        phase = 0;
                        return;
                    }
                    if (Bukkit.getWorld(inputs[0]) == null){
                        p.sendMessage(ChatColor.RED + "ERROR! this world is not real " + inputs[0]);
                        phase = 0;
                        return;
                    }
                    tpLoc.setWorld(Bukkit.getWorld(inputs[0]));
                    tpLoc.setX(Double.parseDouble(inputs[1]));
                    tpLoc.setY(Double.parseDouble(inputs[2]));
                    tpLoc.setZ(Double.parseDouble(inputs[3]));
                    p.teleport(tpLoc);
                    p.sendMessage(ChatColor.BLUE + "Teleported!");
                }
                if (!Objects.equals(ChatMessage.lastinput.get(p), "")) {
                    phase++;
                    input = ChatMessage.lastinput.get(p);
                }
                if (phase == 8){
                    cancel();
                }
            }
        }.runTaskTimer(Infinityerror.getInstance(),0L,1L);
    }
}
