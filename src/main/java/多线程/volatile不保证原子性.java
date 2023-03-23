package 多线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class volatile不保证原子性 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        VolatileAtomicityDemo volatileAtomicityDemo = new VolatileAtomicityDemo();
        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 500; j++) {
                    volatileAtomicityDemo.increase();
                }
            });
        }
        // 等待1.5秒，保证上面程序执行完成
        Thread.sleep(1500);
        System.out.println("count = " + VolatileAtomicityDemo.count);
        threadPool.shutdown();

        // 以上操作的结果不一定每次都是2500 (根据具体环境不同，结果可能会有差异)，因为volatile不保证原子性

        /*具体原因是因为 increase中的 Count++操作其实分为了三个步骤
        * 1. 取值 （从主内存）
        * 2. 自增
        * 3. 存值 （到主内存）
        * 这三个操作的原子性是 是无法仅通过volatile关键字来保证的
        * 有可能在3执行前，有其他线程执行了123，这样原线程执行3之后就覆盖了一次操作，最终的结果就少了一次increase
        *
        * 如要保证原子性，可以
        * 1. 使用synchronized关键字,同步increase方法
        * 2. 或者将count改为AtomicInteger类型
        * 3. 使用ReentrantLock,在方法内手动加锁
        * */


    }

}

class VolatileAtomicityDemo {
    public  volatile static int count = 0;

    public void increase() {
        count++;
    }

}
