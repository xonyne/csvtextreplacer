package csvtextreplacer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVTextReplacer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String fileNameInput="yoga_anmeldungen.csv";
        String fileNameOutput="yoga_anmeldungen_neu.csv";
        
        Path file = Paths.get(System.getProperty("user.dir")).resolve("data").resolve(fileNameInput);
        
        if (!Files.exists(file)) {
            throw new FileNotFoundException(file.toAbsolutePath().toString());
        }
        
        System.out.println("Reading file: " + file);
        
        FileReader reader = new FileReader(file.toFile());
        
        StringBuilder builder = new StringBuilder();
        
        int data = reader.read();
        
        boolean inValueString=false;
        
        while (data != -1) {
            char convertedToChar = (char)data;
            // check if we are inside a value string
            if (convertedToChar == '"') {
                inValueString = !inValueString;
            }
            // check if there is a comma inside a value string and replace it
            if (inValueString && convertedToChar == ',') {
                convertedToChar = ';';
            }
            // check if there is a CARRIAGE RETURN character inside a value string and replace it
            if (inValueString && convertedToChar == '\r') {
                System.out.println("Found a \\r somewhere");
               convertedToChar = ' ';
            }
            // check if there is a NEWLINE character inside a value string and replace it
            if (inValueString && convertedToChar == '\n') {
                System.out.println("Found a \\n somewhere");
                convertedToChar = ' ';
            }
            builder.append(convertedToChar);
            data = reader.read();
        }
        
        reader.close();
        
        //String theData = builder.toString().replace("\r\n", " ").replace("\n", " ");
        
        //
        File fileNew = Paths.get(System.getProperty("user.dir")).resolve("data").resolve(fileNameOutput).toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNew))) {
        writer.write(builder.toString());
        }
    }
    
}
