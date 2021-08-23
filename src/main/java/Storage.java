import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Deals with loading tasks from the file and saving tasks in the file
 */
public class Storage {

    private String filePath;

    private ArrayList<Task> list;

    /**
     * Constructor for Storage
     * @param filePath path to file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.list = new ArrayList<>();
    }

    /**
     * Saves changes made to file in filePath
     * @throws BiscuitException Unable to save
     */
    public void save() throws BiscuitException {
        try {
            try (PrintWriter printWriter = new PrintWriter(new File(filePath))) {
                list.stream().map(this::convertToCsv).forEach(printWriter::println);
            }
        } catch (IOException e) {
            throw new BiscuitException("Woof! Something went wrong and I was unable to save your most recent change.");
        }
    }

    /**
     * Loads list of tasks from file in file path
     * @return List of files
     * @throws BiscuitException Unable to load save file
     */
    public List<Task> load() throws BiscuitException {
        try {
            File fileToSave = new File(filePath);
            if (!fileToSave.exists()) {
                fileToSave.getParentFile().mkdirs();
                fileToSave.createNewFile();
            }
            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNext()) {
                    list.add(convertToTask(scanner.nextLine()));
                }
            }
        } catch (IOException e) {
            throw new BiscuitException("Woof! Could not create save file to save data to.");
        }
        return list;
    }

    /**
     * Converts task to csv string
     * @param task task to convert
     * @return csv string
     */
    private String convertToCsv(Task task) {
        String csv = task.getType().toString() + "," + task.isDone() + ","
                + escapeSpecialCharacters(task.getDescription());
        switch (task.getType()) {
        case DEADLINE:
            Deadline deadline = (Deadline) task;
            csv += "," + escapeSpecialCharacters(deadline.getDate());
            break;
        case EVENT:
            Event event = (Event) task;
            csv += "," + escapeSpecialCharacters(event.getDate());
            break;
        default:
            break;
        }
        return csv;
    }

    /**
     * Converts csv string to task
     * @param csv csv string to convert
     * @return task
     * @throws BiscuitException Invalid csv string
     */
    private Task convertToTask(String csv) throws BiscuitException {
        try {
            // regex adapted from https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
            // splits by commas, but ignores commas in quotes
            String[] values = csv.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            boolean isDone = Boolean.parseBoolean(values[1]);
            String description = unescapeSpecialCharacters(values[2]);
            switch (Task.Type.valueOf(values[0])) {
            case DEADLINE:
                return new Deadline(isDone, description, values[3]);
            case EVENT:
                return new Event(isDone, description, values[3]);
            case TODO:
                return new ToDo(isDone, description);
            default:
                throw new BiscuitException("Woof! Invalid save data, could not load saved tasks.");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new BiscuitException("Woof! Invalid save data, could not load saved tasks.");
        }
    }

    /**
     * Escapes special characters of commas, quotes and new lines
     * @param data data to escape
     * @return string with special characters escaped
     */
    private String escapeSpecialCharacters(String data) {
        // @@author MarcusTXK--reused
        // Reused from https://www.baeldung.com/java-csv
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    /**
     * Unescapes special characters of commas, quotes and new lines
     * @param data data to unescape
     * @return string with special characters unescaped
     */
    private String unescapeSpecialCharacters(String data) {
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"\"","\"");
            data = data.substring(1, data.length() - 1);
        }
        return data;
    }
}