package 多线程.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class 组合任务allOf {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*组合异步任务*/
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2);
        // allOf()方法会返回一个新的CompletableFuture对象，该对象会在所有的参数CompletableFuture对象都complete后complete

        while (true) {
            if (combinedFuture.isDone()) {
                System.out.println("all done");
                break;
            }
        }

        // 阻塞等待结果的代码可以用get() 或者 join() 替代
        combinedFuture.get();
        combinedFuture.join();
       /*
        当 CompletableFuture 对象未完成或者发生异常时，get() 方法会抛出一个受检查异常 java.util.concurrent.ExecutionException，它必须要被预处理。
        此外，在等待 CompletableFuture 对象完成结果时，如果线程在等待过程中发生中断，则会抛出一个受检查异常 java.lang.InterruptedException。
        而 join() 方法没有声明任何受检异常，所以它更适合在函数式编程中使用。
        当 CompletableFuture 对象未完成或者发生异常时，join() 方法会抛出一个 unchecked 异常 java.util.concurrent.CompletionException，因此不需要预先处理异常。

        get() 方法是一个阻塞式方法，调用它时如果 CompletableFuture 对象还没有完成，那么get()方法会一直阻塞直到 CompletableFuture 对象成功完成或者抛出异常。
        如果线程在等待过程中被中断，此方法会抛出java.lang.InterruptedException。
        而join()方法是一个非阻塞式方法，如果 CompletableFuture 对象还没有完成，那么它会立即返回并不断地尝试等待 CompletableFuture 对象的计算结果，直到它成功完成或者抛出异常。
        如果线程在等待过程中被中断，则不会抛出任何异常。

        因此，如果您需要等待 CompletableFuture 对象的计算结果并处理异常，可以使用 get() 方法。
        如果您需要等待 CompletableFuture 对象的计算结果并且不需要处理中断或者异常，建议使用 join() 方法。*/

        System.out.println("all done");
    }
}
