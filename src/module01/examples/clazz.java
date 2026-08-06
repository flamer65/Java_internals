package module01.examples;
public class clazz {
    public static void main(String[] args){
       System.out.println(AnotherClass.arg());

    }
}

class AnotherClass{
    public static String arg(){
        return "Hello this is Another Class";
    }
}
