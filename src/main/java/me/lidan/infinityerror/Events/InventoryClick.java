package me.lidan.infinityerror.Events;

import me.lidan.infinityerror.Commands.InfoClickCommand;
import me.lidan.infinityerror.Util.JsonMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (InfoClickCommand.players.getOrDefault(player, false)){
            new JsonMessage()
            .append(String.format("type=%s ", event.getClick())).setClickAsSuggestCmd(String.valueOf(event.getClick())).save()
            .append(String.format("action=%s ", event.getAction())).setClickAsSuggestCmd(String.valueOf(event.getAction())).save()
            .append(String.format("slot=%s ", event.getSlot())).setClickAsSuggestCmd(String.valueOf(event.getSlot())).save()
            .append(String.format("rawSlot=%s ", event.getRawSlot())).setClickAsSuggestCmd(String.valueOf(event.getRawSlot())).save()
            .append(String.format("clickedInvName=%s ", event.getView().getTitle())).setClickAsSuggestCmd(String.valueOf(event.getView().getTitle())).save()
            .append(String.format("clickedItem=%s ", event.getCurrentItem())).setClickAsSuggestCmd(String.valueOf(event.getCurrentItem())).save()
            .append(String.format("inventoryHolder=%s ", event.getClickedInventory().getHolder())).setClickAsSuggestCmd(String.valueOf(event.getClickedInventory().getHolder())).save()
            .send(player);

        }
    }
}
