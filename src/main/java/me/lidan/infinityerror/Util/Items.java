package me.lidan.infinityerror.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

import static me.lidan.infinityerror.Util.Functions.*;

public class Items {

    public static ItemStack INFINITY_GAUNTLET = createItem(getSkull(new ItemStack(Material.SKULL_ITEM), "c2286827" +
                        "-71ca-4bb9-a7c4-4f30e5ba6832",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGRkODhiMmYxMWRkY2FkYmJhNDkwMzI2YTBiMWFiNDg3OGQ1M2RiYmIyNTViNzA0NjA0NzNkMmVhYmMwYzY4MiJ9fX0="), "§6Infinity Gauntlet",
        new ArrayList<>(Arrays.asList(ChatColor.GRAY + "This Item is so error it will break your game",ChatColor.RED +
                "This is " +
                "ERROR!")));
    public static ItemStack SPACE_STONE = createItem(getSkull(new ItemStack(Material.SKULL_ITEM), "d76c75f3-3bfd-4eb1" +
                    "-8c0b" +
                    "-1eaa77d6f61c", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU1MmY3OTYwZmYzY2VjMmY1MTlhNjM1MzY0OGM2ZTMzYmM1MWUxMzFjYzgwOTE3Y2YxMzA4MWRlY2JmZjI0ZCJ9fX0="), "§bSpace Stone",
            new ArrayList<>(Arrays.asList(ChatColor.GRAY + "This Stone is taken from space",ChatColor.GOLD + "Abilities" +
                    ":")));

    public static Logger LOGGER = LogManager.getLogger();


    public Items(){


    }
}
