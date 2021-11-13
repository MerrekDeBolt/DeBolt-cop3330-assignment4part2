package ucf.assignments;

import java.time.LocalDate;

public class Item {

    public String Description;
    public LocalDate DueDate;
    public boolean Checked;

    @Override
    public String toString() {
        return Description;
    }
}
