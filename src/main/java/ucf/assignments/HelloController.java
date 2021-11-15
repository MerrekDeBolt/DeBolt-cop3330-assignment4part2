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

    ArrayList<Item> items = new ArrayList<>();

    int currentListIndex;
    int currentItemIndex;

    @FXML
    protected void initialize()
    {
        currentListIndex = -1;
        currentItemIndex = -1;

        // doneColumn.setCellValueFactory(new PropertyValueFactory<Item, boolean>("Checked"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("DueDate"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<Item, Button>("DeleteButton"));

        datePicker.valueProperty().addListener((observable, oldDate, newDate)->
        {
            items.get(currentItemIndex).DueDate = datePicker.getValue();
            refreshItems();
            welcomeText.setText(items.get(currentItemIndex).DueDate.toString());
        });
    }

    private void refreshItems()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < items.size(); index++)
            content.add(items.get(index));

        tableView.setItems(content);
    }

    @FXML protected void itemListClick()
    {
        Item selectedItem = (Item) tableView.getSelectionModel().getSelectedItem();

        // Set description
        descBox.setText(selectedItem.Description.getValue());

        // Set due date
        datePicker.setValue(selectedItem.DueDate);

        // Set check box
        completeCheckBox.setSelected(selectedItem.Checked);

        currentItemIndex = tableView.getSelectionModel().getSelectedIndex();
        // refreshItems();
    }

    @FXML protected void onDescBoxChanged()
    {
        items.get(currentItemIndex).Description.setValue(descBox.getText());
        refreshItems();
    }

    @FXML protected void onCompleteCheck()
    {
        items.get(currentItemIndex).Checked = completeCheckBox.isSelected();
        refreshItems();
    }

    @FXML protected void onAddItemClick()
    {
        Item item = new Item();
        // item.Description.setValue("New Item");
        item.DueDate = LocalDate.now();
        item.Checked = false;

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
            if (items.get(index).Checked)
                content.add(items.get(index));

        tableView.setItems(content);
    }
    @FXML protected void showUnchecked()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < items.size(); index++)
            if (!items.get(index).Checked)
                content.add(items.get(index));

        tableView.setItems(content);
    }
}