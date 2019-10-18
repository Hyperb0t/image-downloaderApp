package ru.itis.hyperbot.downladerApp;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class AppArgs {
    @Parameter(names = "--count")
    private int count;
    @Parameter(names = "--mode")
    private String mode;
    @Parameter(names = "--files")
    private String files;
    @Parameter(names = "--folder")
    private String folder;

    public int getCount() {
        return count;
    }

    public String getMode() {
        return mode;
    }

    public String getFiles() {
        return files;
    }

    public String getFolder() {
        return folder;
    }

    public AppArgs() {
    }
}
