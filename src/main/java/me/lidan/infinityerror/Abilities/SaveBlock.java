package me.lidan.infinityerror.Abilities;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Data
public class SaveBlock {

    public static HashMap<Player,Material> players = new HashMap<>();

    public static void active(Player p){
        Block targetBlock = p.getTargetBlock(null, 20);
        Material blockMaterial = targetBlock.getType();
        players.put(p, blockMaterial);
        p.sendMessage("Block saved: " + blockMaterial);
        // p.getWorld().getBlockAt(0,100,0).setType(Material.AIR);
    }
}
