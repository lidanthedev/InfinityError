package me.lidan.infinityerror.Commands;

import me.lidan.infinityerror.Infinityerror;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;

public class FileFind implements CommandExecutor {
    public JavaPlugin plugin = Infinityerror.getInstance();
    public static Logger LOGGER = LogManager.getLogger(FileFind.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        String search = String.join(" ", args);;
        searchFiles(sender, search);
        return true;
    }

    public void searchFiles(CommandSender sender, String search) {
        final File folder = new File(plugin.getDataFolder().getParent() + "/Skript" +
                "/scripts");
        if (!folder.exists() || !folder.isDirectory()){
            sender.sendMessage("Folder Skript not found");
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    for (final File fileEntry : folder.listFiles()) {
                        // LOGGER.info("now reading file {}", fileEntry.getName());
                        BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
                        int lines = 1;
                        String s;
                        while ((s = reader.readLine()) != null) {
                            if (s.contains(search)) {
                                s = s.replace(search, ChatColor.RED + search + ChatColor.RESET);
                                sender.sendMessage("[FileFind] " + fileEntry.getName() + " " + s + " at line " + lines);
                                plugin.getLogger().info("[FileFind] " + fileEntry.getName() + " " + s + " at line " + lines);
                                count++;
                            }
                            lines++;
                        }
                        reader.close();
                    }
                    if (count == 0) {
                        sender.sendMessage("[FileFind] couldn't find " + search);
                    }
                    else{
                        sender.sendMessage("[FileFind] Found " + search + " " + count + " times");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
