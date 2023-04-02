package 多线程.CompletableFuture;

import java.util.concurrent.*;

public class 创建方式与简单使用 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*CompletableFuture相较于Future的优势在于，它提供了函数式编程的方法来组合多个异步任务, 以及对任务执行结果的处理*/


        /*一个简单的使用示例*/
        // 可以使用new关键字来手动创建一个CompletableFuture对象，但注意，该方法创建的对象是一个空的CompletableFuture对象
        // 即内部没有任何任务，只是一个空壳，需要手动调用其complete()方法来设置任务的执行结果后(通常是RPC调用结果),才会执行后面的then……逻辑
        CompletableFuture<String> future = new CompletableFuture<>();
        // 注意 complete方法只能被有效执行一次，之后的then操作不会对 future.get()的结果产生影响
        future.complete("收到通知，之前的任务已经完成了 ||  RPC调用收到结果了。 \n");
        // 上面两个步骤可以用completedFuture方法简化为  CompletableFuture<String> future = CompletableFuture.completedFuture("结果");
        future.thenApplyAsync(s -> s + "对结果做一些操作").thenAcceptAsync(System.out::println);

        // 非Async方法会在当前线程执行，Async方法会在新的线程执行
        // 因为上述的执行特点，所以在主线程(使用CompletableFuture的线程)中，使用非Async方法会阻塞后续的所有方法
        // 而使用Async则不会，多个Async方法会在多个线程中执行
        // 同时需要提醒的是，如果使用流式编程(链式编程)的话，一条链中的所有方法都会在同一个线程中执行, 例如上面的代码，即使显示用了Async,但由于在一条链上，所以所有的then方法都会在同一个线程(阻塞)执行

        // apply 和 accept 两个方法不同之处在于前者的参数传递的是一个Function函数式接口，后者的参数传递的是一个Consumer函数式接口
        // 具体来说，两者都接受前一个操作的结果作为参数，而前者在执行后会有一个新的返回值，后者会执行一个无返回值的操作
        // 如果不需要接受前一个任务的执行结果，并执行一个无返回值的操作，那么可以使用thenRun()方法，

        /*另一种CompletableFuture的创建方式*/
        // supplyAsync()是CompletableFuture的一个工厂方法
        // 可以直接从其函数名看出，该方法通过参数提供一个Supplier<T>类型的函数式接口定义要执行的异步任务，并立即执行
        // 其还可以传递一个Executor对象，用于指定任务的执行线程池
        // 还有一个静态工厂方法是runAsync，它的参数是一个Runnable类型的函数式接口，用于定义要执行的异步任务，两者的区别在于前者有返回值，后者没有返回值
        CompletableFuture.supplyAsync(() -> "doing something first")// 异步任务中，什么也没有做只是返回一个字符串对象, return关键字被省略
                .thenApply(s -> s + " and then do something else") // 这里使用了链式编程，即使使用thenApplyAsync，但由于在一条链上，所以所有的then方法都会在同一个线程(阻塞)执行
                .thenAccept(System.out::println);


    }

}

