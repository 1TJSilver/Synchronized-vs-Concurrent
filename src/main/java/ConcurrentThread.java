import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentThread extends Thread{
    static AtomicInteger sum = new AtomicInteger(0);
    static AtomicInteger integer = new AtomicInteger(0);
    static Map<Integer, Integer> concurrent;
    String name;
    public ConcurrentThread(ThreadGroup group, String name, Map<Integer, Integer> concurrent1) {
        super(group, name);
        concurrent = concurrent1;
        this.name = name;
    }

    @Override
    public void run() {
        for (; integer.get() < concurrent.size();){
            integer.getAndIncrement();
            sum.getAndAdd(
                    concurrent.get(
                            integer.get()));
        }
    }
    public static int getSum (){
        return sum.get();
    }
    public static int toEmpty (){
        int result = sum.get();
        integer.getAndSet(0);
        sum.getAndSet(0);
        return result;
    }
}
