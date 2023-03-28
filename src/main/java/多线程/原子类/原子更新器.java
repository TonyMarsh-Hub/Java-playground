package 多线程.原子类;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class 原子更新器 {
    public static void main(String[] args) {
/*
        如果需要原子更新某个类里的某个字段时，需要用到对象的属性修改类型原子类。

        AtomicIntegerFieldUpdater:原子更新整形字段的更新器
        AtomicLongFieldUpdater：原子更新长整形字段的更新器
        AtomicReferenceFieldUpdater ：原子更新引用类型里的字段的更新器

        三者的使用方式一致，仅面向的数据类型不同
*/
        // 步骤1 ，构建更新器
        AtomicIntegerFieldUpdater<TestObject> updater = AtomicIntegerFieldUpdater.newUpdater(TestObject.class, "property");
        TestObject testObject = new TestObject(1);
        System.out.println(updater.getAndIncrement(testObject));
        System.out.println(updater.get(testObject));

        // 步骤2， 执行原子化的更新操作
        /*注意，支持原子化更新操作的前提是 ： 目标字段使用public volatile 关键字修饰*/
    }
}

class TestObject {
    public volatile int property;

    public TestObject(int property) {
        this.property = property;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }
}