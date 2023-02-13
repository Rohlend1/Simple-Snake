package command;

import main.ConsoleHelper;
import main.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand{
    @Override
    public void execute()throws Exception{
        ConsoleHelper.writeMessage("Какой файл удалить?");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Откуда удалить");
        Path del = Paths.get(ConsoleHelper.readString());
        zipFileManager.removeFile(del);
        ConsoleHelper.writeMessage("Удаление из архива завершено.");

    }
}
