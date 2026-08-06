package module01.examples;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class ProcessBuilderClass {
    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder(
            "javap",
            "-classpath", System.getProperty("java.class.path"),
            "-c",
            ProcessBuilderClass.class.getName()
        );
        processBuilder.redirectErrorStream(true);
        System.out.println("Getting the class Path: " + System.getProperty("java.class.path"));
        try {
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("javap finished with exit code: " + exitCode);
            }
        } catch (IOException e) {
            System.err.println("Could not start javap: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("The javap process was interrupted.");
        }
    }
}
