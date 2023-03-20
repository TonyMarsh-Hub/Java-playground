public class 枚举 {

    // 枚举类用于定义一组固定的常量,这些常量在形式上就是枚举类的final对象实例
    // 枚举类的对象的实例化时间是在类加载时，且不向外提供构造器，所以枚举类是无法在外部实例化的

    // 枚举类是一个语法糖 ,enum只是一个关键字，而不是一个新的数据类型
    // 编译后的字节码文件中，枚举类的每一个实例都是继承自Enum的final对象

    public static void main(String[] args) {
        DayOfWeek today = DayOfWeek.SUNDAY;
        System.out.println("Today is " + today);
        // 因为每一个枚举类实例都是final并不向外部提供构造，所以可以直接使用 == 的方式做地址比较
        if(today == DayOfWeek.SUNDAY|| today == DayOfWeek.SATURDAY) {
            System.out.println("Today is weekend");
        } else {
            System.out.println("Today is weekday");
        }
        System.out.println("Today is the " + today.getDayAsNumber() + "th day of the week");

    }
}


enum DayOfWeek {
    // 在枚举类中，(必须在)第一个语句对每一个实例进行声明，其缺省调用了无参构造方法
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY,
    TEST(-1); //也可以调用自定义的构造方法

    private final int dayAsNumber; // 定义的枚举实例的属性

    DayOfWeek() { // 默认的无参构造方法，其构造了第一行中声明的7个实例
       this.dayAsNumber = ordinal() + 1; //ordinal返回枚举实例在枚举类中的声明顺序(第一行代码),从0开始计数
    }

    //自定义的构造方法，注意枚举类是无法在外部实例化的，所以这里的构造方法只能在枚举类中使用
    // 因为枚举类的创建时间是在类加载的时候，所以这里的构造方法只能在类加载的时候调用
    DayOfWeek(int dayAsNumber) {
        this.dayAsNumber = dayAsNumber;
    }

    // 定义枚举类实例的方法
    public int getDayAsNumber() {
        return dayAsNumber;
    }

}