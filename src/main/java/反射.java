import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class 反射 {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //获取Class对象
        Class<?> targetClass1 = Integer.class; //直接用类名.class 的方式

        // 但在多数情况下，我们需要获得的class对象是运行时才能确定的，这时候就需要用到Class.forName()方法通过类的全限定名获取,而类的全限定名会从配置文件、注解、数据库等地方获取
        Class<?> targetClass2 = Class.forName("java.lang.Integer"); // 通过类的全限定名获取

        // 另一种常用的方法是通过对象的getClass()方法获取
        Integer target = 1;
        Class<?> targetClass3 = target.getClass();

        // 还有一种是通过类加载器ClassLoader的loadClass()方法获取，注意这种方式返回的class对象是没有初始化的，(类中的静态代码块不会执行)
        Class<?> targetClass4 = ClassLoader.getSystemClassLoader().loadClass("java.lang.Integer");// 通过类加载器获取

/*
在 Java 反射中，有两种获取 class 对象的方法：get 和 getDeclared。它们之间的主要区别如下：
• get 方法是获取 public 访问权限的字段、方法或构造器，而 getDeclared 方法可以获取所有访问权限的字段、方法或构造器。
• get 方法只能获取当前类及其父类的 public 字段、方法或构造器，而 getDeclared 方法可以获取当前类中定义的所有字段、方法或构造器，包括 private 和 protected。
• get 方法会对其父类进行继承的检查，如果父类中没有该字段、方法或构造器，则会返回 null，而 getDeclared 方法则不会进行继承的检查，如果当前类中没有该字段、方法或构造器，则会抛出 NoSuchFieldException、NoSuchMethodException 或 NoSuchConstructorException 异常。
因此，根据需要，可以选择不同的方法来获取 class 对象中的字段、方法或构造器。如果需要获取所有访问权限的字段、方法或构造器，可以使用 getDeclared 方法；如果只需要获取 public 访问权限的字段、方法或构造器，可以使用 get 方法。
*/
        Object o = targetClass4.getConstructor().newInstance(); //获取对象
        Method[] methods = targetClass4.getMethods();
        Method[] declaredMethods = targetClass4.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            Object invoke = declaredMethod.invoke(o);
        }

        //还有很多操作演示了，基本上做什么都可以


    }
}
