package String;

import java.util.AbstractCollection;

public class StringTest {
    public static void main(String[] args) {

        String a = "a";
        String abc = new String("abc");
        String substring = abc.substring(0, 1);
        System.out.println(substring == a);
        System.out.println(substring.equals(a));
    }
}
