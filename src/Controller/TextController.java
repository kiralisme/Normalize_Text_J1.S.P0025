package controller;

import model.TextModel;
import view.TextView;
import java.util.List;

public class TextController {
    public static void main(String[] args) {
        TextModel model = new TextModel();
        TextView view = new TextView();

        List<String> processedLines = model.processTextData();
        view.displayResult(processedLines);
        model.processAndWriteToFile();
    }
}
