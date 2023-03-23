package IO流;

import java.io.*;

public class 其他流的基本使用 {
    public static void main(String[] args) {
        /*打印流*/
        System.out.println("Hello World!"); //最常见的一种,打印到系统输出
        try (
                // 创建一个文件输出流
                FileOutputStream fileOutputStream = new FileOutputStream("printStream.txt");
                // 将文件输出流封装成打印流
                PrintStream printStream = new PrintStream(fileOutputStream);
        ) {

            // 使用打印流输出数据
            printStream.println("Hello, World!");
            printStream.println("This is a Java PrintStream example.");
            printStream.printf("PI is approximately %.5f%n", Math.PI);
            System.out.println("Data has been written to the file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
/*补充，PrintSteam 和 PrintWrite
1. 输出数据类型不同
PrintStream主要输出字节数据，而PrintWriter主要输出字符数据。
1. 输出位置不同
PrintStream的输出位置可以是标准输出流(System.out)，文件输出流(FileOutputStream)， 标准错误流(System.err)等。而PrintWriter的输出位置只能是字符输出流，例如FileWriter等。
1. 处理方式不同
PrintStream在输出数据时，处理方式是通过平台的默认字符编码将数据转换成字节流输出。而PrintWriter在输出数据时，处理方式是直接输出字符流。
1. 抛出异常方式不同
PrintWriter继承自Writer，所以在输出时可能抛出IOException异常，需要进行处理。而PrintStream继承自OutputStream，输出时不会抛出IOException异常，而是直接转换为RuntimeException或Error异常。
综上所述，PrintStream适合输出字节数据，而PrintWriter适合输出字符数据。如果需要输出到文件时，可以使用PrintWriter，而如果需要输出到控制台或其他字节流时，可以使用PrintStream。 */


        /*随机访问流 : 支持随机跳转到文件的任意位置进行读写*/

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File("RandomAccess.txt"), "rw")) {
            // mode : 即读写模式， r只读、 rw读写
            // rws: 相对于 rw，rws  同步更新 对“文件的内容”或“元数据”的修改到外部存储设备。
            // rwd : 相对于 rw，rwd 同步更新 对“文件的内容”的修改到外部存储设备。
            // 文件内容指的是文件中实际保存的数据，元数据则是用来描述文件属性比如文件的大小信息、创建和修改时间。

          /*RandomAccessFile 中有一个文件指针用来表示下一个将要被写入或者读取的字节所处的位置。
            可以通过 RandomAccessFile 的 seek(long pos) 方法来设置文件指针的偏移量（距文件开头 pos 个字节处）。
            如果想要获取文件指针当前的位置的话，可以使用 getFilePointer() 方法。 */

            System.out.println("读取之前的偏移量：" + randomAccessFile.getFilePointer() + ",当前读取到的字符" + (char) randomAccessFile.read() + "，读取之后的偏移量：" + randomAccessFile.getFilePointer());
            // 指针当前偏移量为 6
            randomAccessFile.seek(6);
            System.out.println("读取之前的偏移量：" + randomAccessFile.getFilePointer() + ",当前读取到的字符" + (char) randomAccessFile.read() + "，读取之后的偏移量：" + randomAccessFile.getFilePointer());
            // 从偏移量 7 的位置开始往后写入字节数据
            randomAccessFile.write(new byte[]{'H', 'I', 'J', 'K'});
            // 指针当前偏移量为 0，回到起始位置
            randomAccessFile.seek(0);
            System.out.println("读取之前的偏移量：" + randomAccessFile.getFilePointer() + ",当前读取到的字符" + (char) randomAccessFile.read() + "，读取之后的偏移量：" + randomAccessFile.getFilePointer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
