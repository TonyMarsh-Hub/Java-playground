package 动态代理.jdk.原始业务;

public class SmsServiceImpl implements SmsService {
  public String send(String message){
    System.out.println("send message:"+ message);
    return message;
  }
}
