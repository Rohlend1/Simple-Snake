package controller;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    private final View view;
    private HTMLDocument document;
    private File currentFile;

    public HTMLDocument getDocument() {
        return document;
    }
    public void createNewDocument(){
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("Новый редактор");
        currentFile = null;

    }
    public void openDocument(){
        view.selectHtmlTab();
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new HTMLFileFilter());
        if(jFileChooser.showOpenDialog(view)==JFileChooser.APPROVE_OPTION){
            currentFile = jFileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            resetDocument();
            try(FileReader fileReader = new FileReader(currentFile)){
                new HTMLEditorKit().read(fileReader,document,0);
                view.resetUndo();
            }
            catch (Exception e){
                ExceptionHandler.log(e);
            }
        }
    }
    public void saveDocument(){
        view.selectHtmlTab();

        if(currentFile!=null) {
            view.setTitle(currentFile.getName());
            try (FileWriter fileWriter = new FileWriter(currentFile)){
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
        else saveDocumentAs();
    }
    public void saveDocumentAs(){
        view.selectHtmlTab();
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new HTMLFileFilter());
        if(jFileChooser.showSaveDialog(view)==JFileChooser.APPROVE_OPTION){
            currentFile = jFileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            try(FileWriter fileWriter = new FileWriter(currentFile)){
                new HTMLEditorKit().write(fileWriter,document,0, document.getLength());
            }
            catch (Exception e){
                ExceptionHandler.log(e);
            }

        }

    }


    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }
    public Controller(View view){
        this.view = view;

    }
    public void init(){
        createNewDocument();
    }
    public void exit(){
        System.exit(0);
    }
    public void resetDocument() {

        if (document != null) {
            document.removeUndoableEditListener(view.getUndoListener());

        }
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }
    public void setPlainText(String text) {
        resetDocument();
        StringReader stringReader = new StringReader(text);
        try {
            new HTMLEditorKit().read(stringReader, document, 0);
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
    }
    public String getPlainText(){
        StringWriter stringWriter = new StringWriter();
        try{
            new HTMLEditorKit().write(stringWriter,document,0, document.getLength());

        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
        return stringWriter.toString();
    }

}
