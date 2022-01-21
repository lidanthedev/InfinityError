package me.lidan.infinityerror.Events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamage implements Listener {
    public static Logger LOGGER = LogManager.getLogger(OnDamage.class);

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        LOGGER.info("onDamage {} {} {}", e.getDamager(), e.getEntity(), e.getCause());
    }
}
