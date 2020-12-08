package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;

public class FactoryView {

    //Design Pattern Factory: creation des Text, Textfield, Button, Label
    public static Text getText(String txt)
    {
        return new Text(txt);
    }

    public static TextField getTextField()
    {
        return new TextField();
    }

    public static Button getButton(String button)
    {
        return new Button(button);
    }

    public static Label getLabel(String label)
    {
        return new Label(label);
    }

}
