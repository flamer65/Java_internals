package module01.examples;

public class ReversePrint {
    public static void main(String[] args){
        if(args.length != 3){
            System.out.println("Please enter 3 arguments");
            return;
        }
        System.out.println(args[2] + " " + args[1] + " " + args[0]);
    }
}
