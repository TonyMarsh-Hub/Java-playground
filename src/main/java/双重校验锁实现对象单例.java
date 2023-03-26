public class 双重校验锁实现对象单例 {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.function();
    }
}

class Singleton {
    private volatile static Singleton instance;  // 防止指令重排

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) { // 加锁防止多线程下创建多个实例
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void function() {
        System.out.println("function");
    }
}

/*uniqueInstance 采用 volatile 关键字修饰也是很有必要的

uniqueInstance = new Singleton(); 这段代码其实是分为三步执行：
为 uniqueInstance 分配内存空间
初始化 uniqueInstance
将 uniqueInstance 指向分配的内存地址

但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。
指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。
例如，线程 T1 执行了 1 和 3，此时 T2 调用 getUniqueInstance() 后发现 uniqueInstance 不为空，
因此返回 uniqueInstance，但此时 uniqueInstance 还未被初始化。#
 */

/*补充 ，仅使用volatile关键字无法保证原子性，只能保证可见性 具体见 volatile不保证原子性.java*/