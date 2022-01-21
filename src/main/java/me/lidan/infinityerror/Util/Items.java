package me.lidan.infinityerror.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

import static me.lidan.infinityerror.Util.Functions.*;

public class Items {

    public static ItemStack INFINITY_GAUNTLET = createItem(Material.GOLD_INGOT, "§6INFINITY GAUNTLET",
            new ArrayList<>(Arrays.asList("This Item is so error it will break your game","This is ERROR!")));
    public static ItemStack SPACE_STONE = createItem(getSkull(new ItemStack(Material.SKULL_ITEM), "d76c75f3-3bfd-4eb1" +
                    "-8c0b" +
                    "-1eaa77d6f61c", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU1MmY3OTYwZmYzY2VjMmY1MTlhNjM1MzY0OGM2ZTMzYmM1MWUxMzFjYzgwOTE3Y2YxMzA4MWRlY2JmZjI0ZCJ9fX0="), "§bSpace Stone",
            new ArrayList<>(Arrays.asList("This Item is so error it will break your game","This is ERROR!")));
    // {display:{Name:"Space Stone"},SkullOwner:{Id:"d76c75f3-3bfd-4eb1-8c0b-1eaa77d6f61c",Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU1MmY3OTYwZmYzY2VjMmY1MTlhNjM1MzY0OGM2ZTMzYmM1MWUxMzFjYzgwOTE3Y2YxMzA4MWRlY2JmZjI0ZCJ9fX0="}]}}}

    public static Logger LOGGER = LogManager.getLogger();


    public Items(){


    }
}
