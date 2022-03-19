package me.lidan.infinityerror.Commands;

import me.lidan.infinityerror.Infinityerror;
import org.apache.logging.log4j.LogManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class FileFind implements CommandExecutor {
    public JavaPlugin plugin = Infinityerror.getInstance();
    public FileConfiguration config = Infinityerror.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        String search = String.join(" ", args);;
        searchFiles(sender, search);
        return true;
    }

    public void searchFiles(CommandSender sender, String search) {
        // Flags
        boolean lower = false;
        String folderPath = "";
        if (search.contains(" --folder")){ // Folder flag
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
        else {
            folderPath = config.getString("Find_file_default_path");
        }
        if (search.contains(" --lower")){ // Lower flag
            search = search.replace(" --lower","");
            search = search.toLowerCase();
            lower = true;
        }

        if (search.contains("--")){ // Not Implemented flag
            sender.sendMessage("[FileFind] WARN found unsolved flags!");
        }

        final File folder = new File(folderPath);
        String finalSearch = search;
        boolean finalLower = lower;
        searchFiles(sender, folder, finalSearch, finalLower);
    }

    public void searchFiles(CommandSender sender, File folder, String finalSearch, boolean finalLower) {
        if (!folder.exists() || !folder.isDirectory()){
            sender.sendMessage(String.format("Folder %s not", folder.getPath()));
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {

                try {
                    /*Logger logger = Logger.getLogger("FileFindLog");
                    FileHandler fh = new FileHandler("");*/
                    sender.sendMessage(String.format("[FileFind] Searching for %s in folder %s", finalSearch, folder.getPath()));
                    int count = 0;
                    File[] files = folder.listFiles();
                    assert files != null;
                    for (final File fileEntry : files) {
                        // LOGGER.info("now reading file {}", fileEntry.getName());
                        BufferedReader reader = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
                        int lines = 1;
                        String s;
                        while ((s = reader.readLine()) != null) {
                            if (finalLower) s = s.toLowerCase();
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
