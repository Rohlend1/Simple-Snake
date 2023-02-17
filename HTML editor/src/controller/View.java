package controller;

import listeners.FrameListener;
import listeners.TabbedPaneChangeListener;
import listeners.UndoListener;
import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JTextPane htmlTextPane = new JTextPane();
    private final JEditorPane plainTextPane = new JEditorPane();
    private final UndoManager undoManager = new UndoManager();
    private final UndoListener undoListener = new UndoListener(undoManager);



    public void undo(){
        try{
            undoManager.undo();
        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public void redo(){
        try{
            undoManager.redo();
        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public boolean isHtmlTabSelected(){
       return tabbedPane.getSelectedIndex() == 0;
    }
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }
    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }
    public void showAbout(){
        JOptionPane.showMessageDialog(this,"Важная информация","Очень",JOptionPane.INFORMATION_MESSAGE);
    }
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Новый" -> controller.createNewDocument();
            case "Открыть" -> controller.openDocument();
            case "Сохранить" ->controller.saveDocument();
            case "Сохранить как..." -> controller.saveDocumentAs();
            case "Выход" -> controller.exit();
            case "О программе" -> this.showAbout();
        }
    }
    public void exit(){
        controller.exit();
    }
    public void init(){
        initGui();
        FrameListener frameListener = new FrameListener(this);
        addWindowListener(frameListener);
        setVisible(true);
    }
    public void initMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        MenuHelper.initFileMenu(this,jMenuBar);
        MenuHelper.initEditMenu(this,jMenuBar);
        MenuHelper.initStyleMenu(this,jMenuBar);
        MenuHelper.initAlignMenu(this,jMenuBar);
        MenuHelper.initColorMenu(this,jMenuBar);
        MenuHelper.initFontMenu(this,jMenuBar);
        MenuHelper.initHelpMenu(this,jMenuBar);
        getContentPane().add(jMenuBar,BorderLayout.NORTH);
    }
    public View(){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public boolean canUndo(){
        return undoManager.canUndo();

    }
    public boolean canRedo(){
        return undoManager.canRedo();
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }
    public void initEditor(){
        htmlTextPane.setContentType("text/html");
        JScrollPane jScrollPane1 = new JScrollPane(htmlTextPane);
        tabbedPane.add("HTML",jScrollPane1);
        JScrollPane jScrollPane2 = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст",jScrollPane2);
        TabbedPaneChangeListener tabbedPaneChangeListener = new TabbedPaneChangeListener(this);
        tabbedPane.setPreferredSize(new Dimension(300,300));
        tabbedPane.addChangeListener(tabbedPaneChangeListener);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }
    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }
    public void selectedTabChanged(){
        int ind = tabbedPane.getSelectedIndex();
        if(ind == 0){
            controller.setPlainText(plainTextPane.getText());
        }
        else if(ind == 1){
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }

}
