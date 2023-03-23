package 集合;

import java.util.*;

public class List与Array的互相转换 {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // 将数组转为list  -> Steam流
        List<Integer> list = Arrays.stream(data).boxed().toList();
        // 但注意，此时的List是不可变的
        list.add(1);
        // 如要需要一个可变list，那么还要做一个wrap操作 ,任意List实现类都可以,这里用Arraylist演示
        ArrayList<Integer> newlist = new ArrayList<>(list);
        newlist.add(3);

        // list转array直接用ArrayList. toArray()即可
        Object[] objects = newlist.toArray();
        // toArray无参方法返回的是Object[]类型，如果需要指定类型，可以使用toArray((T[] a)方法
        Integer[] integers = newlist.toArray(new Integer[0]); // 因为只是为了指定类型，所以长度为0即可

    }
}
