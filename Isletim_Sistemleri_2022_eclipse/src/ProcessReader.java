import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProcessReader {
    public static List<Process> readProcessesFromFile(String filePath) throws IOException {
        ArrayList<Process> processes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");

                int name =i;
                int priority = Integer.parseInt(parts[1]);
                int burstTime = Integer.parseInt(parts[2]);
                int arrivalTime = Integer.parseInt(parts[0]);
                Process process =new Process(name,priority,burstTime,arrivalTime);;
                i++;
                processes.add(process);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return processes;
    }
}