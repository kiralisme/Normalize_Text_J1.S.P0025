package view;

import java.util.List;

public class TextView {
    public void displayResult(List<String> lines) {
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("Normalize successful.");
    }
}
