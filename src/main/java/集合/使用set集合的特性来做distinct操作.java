package 集合;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class 使用set集合的特性来做distinct操作 {
    public static void main(String[] args) {
        List<Integer> test = List.of(1, 3, 4, 5, 6, 6, 6, 6, 8, 8, 9);
        List<Integer> noDup = new HashSet<>(test).stream().toList();
        noDup.forEach(System.out::println); //

        // 也可以使用stream流的distinct方法
        List<Integer> integers = test.stream().distinct().toList();


        /*注意，避免使用 List 的 contains() 进行遍历去重或者判断包含操作，效率很低
        因为set底层是hash结构，使用hashcode的比较操作复杂度接近O(1)
        而list的contains方法需要一个一个地遍历 */
        ArrayList<Integer> newList = new ArrayList<>();
        for (Integer integer : test) {
            if(!newList.contains(integer)){
                newList.add(integer); // 避免这种方式做distinct
            }
        }
    }
}
