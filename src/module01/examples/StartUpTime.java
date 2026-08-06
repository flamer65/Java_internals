package module01.examples;

public class StartUpTime {
    public static void main(String[] args) {long startTime = System.nanoTime();

        System.out.println("Program started.");

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Startup time: " + elapsedTime + " nanoseconds");
        System.out.println("Startup time: " + (elapsedTime / 1_000_000.0) + " milliseconds");
    }
}
