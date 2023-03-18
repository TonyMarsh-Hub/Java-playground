package 动态代理.jdk.代理业务;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DebugInvocationHandler implements InvocationHandler {
    /**
     * 被代理的对象, 即原始业务类,在invoke中，我们可以通过method.invoke(target, args)调用原始业务类的方法
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("proxy is here doing something before method " + method.getName());
        //调用原始业务类的方法
        Object result = method.invoke(target, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("proxy is here doing something after method " + method.getName());
        return result;
    }
}