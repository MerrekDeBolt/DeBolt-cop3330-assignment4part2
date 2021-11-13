package ucf.assignments;

import java.util.ArrayList;

public class ToDoList {

    public String Name;
    public ArrayList<Item> Items;

    @Override
    public String toString() {
        return Name;
    }

    public ToDoList()
    {
        Items = new ArrayList<>();
    }
}
