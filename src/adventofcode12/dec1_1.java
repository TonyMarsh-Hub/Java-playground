package adventofcode12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class dec1_1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("src/adventofcode12/input1_1.txt"));
        Integer integer = Arrays.stream(input.split("\n\n"))
                .map(
                        block -> Arrays.stream(block.split("\n"))
                                .mapToInt(Integer::parseInt)
                                .sum()
//                ).max().ifPresent(System.out::println);
                ).sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(Integer::sum).get();
        System.out.println(integer);
    }
}