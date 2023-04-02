public class temp {
    public static void main(String[] args) {
        System.out.println(timeFormat(1));
        System.out.println(timeFormat(60));
        System.out.println(timeFormat(1234));
        System.out.println(timeFormat(360310));
        System.out.println(timeFormat(0));

    }

    public static String timeFormat(int second) {
        int hours = second / 3600;
        int minutes = (second % 3600) / 60;
        int remainSec = second % 60;
        StringBuilder sb = new StringBuilder();

        if(hours > 0) {
            sb.append(hours).append("h");
        }
        if(minutes > 0) {
            sb.append(minutes).append("min");
        }
        if(remainSec > 0 || sb.length() == 0) { //sb.length == 0 的情况是 0 秒，此时也要显示 0s
            sb.append(remainSec).append("s");
        }

        return sb.toString();

    }
}
