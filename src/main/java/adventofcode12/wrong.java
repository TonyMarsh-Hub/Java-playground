package adventofcode12;

public class wrong {
    public static void main(String[] args) {
        String a = "a";
        System.out.println(a.intern() == a); // true

        String _a = new String("a");
        System.out.println( _a.intern() == _a); // false
    }
}
