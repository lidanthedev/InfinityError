package me.lidan.infinityerror.Abilities;

import me.lidan.infinityerror.Infinityerror;
import me.lidan.infinityerror.Util.Functions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PasteBlock {
    public static void active(Player p){
        Material blockMaterial = SaveBlock.players.getOrDefault(p, Material.AIR);
        Block targetBlock = p.getTargetBlock(null, 20);
        if (targetBlock.getType() == Material.AIR) return;
        if (p.isSneaking()){
            Material changeType = targetBlock.getType();
            // p.sendMessage("Sneak paste " + targetBlock.getType());
            ArrayList<Block> blocks = Functions.loopBlocksCube(targetBlock.getLocation(),5);
            for (Block block: blocks) {
                if (block.getType().equals(changeType)) {
                    block.setType(blockMaterial);
                }
                /*else if (block.getType() != Material.AIR){
                    Infinityerror.LOGGER.info("{} Not changed cuz its {}",p , block.getType());
                    p.sendBlockChange(block.getLocation(), Material.BRICK, (byte) 0);
                }*/
            }
        }
        targetBlock.setType(blockMaterial);
    }
}
