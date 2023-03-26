package 多线程.线程池;

import java.util.Date;
import java.util.concurrent.*;

public class 线程池的创建与使用 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, // 核心线程数
                10, // 最大线程数
                60, // 线程空闲时间 （当目前线程数大于核心线程数时，多余的空闲的线程的存活时间）
                TimeUnit.SECONDS, // 线程空闲时间单位
                new ArrayBlockingQueue<>(100), // 等待队列,用于存放等待执行的任务
                // ThreadFactory threadFactory 线程工厂,用于创建线程,一般缺省使用默认的即可，也可以使用guava创建一个，用于定义线程的名称等等
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略,也叫做饱和策略，当任务队列满了并且工作线程大于等于线程池的最大线程数时，如何来拒绝请求执行的runnable的策略
                // 拒绝策略 ：  丢弃报异常、丢弃不报异常、丢弃最早的不报异常、和返回给调用方,让调用方线程执行
                    /*
                        线程池处理任务的标准流程是：
                        任务都交给核心线程处理, 多余的就保留在任务队列
                        任务队列满后，才会创建新的线程处理任务
                        不断创建新的线程，直至总线程数也达到最大线程数后,最终执行拒绝策略

                        注意！

                        如果使用的是无界的任务队列,例如LinkedBlockQueue，
                        那么线程池的最大线程数就没有意义了，因为任务队列不会满 ,所以就不会创建除核心线程外的新线程

                        还有一个例外是如果使用的是同步任务队列SynchronousQueue，
                        SynchronousQueue和上面相反，就是直接不存任务
                        当有新任务时，直接交给或创建新的线程执行直到最大线程数，然后多余的任务直接进行拒绝策略，

                        这两种例外情况 本质上不和标准流程的逻辑冲突，
                        只是一个队列是永远存不满，所以不执行后续逻辑
                        一个队列是直接不存，直接执行后续逻辑

                    * */
        );


        // 2. 使用Executor框架工具类Executors来创建
        ExecutorService fixedPoolExecutor = Executors.newFixedThreadPool(5);// 创建固定大小的线程池

    /*
             之所以推荐使用ThreadPoolExecutor的方式创建线程池是因为
             其构造方法中对于线程池的运行规则有更详细的控制,以及显式地说明

            Executor返回的线程池

            FixedThreadPool 和 SingleThreadExecutor ：  固定线程或单线程池
            使用的是无界的 LinkedBlockingQueue，任务队列最大长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致 OOM。

            CachedThreadPool ： 缓存线程池
            使用的是同步队列 SynchronousQueue, 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM。

            ScheduledThreadPool 和 SingleThreadScheduledExecutor :
            使用的无界的延迟阻塞队列DelayedWorkQueue，任务队列最大长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致 OOM。
    */   // 1 使用ThreadPoolExecutor创建线程池（推荐）


        /*使用*/
        // 补充：我们可以在runtime修改线程池的参数,除了任务队列长度  (当然这一点可以自己拓展实现)
        int CORE_POOL_SIZE = 5;
        int MAX_POOL_SIZE = 10;
        int KEEP_ALIVE_TIME = 60;
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaximumPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveTime(KEEP_ALIVE_TIME, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            MyRunnable myRunnable = new MyRunnable("task" + i);
            executor.execute(myRunnable);
        }

        //还可以使用submit方法提交callable任务,其返回一个Future类型的返回值用于获取任务执行结果与其他操作等
        Future<String> submit = executor.submit(() -> {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "the call is done";
        });
        String s = submit.get(10, TimeUnit.SECONDS);// get方法会阻塞当前线程直到任务完成或者超时
        System.out.println(s);
        // Future还可以执行一些其他操作：
//        submit.isCancelled();
//        submit.cancel(true);// 取消任务


        executor.shutdown(); // 关闭线程池,会等待所有任务执行完毕后再关闭
        //  还有shutdownNow, 其立即结束,返回 List<Runnable> 没处理的任务队列


        while (!executor.isTerminated()) {  // isTerminated 判断线程池是否已经执行完所有任务且关闭 ,对应的还有isShutDown，其只检查是否执行了shutdown方法，不管遗留任务是否完成与否
        } //仅保持主线程不退出
        System.out.println("Finished all threads");


    }
}

/**
 * 自定义的简单Runnable类，需要大约1s的执行时间
 */
class MyRunnable implements Runnable {
    private final String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }
}
