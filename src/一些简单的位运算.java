public class 一些简单的位运算 {

    public static void main(String[] args) {

        System.out.println("左移运算符 <<");
        int i = -1;
        System.out.println("初始数据对应的二进制字符串： " + Integer.toBinaryString(i));
        i <<= 10;
        System.out.println("左移 10 位后的数据对应的二进制字符 " + Integer.toBinaryString(i));

        i <<= 42;
        System.out.println("再左移42位，由于左移位数大于等于 32 位操作时，会先求余（%）后再进行左移操作，所以下面的代码左移 42 位相当于左移 10 位（42%32=10）");
        System.out.println("左移 10 位后的数据对应的二进制字符 " + Integer.toBinaryString(i));
        System.out.println("____________");
        // >> :带符号右移，向右移若干位，高位补符号位，低位丢弃。正数高位补 0,负数高位补 1。x >> 1,相当于 x 除以 2。
        //>>> :无符号右移，忽略符号位，空位都以 0 补齐。
        i= -114514;
        System.out.println("初始数据对应的二进制字符串： " + Integer.toBinaryString(i));
        i >>= 10;
        System.out.println("右移 10 位后的数据对应的二进制字符 " + Integer.toBinaryString(i));
        System.out.println("____________");
        i=-114514;
        System.out.println("初始数据对应的二进制字符串： " + Integer.toBinaryString(i));
        i>>>= 10;
        System.out.println("无符号右移 10 位后的数据对应的二进制字符 " + Integer.toBinaryString(i) + "    (前面的0省略了)");
        System.out.println("____________");
        System.out.println("普通右移与无符号右移的区别在于，普通右移会保留符号位，而无符号右移会将符号位也当作数据位一起右移");
        System.out.println("即无符号右移的高位补 0，而普通右移的高位补原来的符号位");

    }
}
