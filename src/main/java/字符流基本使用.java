import java.io.*;
import java.nio.charset.StandardCharsets;

public class 字符流基本使用 {
    public static void main(String[] args) {

//        1. 使用 try-with-resources 语句自动关闭流，避免资源内存泄漏。
//        2. 选择适当的字符编码，以避免乱码等问题。通常情况下，UTF-8 是一个不错的选择。
//        3. 选择适当的缓存大小。通常情况下，缓存大小为 8KB 左右可以满足大部分读取需求。
//        4. 使用 BufferedReader 这个字符缓冲流，可以提高读取文件的效率。

        // InputStreamReader是 字节流到字符流的桥梁，用于将字节流转换为字符流
        // 之所以不直接用FileReader而是用下面转换流的方式，是因为FileReader无法指定字符集，其默认使用的是平台默认的字符集
//        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("multilingual.txt"))){ } catch (IOException e) { }
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream("multilingual.txt"), StandardCharsets.UTF_8))) {
            char[] buffer = new char[8192];
            int len;
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = reader.read(buffer)) != -1) {
                // 处理读取的数据
                // 可以将字符数组转换为字符串继续处理
                stringBuilder.append(buffer, 0, len);
                // 仅做为示例，把所有内容放在一个String里在数据量大的时候显然不是一个好主意
            }
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            // 处理异常
        }

        try (Writer output = new FileWriter("multilingual.txt",true)) {
            output.write(System.lineSeparator());
            output.write("مرحبا! أنا نور. أنا من بيروت. كيف حالك؟");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
