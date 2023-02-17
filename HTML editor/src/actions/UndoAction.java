package actions;

import controller.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
    private final View view;
    public UndoAction(View view){
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        view.undo();
    }
}
