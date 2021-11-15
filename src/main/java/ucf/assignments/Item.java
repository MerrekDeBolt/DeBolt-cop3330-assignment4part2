package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class Item {

    public String description;
    public LocalDate dueDate;
    public boolean checked;
    public Button deleteButton;

    public Item()
    {
        // description = new SimpleStringProperty("New Item");
        // description = "New Item";

        deleteButton = new Button();
    }

    public CheckBox getChecked()
    {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(checked);
        return checkBox;
    }

    public String getDescription() { return description; }
    // public void setDescription(String desc) { description.setValue(desc); }

    public String getDueDate() { return dueDate.toString(); }

    public Button getDeleteButton()
    {
        Button button = new Button();
        button.setText("\uD83D\uDDD1");
        button.setPrefSize(75, 30);
        return button;
    }

    // public get

    /*
    @Override
    public String toString() {
        return Description;
    }
    */
}
