package ru.itis.hyperbot.downladerApp;

import ru.itis.downloader.DownloadTaskInfo;

import java.util.List;

public class GuiDrawerTask implements Runnable{
    private List<DownloadTaskInfo> infos;

    public GuiDrawerTask(List<DownloadTaskInfo> infos) {
        this.infos = infos;
    }

    private boolean hasUnfinished() {
        boolean result = false;
        for(int i = 0; i < infos.size(); i++) {
            if(infos.get(i).getProgress() < 1) {
                result = true;
                break;
            }
        }
        return result;
    }
    @Override
    public void run() {
        int longest = 6;
        for(int i = 0; i < infos.size(); i++) {
            if(infos.get(i).getThreadName().length() > longest) {
                longest = infos.get(i).getThreadName().length();
            }
            if(infos.get(i).getFilename().length() > longest) {
                longest = infos.get(i).getFilename().length();
            }
        }
        for(int i = 0; i < infos.size(); i++) {
            System.out.printf("%" + longest + "s | ", infos.get(i).getThreadName());
        }
        System.out.println();
        for(int i = 0; i < infos.size(); i++) {
            System.out.printf("%" + longest + "s | ", infos.get(i).getFilename());
        }
        System.out.println();
        while (hasUnfinished()) {
            System.out.print("\r");
            for(int i = 0; i < infos.size(); i ++) {
                String t = String.format("%d/%dbytes %5.2f%%", infos.get(i).getDownloaded(), infos.get(i).getFilesize(), infos.get(i).getProgress()*100);
                System.out.printf("%" + longest + "s | ", t);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("can't update gui");
            }
        }
        System.out.print("\r");
        for(int i = 0; i < infos.size(); i ++) {
            String t = String.format("%d/%dbytes %5.2f%%", infos.get(i).getDownloaded(), infos.get(i).getFilesize(), infos.get(i).getProgress()*100);
            System.out.printf("%" + longest + "s | ", t);
        }
    }
}
