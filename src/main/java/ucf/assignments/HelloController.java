package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {

    @FXML private Label welcomeText;
    @FXML private TableView tableView;
    @FXML private TableColumn doneColumn;
    @FXML private TableColumn descColumn;
    @FXML private TableColumn dueDateColumn;
    @FXML private TableColumn deleteColumn;
    @FXML private CheckBox completeCheckBox;
    @FXML private DatePicker datePicker;
    @FXML private TextArea descBox;

    ObservableList<Item> items = FXCollections.observableArrayList();

    int currentItemIndex = -1;

    @FXML
    protected void initialize()
    {
        doneColumn.setCellValueFactory(new PropertyValueFactory<Item, CheckBox>("Checked"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("DueDate"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<Item, Button>("DeleteButton"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentItemIndex = tableView.getSelectionModel().selectedIndexProperty().getValue();
            focusItem();
        });

        doneColumn.setStyle( "-fx-alignment: CENTER;");
        descColumn.setStyle( "-fx-alignment: CENTER-LEFT;");
        dueDateColumn.setStyle( "-fx-alignment: CENTER-LEFT;");
        deleteColumn.setStyle( "-fx-alignment: CENTER;");

        datePicker.valueProperty().addListener((observable, oldDate, newDate)->
        {
            items.get(currentItemIndex).dueDate = datePicker.getValue();
            refreshItems();
        });
    }

    private void refreshItems()
    {
        tableView.setItems(items);
        tableView.refresh();
    }

    @FXML protected void focusItem()
    {
        Item selectedItem = items.get(currentItemIndex);

        // Set description
        // descBox.setText(selectedItem.description.getValue());
        descBox.setText(selectedItem.description);

        // Set due date
        datePicker.setValue(selectedItem.dueDate);

        // Set check box
        completeCheckBox.setSelected(selectedItem.checked);

    }

    @FXML protected void onDescBoxChanged()
    {
        // items.get(currentItemIndex).description.setValue(descBox.getText());
        items.get(currentItemIndex).description = descBox.getText();
        refreshItems();
    }

    @FXML protected void onCompleteCheck()
    {
        items.get(currentItemIndex).checked = completeCheckBox.isSelected();
        refreshItems();
    }

    @FXML protected void onAddItemClick()
    {
        Item item = new Item();
        // item.Description.setValue("New Item");
        item.dueDate = LocalDate.now();
        item.checked = false;

        items.add(item);

        refreshItems();
    }

    @FXML protected void onDeleteItemClick()
    {
        refreshItems();
    }

    @FXML protected void showAll()
    {
        refreshItems();
    }
    @FXML protected void showChecked()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < items.size(); index++)
            if (items.get(index).checked)
                content.add(items.get(index));

        tableView.setItems(content);
    }
    @FXML protected void showUnchecked()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < items.size(); index++)
            if (!items.get(index).checked)
                content.add(items.get(index));

        tableView.setItems(content);
    }
}