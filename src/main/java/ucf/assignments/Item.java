package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class Item {

    public int Id;
    public String description;
    public LocalDate dueDate;
    public boolean checked;
    public Button deleteButton;

    public HelloController controller;

    public Item(HelloController controller)
    {
        this.controller = controller;

        description = "New Item " + controller.newItemNum;
        controller.newItemNum++;

        dueDate = LocalDate.now();
        checked = false;

        // deleteButton = new Button();
    }

    public CheckBox getChecked()
    {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(checked);

        checkBox.selectedProperty().addListener((observable, oldVal, newVal)->
        {
            checked = checkBox.isSelected();
            controller.refreshItems();
            controller.focusItem();
        });

        return checkBox;
    }

    public String getDescription() { return description; }

    public String getDueDate() { return dueDate.toString(); }

    public Button getDeleteButton()
    {
        Button button = new Button();
        button.setText("\uD83D\uDDD1");
        button.setPrefSize(75, 30);

        button.setOnAction(event -> onDeleteButtonClick());

        return button;
    }

    public void onDeleteButtonClick()
    {
        controller.deleteItem(this);
        controller.refreshItems();
        controller.clearFocus();
    }
}
