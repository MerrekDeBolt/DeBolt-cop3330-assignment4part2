package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

import java.time.LocalDate;

public class Item {

    public StringProperty Description;
    public LocalDate DueDate;
    public boolean Checked;
    public Button DeleteButton;

    public Item()
    {
        Description = new SimpleStringProperty("New Item");

        DeleteButton = new Button();
    }

    // public get

    /*
    @Override
    public String toString() {
        return Description;
    }
    */
}
