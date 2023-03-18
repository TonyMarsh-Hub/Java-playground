import java.util.*;

public class List与Array的互相转换 {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // 将数组转为list
        List<Integer> list = Arrays.stream(data).boxed().toList();
        for (Integer integer : list) {
            System.out.println(integer);
        }
        // 注意，此时的List是不可变的

        // 如要需要一个可变list，那么还要做一个wrap操作
        // 任意List实现类都可以,这里用Arraylist演示
        ArrayList<Integer> newlist = new ArrayList<>(list);
        newlist.add(3);
        for (Integer integer : newlist) {
            System.out.println(integer);
        }


        // list转array直接用ArrayList. toArray()即可
        Object[] objects = newlist.toArray();



    }
}
