import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentThread extends Thread{
    static int sum = 0;
    static int integer = 0;
    static Map<Integer, Integer> concurrent;
    static int[] intArray;
    String name;
    public ConcurrentThread(ThreadGroup group, String name, Map<Integer, Integer> concurrent1, int[] array) {
        super(group, name);
        concurrent = concurrent1;
        this.name = name;
        intArray = array;
    }

    @Override
    public void run() {
        for (integer = 0 ; integer < intArray.length; integer++){

            concurrent.put(integer, intArray[integer]);
        }
        for (integer = 0; integer < concurrent.size(); integer++){
            sum += concurrent.get(integer);
        }
    }
    public static int getSum (){
        return sum;
    }
    public static int toEmpty (){
        int result = sum;
        integer = 0;
        sum = 0;
        return result;
    }
}
