package 集合;

import java.util.*;

import pojo.Person;

public class 为treemap指定排序规则 {
    public static void main(String[] args) {


/*
        Treemap的key需要是可比较的，如果传入的key本身是comparable的话就不用额外操作了，
        否则的话，一种无侵入式的方法是在treemap的构造器中传递key的比较器
        就如下面这个代码示例所示

        补充：如果key本身是comparable的，那么缺省treemap按照升序排列，
        如果想要降序排列，还是需要在构造器中传递一个新的比较器，用于定义排序规则
*/
        TreeMap<Person, String> treeMap = new TreeMap<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.age() - o1.age(); //按年龄，降序排序
            }
        });  // 传递comparator的操作可以用lambda简化，IDEA应该会有提示,此处为演示没做简化

        treeMap.put(new Person(3), "person1");
        treeMap.put(new Person(18), "person2");
        treeMap.put(new Person(35), "person3");
        treeMap.put(new Person(16), "person4");

        treeMap.forEach((key, value) -> System.out.println(value + ": " + key.age() ));


    }
}
