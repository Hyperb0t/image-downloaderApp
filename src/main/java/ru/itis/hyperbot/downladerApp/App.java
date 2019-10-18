package ru.itis.hyperbot.downladerApp;

import com.beust.jcommander.JCommander;
import ru.itis.downloader.ImageDownloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AppArgs appArgs = new AppArgs();
        JCommander.newBuilder().addObject(appArgs).build().parse(args);
        ImageDownloader imageDownloader = null;
        if (appArgs.getFolder() == null) {
            System.out.println("download folder not specified");
            System.exit(0);
        }
        if (appArgs.getFiles() == null) {
            System.out.println("no files to download");
            System.exit(0);
        }
        if (appArgs.getCount() == 0) {
            System.out.println("number of threads not specified");
            System.exit(0);
        } else {
            try {
                int threadCount = (appArgs.getMode().equals("single-thread")) ? 1 : appArgs.getCount();
                imageDownloader = ImageDownloader.create("postgres", "cbhma8hq", "jdbc:postgresql://127.0.0.1/image-downloader", threadCount);
            } catch (SQLException e) {
                System.out.println("can't connect to database");
                System.exit(0);
            }
            String[] urls = appArgs.getFiles().split(";");
            for (int i = 0; i < urls.length; i++) {
                try {
                    URL t = new URL(urls[i]);
                    imageDownloader.load(t, appArgs.getFolder());
                } catch (MalformedURLException e) {
                    System.out.println("url with index " + i + " is bad and file won't be loaded");
                }
            }
            Thread gui = new Thread(new GuiDrawerTask(imageDownloader.getInfos()));
            gui.start();
            imageDownloader.shutdown();
        }

    }
}
