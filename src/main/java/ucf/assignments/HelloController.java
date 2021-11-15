package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML private Label welcomeText;
    @FXML public TableView tableView;
    @FXML private TableColumn doneColumn;
    @FXML private TableColumn descColumn;
    @FXML private TableColumn dueDateColumn;
    @FXML private TableColumn deleteColumn;
    @FXML private CheckBox completeCheckBox;
    @FXML private DatePicker datePicker;
    @FXML private TextArea descBox;

    ObservableList<Item> items = FXCollections.observableArrayList();

    // int currentItemIndex = -1;

    int newItemNum = 1;

    @FXML
    protected void initialize()
    {
        doneColumn.setCellValueFactory(new PropertyValueFactory<Item, CheckBox>("Checked"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("DueDate"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<Item, Button>("DeleteButton"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            focusItem();
        });

        doneColumn.setStyle( "-fx-alignment: CENTER;");
        descColumn.setStyle( "-fx-alignment: CENTER-LEFT;");
        dueDateColumn.setStyle( "-fx-alignment: CENTER-LEFT;");
        deleteColumn.setStyle( "-fx-alignment: CENTER;");

        datePicker.valueProperty().addListener((observable, oldDate, newDate)->
        {
            items.get(tableView.getSelectionModel().getSelectedIndex()).dueDate = datePicker.getValue();
            refreshItems();
        });

        // Temporary test
        /*
        Item item;
        for (int index = 0; index < 100; index++)
        {
            item = new Item(this);
            if (index % 2 == 0)
                item.checked = true;
            items.add(item);
        }
        refreshItems();
        */
    }

    public void refreshItems()
    {
        tableView.setItems(items);
        tableView.refresh();
    }

    public void deleteItem(Item item)
    {
        items.remove(item);
    }

    @FXML protected void focusItem()
    {
        // Item selectedItem = items.get(currentItemIndex);
        Item selectedItem = (Item) tableView.getSelectionModel().getSelectedItem();

        // Set description
        descBox.setText(selectedItem.description);

        // Set due date
        datePicker.setValue(selectedItem.dueDate);

        // Set check box
        completeCheckBox.setSelected(selectedItem.checked);
    }

    public void clearFocus()
    {
        if (tableView.getSelectionModel().getSelectedItem() == null)
        {
            descBox.setText("");
            datePicker.setValue(LocalDate.now());
            completeCheckBox.setSelected(false);
        }
    }

    @FXML protected void onDescBoxChanged()
    {
        if (descBox.getText().length() > 256)
        {
            descBox.setText(descBox.getText().substring(0, 256));
            welcomeText.requestFocus();
        }

        items.get(tableView.getSelectionModel().getSelectedIndex()).description = descBox.getText();
        refreshItems();
    }

    @FXML protected void onCompleteCheck()
    {
        items.get(tableView.getSelectionModel().getSelectedIndex()).checked = completeCheckBox.isSelected();
        refreshItems();
    }

    @FXML protected void onAddItemClick()
    {
        Item item = new Item(this);
        items.add(item);
        try { refreshItems(); } catch (Exception ex) { }
    }

    @FXML protected void showAll()
    {
        refreshItems();
    }
    @FXML protected ObservableList<Item> showChecked()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < items.size(); index++)
            if (items.get(index).checked)
                content.add(items.get(index));

        try { tableView.setItems(content); } catch (Exception ex) { }

        return content;
    }
    @FXML protected ObservableList<Item> showUnchecked()
    {
        ObservableList<Item> content = FXCollections.observableArrayList();
        for (int index = 0; index < items.size(); index++)
            if (!items.get(index).checked)
                content.add(items.get(index));

        try { tableView.setItems(content); } catch (Exception ex) { }

        return content;
    }

    @FXML protected void onClearClick()
    {
        items.clear();
        try { refreshItems(); } catch (Exception ex) { }
    }

    @FXML protected void onLoadClick()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage mainStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(mainStage);

        loadList(selectedFile.getPath());
    }

    void loadList(String path)
    {
        addItemsFromList(path);
        refreshItems();
    }

    public void addItemsFromList(String path)
    {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(path), StandardCharsets.US_ASCII); }
        catch (Exception e) { System.out.println("File not found."); return; }
        Item item;
        String line;
        ArrayList<Item> newItems = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++)
        {
            item = new Item(this);
            line = lines.get(i);

            item.description = line.substring(0, line.indexOf(";"));
            line = line.substring(line.indexOf(";") + 1);

            item.dueDate = LocalDate.parse(line.substring(0, line.indexOf(";")));
            line = line.substring(line.indexOf(";") + 1);

            if (line.equals("false"))
                item.checked = false;
            else if (line.equals("true"))
                item.checked = true;

            if (item.description.length() > 256)
                item.description = item.description.substring(0, 256);

            items.add(item);
        }
    }

    @FXML protected void onSaveClick()
    {
        String content = createContent();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage mainStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(mainStage);

        PrintWriter writer;
        try { writer = new PrintWriter(selectedFile); }
        catch (Exception e) { System.out.println("File not found."); return; }

        writer.println(content);
        writer.close();
    }

    public String createContent()
    {
        String content = "";
        for (int index = 0; index < items.size(); index++)
        {
            content += items.get(index).description;
            content += ";";

            content += items.get(index).dueDate.toString();
            content += ";";

            content += items.get(index).checked;

            if (index != items.size() - 1)
                content += "\n";
        }
        return content;
    }
}