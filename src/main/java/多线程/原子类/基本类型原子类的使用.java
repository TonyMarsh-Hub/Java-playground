package 多线程.原子类;


import java.util.concurrent.atomic.*;

public class 基本类型原子类的使用 {
    public static void main(String[] args) {
        int temp;
        AtomicInteger i = new AtomicInteger(0);
        // 原子类提供的一些方法中，其底层包含多个操作，但是这些操作是原子的，不会被打断

        // 一些特色方法例如 getAndDoSomething()，会先获取原值， 然后对值进行一些更新操作，这在一些多线程的场景下是很有用的
        temp = i.getAndIncrement();
        System.out.println("temp:" + temp + ";  i:" + i); //temp:0;  i:1
        // 当然也有其他方法，这里不一一列举了
        temp = i.incrementAndGet();
        System.out.println("temp:" + temp + ";  i:" + i); //temp:2;  i:2


        /*原子数组*/
        int tempVal;
        int[] nums = {1, 2, 3, 4, 5, 6};
        AtomicIntegerArray aa = new AtomicIntegerArray(nums);
        for (int j = 0; j < nums.length; j++) {
            System.out.print(aa.get(j));
        }
        System.out.println();
        tempVal = aa.getAndSet(0, 2);
        System.out.println("tempVal:" + tempVal + ";  i:" + aa);
        tempVal = aa.getAndIncrement(0);
        System.out.println("tempVal:" + tempVal + ";  i:" + aa);

    }
}



