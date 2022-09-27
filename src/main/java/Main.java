import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    static final int AMOUNT = 100_000;
    static final int VALUES = 100;

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        ThreadGroup concurrentGroup = new ThreadGroup("Concurrent");
        ThreadGroup synchronizedGroup = new ThreadGroup("Synchronized");

        System.out.println("Filling the maps...");
        fillMap(concurrentMap, VALUES);
        fillMap(synchronizedMap, VALUES);




        System.out.println("Start an experiment");

        System.out.println("Current time: " + System.currentTimeMillis());

        new ConcurrentThread(concurrentGroup, "Concurrent-1", concurrentMap).start();
        new ConcurrentThread(concurrentGroup, "Concurrent-2", concurrentMap).start();
        new ConcurrentThread(concurrentGroup, "Concurrent-3", concurrentMap).start();

        waitAndFinish(concurrentGroup);

        System.out.println(concurrentGroup.getName() + ": Total sum = " + ConcurrentThread.toEmpty());

        System.out.println("Current time: " + System.currentTimeMillis() + "\n");

        System.out.println("Start a Synchronized group");
        System.out.println("Current time: " + System.currentTimeMillis());

        new ConcurrentThread(synchronizedGroup, "Synchronized-1", synchronizedMap).start();
        new ConcurrentThread(synchronizedGroup, "Synchronized-2", synchronizedMap).start();
        new ConcurrentThread(synchronizedGroup, "Synchronized-3", synchronizedMap).start();

        waitAndFinish(synchronizedGroup);
        System.out.println(synchronizedGroup.getName() + ": Total sum = " + ConcurrentThread.getSum());

        System.out.println("Current time: " + System.currentTimeMillis() + "\n");
    }

    public static void fillMap(Map<Integer, Integer> map, int value) {
        Random random = new Random();
        for (int i = 0; i < AMOUNT; i++) {
            map.put(i, random.nextInt(value));
        }
    }

    public static void waitAndFinish(ThreadGroup group) {
        while (group.activeCount() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println(group.getName() + " is end");
        group.interrupt();
    }
}
