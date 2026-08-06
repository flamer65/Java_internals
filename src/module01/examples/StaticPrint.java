package module01.examples;

public class StaticPrint {
    static {
        System.out.println("Intializing...");
    }
    public static void main(String[] args) {
        System.out.println("StaticPrint.main");
    }

}
