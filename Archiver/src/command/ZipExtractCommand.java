package command;

import exception.PathIsNotFoundException;
import main.ConsoleHelper;
import main.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand{
    @Override
    public void execute() throws Exception{
        try{
            ConsoleHelper.writeMessage("Распаковка архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Введите полное имя файла или директории для распаковки.");
            Path path = Paths.get(ConsoleHelper.readString());
            zipFileManager.extractAll(path);
            ConsoleHelper.writeMessage("Архив распакован.");
        }
        catch (PathIsNotFoundException e){
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }
    }
}
