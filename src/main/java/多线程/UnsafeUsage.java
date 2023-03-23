package 多线程;

import pojo.Person;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;

public class UnsafeUsage {
    public static void main(String[] args) {
        // 以下方法使用到了DirectByteBuffer类，其中的allocate就是用Unsafe类的allocateMemory方法实现的
        // 不能直接访问Unsafe类，这是刻意设计的，因为其方法的使用不当会导致内存泄漏
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        // 正如其名，unsafe是不安全的,其可以直接操作内存，使用不当会导致内存泄漏
        Unsafe unsafe = Unsafe.getUnsafe();
        long l = unsafe.allocateMemory(1024);
        unsafe.freeMemory(l);
    }
}
