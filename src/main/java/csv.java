import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class csv {
static File csvFile = new File("output.csv");

    public static void main(String[] args) {
        /*List<String[]> dataLines = new ArrayList<>();
        //Open existing CSV file  
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile,true))) {
            pw.println("New session started at: " + LocalDateTime.now());
            System.out.println("CSV file written successfully: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }*/
    }
    public static void log_to_csv(String str) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true))) {
            pw.println(str + " at " + LocalDateTime.now());
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

}