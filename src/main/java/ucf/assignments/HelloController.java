package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {

    @FXML private Label welcomeText;
    @FXML private CheckBox completeCheckBox;
    @FXML private ListView todoListView;
    @FXML private ListView itemsListView;
    @FXML private DatePicker datePicker;
    @FXML private TextField titleBox;
    @FXML private TextArea descBox;

    ArrayList<ToDoList> toDoLists = new ArrayList<>();

    int currentListIndex;
    int currentItemIndex;

    @FXML
    protected void initialize()
    {
        currentListIndex = -1;
        currentItemIndex = -1;

        refreshToDoList();

        datePicker.valueProperty().addListener((observable, oldDate, newDate)->
        {
            toDoLists.get(currentListIndex).Items.get(currentItemIndex).DueDate = datePicker.getValue();
            refreshItems();
            welcomeText.setText(toDoLists.get(currentListIndex).Items.get(currentItemIndex).DueDate.toString());
        });
    }

    private void refreshToDoList()
    {
        ObservableList<ToDoList> content = FXCollections.observableArrayList();
        for (int index = 0; index < toDoLists.size(); index++)
            content.add(toDoLists.get(index));

        todoListView.setItems(content);
    }

    private void refreshItems()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < toDoLists.get(currentListIndex).Items.size(); index++)
            content.add(toDoLists.get(currentListIndex).Items.get(index));

        itemsListView.setItems(content);
    }

    @FXML protected void toDoListClick()
    {
        ToDoList selectedList = (ToDoList) todoListView.getSelectionModel().getSelectedItem();

        // Set title
        titleBox.setText(selectedList.Name);

        // Fills ListView with Items
        currentListIndex = todoListView.getSelectionModel().getSelectedIndex();
        refreshItems();
    }

    @FXML protected void onAddListClick()
    {
        ToDoList list = new ToDoList();
        list.Name = "New List";
        toDoLists.add(list);

        refreshToDoList();
    }

    @FXML protected void onDeleteListClick()
    {

        refreshToDoList();
    }

    @FXML protected void itemListClick()
    {
        Item selectedItem = (Item) itemsListView.getSelectionModel().getSelectedItem();

        // Set description
        descBox.setText(selectedItem.Description);

        // Set due date
        datePicker.setValue(selectedItem.DueDate);

        // Set check box
        completeCheckBox.setSelected(selectedItem.Checked);

        currentItemIndex = itemsListView.getSelectionModel().getSelectedIndex();
        // refreshItems();
    }

    @FXML protected void onTitleBoxChanged()
    {
        toDoLists.get(currentListIndex).Name = titleBox.getText();
        refreshToDoList();
    }

    @FXML protected void onDescBoxChanged()
    {
        toDoLists.get(currentListIndex).Items.get(currentItemIndex).Description = descBox.getText();
        refreshItems();
    }

    @FXML protected void onCompleteCheck()
    {
        toDoLists.get(currentListIndex).Items.get(currentItemIndex).Checked = completeCheckBox.isSelected();
        refreshItems();
    }

    @FXML protected void onAddItemClick()
    {
        Item item = new Item();
        item.Description = "New Item";
        item.DueDate = LocalDate.now();
        item.Checked = false;

        toDoLists.get(currentListIndex).Items.add(item);

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
        ToDoList tempList = (ToDoList) todoListView.getSelectionModel().getSelectedItem();

        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < tempList.Items.size(); index++)
            if (tempList.Items.get(index).Checked)
                content.add(tempList.Items.get(index));

        itemsListView.setItems(content);
    }
    @FXML protected void showUnchecked()
    {
        ToDoList tempList = (ToDoList) todoListView.getSelectionModel().getSelectedItem();

        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < tempList.Items.size(); index++)
            if (!tempList.Items.get(index).Checked)
                content.add(tempList.Items.get(index));

        itemsListView.setItems(content);
    }
}