import java.util.*;
import java.util.stream.Collectors;

public class Person {
    private final Integer age;

    public Person(Integer age){
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public static void main(String[] args) {
        TreeMap<Person, String> treeMap = new TreeMap<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getAge() - o1.getAge();
            }
        });

        treeMap.put(new Person(3), "person1");
        treeMap.put(new Person(18), "person2");
        treeMap.put(new Person(35), "person3");
        treeMap.put(new Person(16), "person4");
        treeMap.forEach((key, value) -> System.out.println(value));

        List<Integer> test = List.of(1, 3, 4, 5, 6, 7, 8, 8, 9);
        List<Integer> noDup = new HashSet<Integer>(test).stream().toList();
        noDup.forEach(System.out::println);


        String [] s= new String[]{
                "dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
        };
        List<String> list = Arrays.asList(s);
        Collections.reverse(list);
        //没有指定类型的话会报错
//        s=list.toArray(new String[0]);



    }
}
