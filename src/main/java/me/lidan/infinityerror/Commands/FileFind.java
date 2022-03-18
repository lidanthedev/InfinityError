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
        // flag --lower
        boolean lower = false;
        String folderPath = "";
        if (search.contains(" --folder")){
            String[] splitSearch = search.split(" ");
            boolean foundFolderFlag = false;
            for (String s : splitSearch) {
                if (s.equals("--folder")){
                    foundFolderFlag = true;
                }
                else if (foundFolderFlag){
                    folderPath = s;
                }
            }
            search = search.replace(" --folder", "");
            search = search.replace(String.format(" %s", folderPath), "");
        }
        if (search.contains(" --lower")){
            search = search.replace(" --lower","");
            lower = true;
        }
        else {
            folderPath = plugin.getDataFolder().getParent() + "/Skript" +
                    "/scripts";
        }
        if (search.contains("--")){
            sender.sendMessage("[FileFind] WARN found unsolved flags!");
        }
        final File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()){
            sender.sendMessage(String.format("Folder %s not found", folder.getPath()));
            return;
        }
        String finalSearch = search;
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    sender.sendMessage(String.format("[FileFind] Searching for %s in folder %s", finalSearch, folder.getPath()));
                    int count = 0;
                    for (final File fileEntry : folder.listFiles()) {
                        // LOGGER.info("now reading file {}", fileEntry.getName());
                        BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
                        int lines = 1;
                        String s;
                        while ((s = reader.readLine()) != null) {
                            if (s.contains(finalSearch)) {
                                s = s.replace(finalSearch, ChatColor.RED + finalSearch + ChatColor.RESET);
                                sender.sendMessage("[FileFind] " + fileEntry.getName() + " " + s + " at line " + lines);
                                plugin.getLogger().info("[FileFind] " + fileEntry.getName() + " " + s + " at line " + lines);
                                count++;
                            }
                            lines++;
                        }
                        reader.close();
                    }
                    if (count == 0) {
                        sender.sendMessage("[FileFind] couldn't find " + finalSearch);
                    }
                    else{
                        sender.sendMessage("[FileFind] Found " + finalSearch + " " + count + " times");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
