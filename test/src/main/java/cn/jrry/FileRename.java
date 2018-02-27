package cn.jrry;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileFilter;

public class FileRename {
    public static void main(String[] args) {
        final String suffix = args[1];
        long start = System.currentTimeMillis();

        File dir = new File(args[0]);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(suffix);
                }
            });
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && !files[i].isHidden()){
                    // System.out.println(files[i].getName());
                    files[i].renameTo(new File(files[i].getParent(),String.valueOf(start+i).concat(suffix)));
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("process time: %d",end-start));
    }
}
