package 多线程.ThreadLocal;

public class InheritableThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        threadLocal.set("父类数据:threadLocal");
        inheritableThreadLocal.set("父类数据:inheritableThreadLocal");

        new Thread(() -> {
            System.out.println("子线程获取父类ThreadLocal数据：\t" + threadLocal.get());
            System.out.println("子线程获取父类inheritableThreadLocal数据：\t" + inheritableThreadLocal.get());
        }).start();
    }
}
