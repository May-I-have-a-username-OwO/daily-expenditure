package fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readFiles {
    public static String readText(String filename) {
        String content = "";
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                content += fileScanner.nextLine() + "\n";
            }
            fileScanner.close();

            System.out.println("File '" + filename + "' Read Successfully");
        } catch (FileNotFoundException e) {
            System.out.println("File '" + filename + "' Not Found");
        }
        return content;
    }
}
