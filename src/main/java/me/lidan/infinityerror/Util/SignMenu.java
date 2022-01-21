package me.lidan.infinityerror.Util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SignMenu {
    private final Plugin plugin;
    private final Map<UUID, SignMenu.InputReceiver> inputReceivers;

    public SignMenu(Plugin plugin) {
        this.plugin = plugin;
        this.inputReceivers = new ConcurrentHashMap();
        this.listen();
    }

    public void open(UUID uuid, String[] text, SignMenu.InputReceiver inputReceiver) {
        this.inputReceivers.putIfAbsent(uuid, this.display(uuid, inputReceiver, (String[])Arrays.stream(Arrays.copyOf(text, 4)).map((s) -> {
            return ChatColor.translateAlternateColorCodes('&', (String)Optional.ofNullable(s).orElse(""));
        }).toArray((x$0) -> {
            return new String[x$0];
        })));
    }

    public void open(UUID uuid, List<String> text, SignMenu.InputReceiver inputReceiver) {
        this.open(uuid, (String[])text.toArray(new String[text.size()]), inputReceiver);
    }

    public void open(Player player, String[] text, SignMenu.InputReceiver inputReceiver) {
        this.open(player.getUniqueId(), text, inputReceiver);
    }

    public void open(Player player, List<String> text, SignMenu.InputReceiver inputReceiver) {
        this.open(player.getUniqueId(), text, inputReceiver);
    }

    private void listen() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, new PacketType[]{Client.UPDATE_SIGN}) {
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                String[] text = (String[])packet.getStringArrays().read(0);
                if (SignMenu.this.inputReceivers.containsKey(player.getUniqueId())) {
                    event.setCancelled(true);
                    ((SignMenu.InputReceiver)SignMenu.this.inputReceivers.remove(player.getUniqueId())).receive(player, text);
                }
            }
        });
    }

    private SignMenu.InputReceiver display(UUID uuid, SignMenu.InputReceiver inputReceiver, String... text) {
        Player player = Bukkit.getPlayer(uuid);
        Location location = player.getLocation();
        BlockPosition blockPosition = new BlockPosition(location.getBlockX(), 0, location.getBlockZ());
        PacketContainer fakeSign = ProtocolLibrary.getProtocolManager().createPacket(Server.BLOCK_CHANGE);
        PacketContainer openSign = ProtocolLibrary.getProtocolManager().createPacket(Server.OPEN_SIGN_EDITOR);
        PacketContainer signData = ProtocolLibrary.getProtocolManager().createPacket(Server.TILE_ENTITY_DATA);
        fakeSign.getBlockPositionModifier().write(0, blockPosition);
        fakeSign.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));
        openSign.getBlockPositionModifier().write(0, blockPosition);
        NbtCompound signNBT = (NbtCompound)signData.getNbtModifier().read(0);
        IntStream.range(0, text.length).forEach((v) -> {
            signNBT.put("Text" + (v + 1), "{\"extra\":[{\"text\":\"" + text[v] + "\"}],\"text\":\"\"}");
        });
        signData.getBlockPositionModifier().write(0, blockPosition);
        signData.getIntegers().write(0, 9);
        signData.getNbtModifier().write(0, signNBT);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, fakeSign);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, openSign);
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, signData);
        } catch (InvocationTargetException var12) {
            var12.printStackTrace();
        }

        return inputReceiver;
    }

    public interface InputReceiver {
        void receive(Player var1, String[] var2);
    }
}

