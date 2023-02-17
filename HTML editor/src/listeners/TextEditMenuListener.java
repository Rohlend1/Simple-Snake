package listeners;

import controller.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class TextEditMenuListener implements MenuListener {
    private final View view;
    @Override
    public void menuSelected(MenuEvent e) {
        JMenu jMenu = (JMenu) e.getSource();
        Component[] arr = jMenu.getMenuComponents();
        for(Component ar : arr){
            ar.setEnabled(view.isHtmlTabSelected());
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }


    public TextEditMenuListener(View view) {
        this.view = view;
    }
}
