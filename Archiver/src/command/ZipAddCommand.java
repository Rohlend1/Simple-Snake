package command;

import exception.PathIsNotFoundException;
import main.ConsoleHelper;
import main.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand{
    @Override
    public void execute()throws Exception{
        try{
        ConsoleHelper.writeMessage("Какой файл добавить?");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Куда добавить");
        Path add = Paths.get(ConsoleHelper.readString());
        zipFileManager.addFile(add);
        ConsoleHelper.writeMessage("Добавление в архив завершено.");
        }
        catch (PathIsNotFoundException e){
            ConsoleHelper.writeMessage("Неверный путь.");
        }

    }
}
