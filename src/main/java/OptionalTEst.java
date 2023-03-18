import java.util.Optional;

public class OptionalTEst {
    public static void main(String[] args) {
        Optional<String> optional = Optional.ofNullable(null);
        String sth = optional.orElse("isnull");
        System.out.println(sth);
    }
}
