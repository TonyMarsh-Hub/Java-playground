package 多线程.ThreadLocal;

import java.lang.reflect.Field;

public class ThreadLocalTest {
    /*在 Java 9 和更高版本中,反射的setAccessible方法的使用受到限制，要运行这段代码需要在模块声明文件中使用opens命令并添加jvm的启动命令*/
    // 我懒得这么做了，另个一可行的方式是直接切换jdk版本为8

    /* 这段代码的结果会是 线程2的ThreadLocalMap中的key被GC后为null，但其对应的def的值仍然是可获取的(没有被回收) */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Thread t = new Thread(() -> test("abc", false));
        t.start();
        t.join();
        System.out.println("--gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
    }

    private static void test(String s, boolean isGC) {
        try {
            new ThreadLocal<>().set(s);
            /*
             *  如果这段代码修改为以下形式，有一个显示的强应用指向ThreadLocal，则不会出现上述的问题
             *
             *  ThreadLocal<String> tl = new ThreadLocal<>();
             *  tl.set(s);
             *
             *  这引申到的一个流行的面试题是 既然ThreadLocal是弱引用，那么在 ThreadLocal.get()的时候发生了GC，是否就获取不到value了
             *  答案是不一定
             *  这种题目的回答重心不在于ThreadLocal，而是弱引用本身, 一个对象若 *只有* 弱引用指向他才会被gc
             *  而不是只要有弱引用指向的就会被GC, 如果ThreadLocal有一个强引用关系，那么自然不会被gc
             * */
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object ThreadLocalMap = field.get(t);
            Class<?> tlmClass = ThreadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.printf("弱引用key:%s,值:%s%n", referenceField.get(o), valueField.get(o));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
