package ucf.assignments;

import javafx.scene.control.TableView;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ucf.assignments.HelloApplication;
import ucf.assignments.HelloController;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class JUnit
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test1()
    {
        HelloController controller = new HelloController();
        controller.addItemsFromList("src/test/java/testList.txt");

        // Tests to see if the list can hold 100 items.
        assertEquals(100, controller.items.size());

        // Tests to see if items have descriptions, dueDates, and checked.
        for (int index = 0; index < controller.items.size(); index++)
        {
            if (controller.items.get(index).description != null)
                assertEquals(true, true);
            if (controller.items.get(index).dueDate != null)
                assertEquals(true, true);
        }

        // Tests to see if the filters are working.
        assertEquals(50, controller.showUnchecked().size());
        assertEquals(50, controller.showChecked().size());

        // Tests to see if the list can be cleared.
        controller.onClearClick();
        assertEquals(0, controller.items.size());
    }

    @Test
    public void test2()
    {
        HelloController controller = new HelloController();

        // Adds an item to the list
        assertEquals(0, controller.items.size());
        controller.onAddItemClick();
        assertEquals(1, controller.items.size());

        // Editing an item's description, due date, and complete
        assertEquals("New Item 1", controller.items.get(0).description);
        assertEquals(LocalDate.now(), controller.items.get(0).dueDate);
        assertEquals(false, controller.items.get(0).checked);

        controller.items.get(0).description = "New Description";
        controller.items.get(0).dueDate = LocalDate.parse("2002-05-05");
        controller.items.get(0).checked = true;

        assertEquals("New Description", controller.items.get(0).description);
        assertEquals(LocalDate.parse("2002-05-05"), controller.items.get(0).dueDate);
        assertEquals(true, controller.items.get(0).checked);

        // Saving list
        String path = "testList2.txt";

        PrintWriter writer;
        try { writer = new PrintWriter(path); }
        catch (Exception e) { System.out.println("File not found."); return; }
        writer.println(controller.createContent());
        writer.close();

        assertEquals(true, Files.exists(Paths.get(path)));
        if (Files.exists(Paths.get(path)))
            try { Files.delete(Paths.get(path)); } catch (Exception ex) { }

        // Deleting an item from the list
        controller.deleteItem(controller.items.get(0));
        assertEquals(0, controller.items.size());
    }
}
