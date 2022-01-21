package me.lidan.infinityerror.Util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import me.lidan.infinityerror.Events.ChatMessage;
import me.lidan.infinityerror.Infinityerror;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Functions {
    public static void setName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);

    }
    public static ItemStack getSkull(ItemStack itemStack, String id, String value) {
        ItemStack item = new ItemStack(itemStack.getType(), itemStack.getAmount(), (short)3);
        item.setItemMeta(itemStack.getItemMeta());
        if (!value.isEmpty()) {
            NBTItem nbt = new NBTItem(item);
            NBTCompound skull = nbt.addCompound("SkullOwner");
            skull.setString("Id", id);
            NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
            texture.setString("Value", value);
            nbt.applyNBT(item);
        }
        return item;

    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public static void setArmorColor(ItemStack item, Color color) {
        if (item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS) {
            LeatherArmorMeta leatherArmorMeta = item.hasItemMeta() ? (LeatherArmorMeta)item.getItemMeta() : (LeatherArmorMeta) Bukkit.getItemFactory().getItemMeta(item.getType());
            leatherArmorMeta.setColor(color);
            item.setItemMeta(leatherArmorMeta);
        }
    }

    public static String getNumberFormat(Object num) {
        if (!(num instanceof Number) && !(num instanceof String)) return "";
        String output = num + "";
        output = output.replaceAll("(?<=\\d)(?=(\\d\\d\\d)+(?!\\d))", ",");
        return output;
    }

    public static void setLore(ItemStack item, ArrayList<String> name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(name);
        item.setItemMeta(itemMeta);
    }

    public static ArrayList<Entity> loopEntities(Location center, int size) {
        List<Entity> entities = center.getWorld().getEntities();
        ArrayList<Entity> entity = new ArrayList<>();

        for(Entity value : entities) {
            if (center.distance(value.getLocation()) <= (double)size) {
                entity.add(value);
            }
        }

        return entity;
    }

    public static Location getCenter(Location loc) {
        loc.setX(Math.floor((double)loc.getBlockX()) + 0.5D);
        loc.setY(Math.floor((double)loc.getBlockY()) + 0.5D);
        loc.setZ(Math.floor((double)loc.getBlockZ()) + 0.5D);
        return loc;
    }

    public static ArrayList<Block> loopBlocksCube(Location center, int size) {
        ArrayList<Block> blocks = new ArrayList<>();
        int X = center.getBlockX();
        int Y = center.getBlockY();
        int Z = center.getBlockZ();

        for(int x = X - size; x < X + size; ++x) {
            for(int y = Y - size; y < Y + size; ++y) {
                for(int z = Z - size; z < Z + size; ++z) {
                    blocks.add(center.getWorld().getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

    public static String setTitleCase(String text) {
        if (text != null && !text.isEmpty()) {
            StringBuilder converted = new StringBuilder();
            boolean convertNext = true;
            char[] var3 = text.toCharArray();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                char ch = var3[var5];
                if (Character.isSpaceChar(ch)) {
                    convertNext = true;
                } else if (convertNext) {
                    ch = Character.toTitleCase(ch);
                    convertNext = false;
                } else {
                    ch = Character.toLowerCase(ch);
                }

                converted.append(ch);
            }

            return converted.toString();
        } else {
            return text;
        }
    }

    public static ArrayList<String> loreBuilder(String lore, int num) {
        String[] lores = lore.split(" ");
        int l = 0;
        int n = 0;
        ArrayList<String> list = new ArrayList();
        String[] var6 = lores;
        int var7 = lores.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String str = var6[var8];
            if (str.equals("RESET_LENGTH")) {
                l = 0;
            } else if (str.equals("NEW_LINE")) {
                list.add(ChatColor.GRAY + "");
                l = 0;
                ++n;
            } else {
                l += str.length() + 1;

                try {
                    list.set(n, (String)list.get(n) + str + " ");
                } catch (IndexOutOfBoundsException var11) {
                    list.add(ChatColor.GRAY + "");
                    list.set(n, (String)list.get(n) + str + " ");
                }

                if (l > num) {
                    l = 0;
                    ++n;
                }
            }
        }

        return list;
    }

    public static ArrayList<String> loreBuilder(String lore, ChatColor color) {
        String[] lores = lore.split(" ");
        int l = 0;
        int n = 0;
        ArrayList<String> list = new ArrayList();
        String[] var6 = lores;
        int var7 = lores.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String str = var6[var8];
            if (str.equals("NEW_LINE")) {
                list.add("");
                l = 0;
                ++n;
            } else {
                l += str.length() + 1;

                try {
                    list.set(n, (String)list.get(n) + str + " ");
                } catch (IndexOutOfBoundsException var11) {
                    list.add(color + "");
                    list.set(n, (String)list.get(n) + str + " ");
                }

                if (l > 30) {
                    l = 0;
                    ++n;
                }
            }
        }

        return list;
    }

    public static ArrayList<String> loreBuilder(String lore) {
        return loreBuilder(lore, ChatColor.GRAY);
    }

    public static boolean isSolid(Block b) {
        Material t = b.getType();
        if (t != Material.AIR && t != Material.TORCH && t != Material.LONG_GRASS && t != Material.FENCE && t != Material.SKULL && t != Material.SIGN && t != Material.SIGN_POST && t != Material.WALL_SIGN && t != Material.STONE_BUTTON && t != Material.WOOD_BUTTON && t != Material.IRON_FENCE && t != Material.VINE && t != Material.LADDER && t != Material.DEAD_BUSH && t != Material.THIN_GLASS && t != Material.STAINED_GLASS_PANE && t != Material.CARPET && t != Material.WATER && t != Material.WATER_LILY && t != Material.STATIONARY_WATER && t != Material.LAVA && t != Material.STATIONARY_LAVA && t != Material.WEB && t != Material.GOLD_PLATE && t != Material.IRON_PLATE && t != Material.STONE_PLATE && t != Material.WOOD_PLATE && t != Material.TRIPWIRE && t != Material.STRING && t != Material.TRIPWIRE_HOOK && t != Material.DARK_OAK_DOOR && t != Material.ACACIA_DOOR && t != Material.BIRCH_DOOR && t != Material.IRON_DOOR && t != Material.JUNGLE_DOOR && t != Material.SPRUCE_DOOR && t != Material.TRAP_DOOR && t != Material.WOOD_DOOR && t != Material.WOODEN_DOOR && t != Material.IRON_TRAPDOOR && t != Material.SNOW) {
            return t.isSolid() ? true : true;
        } else {
            return false;
        }
    }

    public static void hideEntity(Entity entity, Player player) {
        try {
            ProtocolManager manager = ProtocolLibrary.getProtocolManager();
            PacketContainer packet = manager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
            packet.getIntegers().write(0, 1);
            int[] id = new int[]{entity.getEntityId()};
            packet.getIntegerArrays().write(0, id);
            manager.sendServerPacket(player, packet);
        } catch (InvocationTargetException var5) {
            var5.printStackTrace();
        }
    }

    public static void sendPacketItem(int slot, Player player, ItemStack item) {
        try {
            ProtocolManager manager = ProtocolLibrary.getProtocolManager();
            PacketContainer packet = manager.createPacket(PacketType.Play.Server.SET_SLOT);
            packet.getIntegers().write(0, 0);
            packet.getIntegers().write(1, slot + 36);
            packet.getItemModifier().write(0, item);
            manager.sendServerPacket(player, packet);
        } catch (InvocationTargetException var5) {
            var5.printStackTrace();
        }

    }

    public static void sendPacketItem(int slot, Player player, ItemStack itemStack, Material material) {
        ItemStack item = itemStack.clone();
        item.setType(material);
        sendPacketItem(slot, player, item);
    }

    public static void sendPacketItem(final int slot, final Player player, final ItemStack item, final int ticks) {
        final ItemStack itemStack = player.getInventory().getItem(slot);
        (new BukkitRunnable() {
            private int loop = 0;

            public void run() {
                if (this.loop >= ticks) {
                    Functions.sendPacketItem(slot, player, itemStack);
                    this.cancel();
                }

                Functions.sendPacketItem(slot, player, item);
                ++this.loop;
            }
        }).runTaskTimer(Infinityerror.getInstance(), (long)slot, 1L);
    }

    public static void sendPacketItem(final int slot, final Player player, final ItemStack itemStack, Material material, final int ticks) {
        final ItemStack item = itemStack.clone();
        item.setType(material);
        (new BukkitRunnable() {
            private int loop = 0;
            
            public void run() {
                if (this.loop >= ticks) {
                    Functions.sendPacketItem(slot, player, itemStack);
                    this.cancel();
                }

                Functions.sendPacketItem(slot, player, item);
                ++this.loop;
            }
        }).runTaskTimer(Infinityerror.getInstance(), (long)slot, 1L);
    }

    public static void setItem(final ItemStack item1, Material material, Long wait) {
        final Material material1 = item1.clone().getType();
        item1.setType(material);
        (new BukkitRunnable() {
            @EventHandler
            public void run() {
                item1.setType(material1);
            }
        }).runTaskLater(Infinityerror.getInstance(), wait);
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1.0D);
    }

    public static void particleLine(Location loc1, Location loc2, Particle particle) {
        particleLine(loc1, loc2, particle, 0.0D, 0.0D, 0.0D);
    }

    public static void particleLine(Location loc1, Location loc2, Particle particle, double red, double green, double blue) {
        double DISTANCE = loc1.distance(loc2) * 5.0D;
        double t = 0.0D;

        for(int i = 0; (double)i < DISTANCE; ++i) {
            t += 0.05D;
            Vector direction = new Vector(loc2.getX() - loc1.getX(), loc2.getY() - loc1.getY(), loc2.getZ() - loc1.getZ());
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5D;
            double z = direction.getZ() * t;
            loc1.add(x, y, z);
            loc1.getWorld().spawnParticle(particle, loc1, 0, red, green, blue);
            loc1.subtract(x, y, z);
        }

    }

    public static String getLocation(Location location) {
        double x = Math.floor(location.getX() * 100.0D) / 100.0D;
        double y = Math.floor(location.getY() * 100.0D) / 100.0D;
        double z = Math.floor(location.getZ() * 100.0D) / 100.0D;
        return x + "," + y + "," + z;
    }

    public static boolean chanceOf(double percent) {
        double chance = randomDouble(0.0D, 100.0D);
        return chance <= percent;
    }

    public static String getItemName(ItemStack item) {
        try {
            ItemMeta meta = item.getItemMeta();
            return meta.getDisplayName();
        } catch (NullPointerException var2) {
            return item.getType().toString();
        }
    }

    public static ItemStack createItem(ItemStack item, String name, ArrayList<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, int data, String name, ArrayList<String> lore) {
        ItemStack item = new ItemStack(material, 1, (short)data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name) {
        return createItem(material, 0, "", new ArrayList());
    }

    public static ItemStack createItem(Material material, int data, String name) {
        return createItem(material, (short)data, "", new ArrayList());
    }

    public static ItemStack createItem(Material material, String name, ArrayList<String> lore) {
        return createItem(material, 0, name, lore);
    }

    public static void openSign(Player player, ArrayList<String> strings) {
        (new SignMenu(Infinityerror.getInstance())).open(player.getUniqueId(), new String[]{"&a&lThis", "&e&lis", "&d&lan", "&b" +
                "&lexample!"}, (player1, text) -> {
            Arrays.stream(text).forEach(new Consumer<String>() {
                public void accept(String t) {
                    if (!t.equals("")) {
                        strings.set(0, t);
                        System.out.println((String)strings.get(0));
                    }

                }
            });
        });
    }

    public static void chatInput(Player p, String message) {
        ChatMessage.players.put(p, 1L);
        ChatMessage.lastinput.put(p, "");
        p.closeInventory();
        p.sendMessage(message);
    }

}
