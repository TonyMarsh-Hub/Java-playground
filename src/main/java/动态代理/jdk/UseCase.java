package 动态代理.jdk;

import 动态代理.jdk.代理业务.DebugInvocationHandler;
import 动态代理.jdk.原始业务.SmsService;
import 动态代理.jdk.原始业务.SmsServiceImpl;

import java.lang.reflect.Proxy;

public class UseCase {
    public static void main(String[] args) {

        SmsService origin = new SmsServiceImpl();
        // Proxy.newProxyInstance -> 生成代理对象
        SmsService proxy = (SmsService) Proxy.newProxyInstance(
                origin.getClass().getClassLoader(),  // 类加载器用于动态创建代理类
                origin.getClass().getInterfaces(),  // 由于jdk动态代理是基于接口的，所以这里需要传入interfaces
                new DebugInvocationHandler(origin) // 代理逻辑
        );
        proxy.send("hello world");

/* 执行流程如下
1. 客户端调用代理对象的目标方法。   -> proxy.send("hello world");
2. Proxy类接收到方法调用并将调用转发给InvocationHandler接口。
3. InvocationHandler根据代理对象和方法参数等信息，调用目标对象上的方法，并可以添加其他的逻辑处理。
4. InvocationHandler将目标对象返回的结果返回给Proxy，并返回给客户端。客户端接收到真正的结果。*/
    }
}
