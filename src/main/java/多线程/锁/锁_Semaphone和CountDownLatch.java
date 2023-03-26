package 多线程.锁;

import java.util.concurrent.*;

public class 锁_Semaphone和CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        /*Semaphore*/
        // 初始共享资源数量,还可以传递一个boolean表示公平与否
        final Semaphore semaphore = new Semaphore(5);
        // 获取1个许可 , 可以获取多个，但一般没有必要这么做
        semaphore.acquire(); //还有一个相对应的tryAcquire方法，其不会堵塞，若获取失败直接返回false
        // 释放1个许可
        semaphore.release();


        /*Countdown*/
        int threadCount = 550;
        // 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 1; i <= threadCount; i++) {
            final int threadNo = i;
            threadPool.execute(() -> {// Lambda 表达式的运用
                try {
                    test(threadNo);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();// 表示一个请求已经被完成
                }

            });
        }
        countDownLatch.await(); //阻塞，直至CountDownLatch的计数器为0
        // 谨慎使用上述的await方法，其可能导致死锁，因为如果计数器永远不为0，那么就会一直阻塞
        threadPool.shutdown();
        System.out.println("finish");
    }
    public static void test(int threadNo) throws InterruptedException {
        Thread.sleep(1000);// 模拟请求的耗时操作
        System.out.println("threadNo:" + threadNo);
        Thread.sleep(1000);// 模拟请求的耗时操作
    }


}

