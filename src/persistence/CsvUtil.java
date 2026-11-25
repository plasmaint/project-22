package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    // Ensures parent directory  exists before reading/writing
    public static Path ensureFile(String filePath, String headerLine) throws IOException {
        Path path = Paths.get(filePath);
        
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        
        if (Files.notExists(path)) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                if (headerLine != null && !headerLine.isEmpty()) {
                    writer.write(headerLine);
                    writer.newLine();
                }
            }
        }
        
        return path;
    }

    // Writes all rows (including header) to the CSV file
    public static void writeAll(String filePath, String headerLine, List<String[]> rows) throws IOException {
        Path path = ensureFile(filePath, headerLine);
        
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            if (headerLine != null && !headerLine.isEmpty()) {
                writer.write(headerLine);
                writer.newLine();
            }
            
            for (String[] row : rows) {
                writer.write(formatRow(row));
                writer.newLine();
            }
        }
    }

    // Reads all data rows (skips the header row and blank lines)
    public static List<String[]> readAll(String filePath, String defaultHeader) throws IOException {
        Path path = ensureFile(filePath, defaultHeader);
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            boolean isHeader = true;
            
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                records.add(parseLine(line));
            }
        }
        
        return records;
    }

    // Formats fields safely with quotes
    public static String formatRow(String[] fields) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) sb.append(",");
            
            String val = fields[i] == null ? "" : fields[i];
            
            if (val.contains(",") || val.contains("\"") || val.contains("\n")) {
                val = "\"" + val.replace("\"", "\"\"") + "\"";
            }
            sb.append(val);
        }
        
        return sb.toString();
    }

    // Parses a single CSV line respecting double-quoted values
    public static String[] parseLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        
        tokens.add(current.toString());
        return tokens.toArray(new String[0]);
    }
}