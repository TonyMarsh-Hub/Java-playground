package IO流;

import java.io.*;

public class 字节流基本使用 {
    public static void main(String[] args) throws FileNotFoundException {

        /*简单用例*/

        // 输出流
        try (FileOutputStream output = new FileOutputStream("text.txt")) {
            byte[] array = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.".getBytes();
            output.write(array);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输入流
        try (InputStream fis = new FileInputStream("text.txt")) {
            System.out.println("Number of remaining bytes: "
                    + fis.available());
            int content;
            long skip = fis.skip(2);
            System.out.println("The actual number of bytes skipped: " + skip);
            System.out.println("The content read from file: ");
            while ((content = fis.read()) != -1) {
                System.out.print((char) content);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------");


        /*使用缓冲流获取更好的性能*/
        // 比起直接使用FileInputStream,使用BufferedInputStream可以提高读取文件的效率
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("text.txt"))) {
            String result = new String(bufferedInputStream.readAllBytes());
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 虽然很方便，但是readAllBytes()这种方式会将整个文件直接读取到内存中，如果文件过大，会导致内存溢出
        // 更推荐的方式还是 使用 read(byte[] b, int off, int len) 方法,指定每一次读取的字节数
        // 如果是bufferedReader的话，可以使用readLine()方法，每次读取一行，但有些(压缩过的)文件是没有换行的，这种方式还是有局限性的
        // 也还是推荐使用 read(char[])的方法，指定每一次读取的字符数，这样可以避免内存溢出的问题
        System.out.println("------------------------------");


        /*Data流*/
        // 之前的inputStream读取的都是字节或者字符,而DataInputStream可以读取各种类型的数据
        // 其封装了更多的read方法，可以一次读取多个字节，例如readInt()可以一次读取4个字节，readLong()可以一次读取8个字节
        // 但注意，使用dataSteam需要了解输入流中的数据的类型，否则操作中会出现意外的结果,例如数据应该是int类型，但是使用readUTF()读取，就会出现异常

        // 输出流
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("data.txt"))){
            // 输出任意数据类型
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeByte(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 输入流
        try (DataInputStream in = new DataInputStream(new FileInputStream("data.txt"))) {
            System.out.println(in.readBoolean());
            System.out.println(in.readByte());
        } catch (IOException e) {
            // 处理 IO 异常
            e.printStackTrace();
        }

        System.out.println("------------------------------");

        /*Object流*/
        // 用于序列化与反序列化java对象
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("object.txt"))){
            // 序列化
            Pojo test = new Pojo(1);
            objectOutputStream.writeObject(test);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("object.txt"))){
            // 反序列化
            Pojo object = (Pojo) objectInputStream.readObject();
            System.out.println(object);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }
}
