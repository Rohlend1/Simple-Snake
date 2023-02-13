package main;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final Path rootPath;
    private final List<Path> fileList;
    public FileManager(Path rootPath) throws IOException{
        this.rootPath = rootPath;
        fileList = new ArrayList<>();
        collectFileList(rootPath);
    }
    public List<Path> getFileList() {
        return fileList;
    }
    private void collectFileList(Path path) throws IOException{
        if(Files.isRegularFile(path)){
            fileList.add(rootPath.relativize(path));
        }
        else if(Files.isDirectory(path)){
            try(DirectoryStream<Path> dir = Files.newDirectoryStream(path)){
                for (Path value : dir) {
                    collectFileList(value);
                }
            }
        }
    }
}
