package controller;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String str = f.getName().toLowerCase();
        if(f.isDirectory()) return true;
        return (str.endsWith(".html")||str.endsWith(".htm"));
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
