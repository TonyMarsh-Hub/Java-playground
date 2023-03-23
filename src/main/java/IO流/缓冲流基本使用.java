package IO流;

import org.junit.Test;

import javax.swing.plaf.ViewportUI;
import java.io.*;
import java.lang.invoke.VarHandle;

public class 缓冲流基本使用 {
    public static void main(String[] args) {

/*
         缓冲流在设计模式上采用了装饰者模式，我们可以直接在构造器中传入一个流参数将其做wrapper后使用

         IO 操作是很耗时的，所以有了缓冲流的实现，其将数据先缓存到内存中，然后一次性写入磁盘(或是一次性读取多个字节的数据到缓存区)，这样就减少了IO请求的频率，提高了效率
         注意，缓冲流之所以效率更高的原因是其减少了耗时的IO操作的次数
         因为缓冲流默认带一个8kb的缓冲大小(可以自定义)，所有的操作都是先在缓冲区中执行,只有缓冲区没数据(才读文件) 、满数据(才进行写入) 或者显示用flash操作之后才执行io操作

         但也不是无脑使用缓冲流就有更高的效率，还是看具体的业务场景
         缓冲流本质上只是封装了一层上述的逻辑，如果有些场景中这些做法是非必要的，那么其就不一定带来性能的提升，也就不用刻意使用缓冲流了
         例如下面两个testcase，两者在效率上没有很大的差别
*/

    }

    @Test
    public void copy_pdf_to_another_pdf_buffer_stream() {

        // 设定缓冲区大小为1mb
        int bufferSize = 1000 * 1024;

        // 记录开始时间
        long start = System.currentTimeMillis();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("video.mp4"), bufferSize);
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("video-copy.mp4"), bufferSize)) {
            byte[] buffer = new byte[1000 * 1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("使用缓冲流复制PDF文件总耗时:" + (end - start) + " 毫秒");
    }

    @Test
    public void copy_pdf_to_another_pdf_stream() {
        // 记录开始时间
        long start = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream("video.mp4");
             FileOutputStream fos = new FileOutputStream("video-copy.mp4")) {
            int content;
            int bufferSize = 1000 * 1024;
            byte[] buffer = new byte[bufferSize];
            while ((content = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("使用普通流复制PDF文件总耗时:" + (end - start) + " 毫秒");
    }

}
