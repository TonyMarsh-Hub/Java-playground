package 多线程;

import java.util.concurrent.*;

public class CompletableFuture使用 {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟一个耗时的操作
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        // 当异步任务完成时执行给定的动作，即打印异步任务的结果
        future.thenAccept(result -> System.out.println("异步任务结果：" + result));
        System.out.println("主线程继续执行");

        // 等待异步任务完成
        Integer result;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("异步任务结果：" + result);

    }

}

