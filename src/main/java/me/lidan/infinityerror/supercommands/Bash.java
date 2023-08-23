package me.lidan.infinityerror.supercommands;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Bash {

    public static final String OS = System.getProperty("os.name");

    @Getter
    private CommandSender sender;

    @Getter
    private static Bash instance;

    @Getter
    @Setter
    private String URL;

    public Bash(CommandSender sender) {
        this.sender = sender;
        instance = this;
    }

    public void bash(String[] args) {
        Scanner input = new Scanner(System.in);
        String command = "";


    }

    public void useDownloader(String[] split_command) {
        sender.sendMessage("Using Bundled Downloader");
        if (split_command.length < 3) {
            sender.sendMessage("use download [file] [url]");
            return;
        }
        String path = split_command[1];
        String url = split_command[2];
        if (downloadFile(path, url)) {
            sender.sendMessage("Download successfully!");
        } else {
            sender.sendMessage("Download failed!");
        }
    }


    public static boolean downloadFile(String filename, String url){
        try {
            Files.copy(new URL(url).openStream(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int sendPOSTMessage(String url, String message) throws IOException {
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        // Step 3: Create a Message object and convert it to a JSON string
        Gson gson = new Gson();
        WebhookMessage webhookMessage = new WebhookMessage(message);
        String json = gson.toJson(webhookMessage);

        // Step 4: Send the JSON data
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        // Step 5: Check the response
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        return responseCode;
    }

    public static int sendPOSTFile(String url, String filename) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("curl", "-X", "POST", "-F", "file=@" + filename, url);
        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return process.exitValue();
    }
}
