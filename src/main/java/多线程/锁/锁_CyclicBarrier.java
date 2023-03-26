package 多线程.锁;

import java.util.concurrent.*;

public class 锁_CyclicBarrier {
    // 请求的数量
    private static final int threadCount = 30;
    // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(200); // 为了让输出更加明显，让每个线程之间有一定的间隔
            threadPool.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        threadPool.shutdown();
    }

    public static void test(int threadNo) throws InterruptedException, BrokenBarrierException {
        System.out.println("threadNo:" + threadNo + " is ready");
        try {
            // 等待cyclicBarrier的计数器为0，最长等待时间为5秒
            cyclicBarrier.await(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("-----CyclicBarrierException------");
        }
        System.out.println("threadNo:" + threadNo + " is passed");
    }
}
