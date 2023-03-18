import java.math.BigInteger;
import java.util.AbstractCollection;

public class temp {
    public static void main(String[] args) {

        String abcd = "abcd";

        String abcd2 = new StringBuilder().append("abc").append("d").toString();
        System.out.println(abcd2.intern() == abcd2);

    }
}
