package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextModel {
    public List<String> processTextData() {
        List<String> processedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String processedLine = formatText(line);
                if (!processedLine.isEmpty()) {
                    processedLines.add(processedLine);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Can't not found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return processedLines;
    }
    public void processAndWriteToFile() {
        List<String> processedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
             PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")))) {

            String line;
            while ((line = br.readLine()) != null) {
                String processedLine = formatText(line);
                if (!processedLine.isEmpty()) {
                    processedLines.add(processedLine);
                }
            }

            // Ghi tài liệu đã chuẩn hóa vào tệp đầu ra
            for (String processedLine : processedLines) {
                pw.println(processedLine);
            }

            System.out.println("Normalize successful.");
        } catch (FileNotFoundException ex) {
            System.err.println("Can't not found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private String formatText(String line) {
        line = formatOneSpace(line);
        line = formatSpecialCharacters(line);
        line = afterDotUpperCase(line);
        line = noSpaceQuotes(line);
        line = firstUpercase(line);
        line = lastAddDot(line);
        return line;
    }

    private String formatOneSpace(String line) {
        // Thực hiện các phương thức xử lý dữ liệu ở đây
        line = line.toLowerCase();
        line = line.replaceAll("\\s+", " ");
        line = formatOneSpaceSpecial(line, ".");
        line = formatOneSpaceSpecial(line, ",");
        line = formatOneSpaceSpecial(line, ":");
        line = formatOneSpaceSpecial(line, "\"");
        return line.trim();
    }

    private String formatOneSpaceSpecial(String line, String character) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] strings = line.split("\\s*\\" + character + "\\s*");
        for (String oneWord : strings) {
            stringBuffer.append(oneWord + " " + character);
            stringBuffer.append(" ");
        }
        return stringBuffer.toString().trim().substring(0, stringBuffer.length() - 3);
    }

    private String formatSpecialCharacters(String line) {
        StringBuffer stringBuffer = new StringBuffer(line);
        for (int i = 0; i < stringBuffer.length() - 1; i++) {
            if (stringBuffer.charAt(i) == ' '
                    && (stringBuffer.charAt(i + 1) == '.'
                    || stringBuffer.charAt(i + 1) == ','
                    || stringBuffer.charAt(i + 1) == ':')) {
                stringBuffer.deleteCharAt(i);
            }
        }
        return stringBuffer.toString().trim();
    }

    private String afterDotUpperCase(String line) {
        StringBuffer stringBuffer = new StringBuffer(line);
        int lengthLine = stringBuffer.length();
        for (int i = 0; i < lengthLine - 2; i++) {
            if (stringBuffer.charAt(i) == '.') {
                char afterDot = stringBuffer.charAt(i + 2);
                stringBuffer.setCharAt(i + 2, Character.toUpperCase(afterDot));
            }
        }
        return stringBuffer.toString().trim();
    }

    int countQuotes = 0;

    private String noSpaceQuotes(String line) {
        StringBuffer stringBuffer = new StringBuffer(line);
        for (int i = 0; i < stringBuffer.length(); i++) {
            if (stringBuffer.charAt(i) == '"' && countQuotes % 2 == 0) {
                stringBuffer.deleteCharAt(i + 1);
                countQuotes++;
            } else if (stringBuffer.charAt(i) == '"' && countQuotes % 2 == 1
                    && i != 0) {
                stringBuffer.deleteCharAt(i - 1);
                countQuotes++;
            }
        }
        return stringBuffer.toString().trim();
    }

    private String firstUpercase(String line) {
        StringBuilder stringBuffer = new StringBuilder(line);
        for (int i = 0; i < stringBuffer.length(); i++) {
            if (Character.isLetter(stringBuffer.charAt(i))) {
                stringBuffer.setCharAt(i, Character.toUpperCase(stringBuffer.charAt(i)));
                break;
            }
        }
        return stringBuffer.toString().trim();
    }

    private String lastAddDot(String line) {
        if (line.endsWith(".")) {
            return line;
        } else {
            return line + ".";
        }
    }

    private boolean isLineEmpty(String line) {
        return line.trim().isEmpty();
    }

    public int countLines() {
        int countLine = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!isLineEmpty(line)) {
                    countLine++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Can't not found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return countLine;
    }
}
