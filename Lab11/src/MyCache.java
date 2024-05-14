import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyCache extends Thread {
    private Map<Integer, StoredObject> cache = new ConcurrentHashMap<>();
    private int counter = 0;

    public MyCache() {
    }

    public MyCache(Map<Integer, StoredObject> cache, int counter) {
        this.cache = cache;
        this.counter = counter;
    }

    public Map<Integer, StoredObject> getCache() {
        return cache;
    }

    public void setCache(Map<Integer, StoredObject> cache) {
        this.cache = cache;
    }

    public void addObject(StoredObject obj) {
        cache.put(counter++, obj);
    }

    public Object getObject(int cheie) {
        StoredObject storedObject = cache.get(cheie);
        if (storedObject != null && !storedObject.isExpired()) {
            return storedObject.getMyInfo();
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Something went wrong when thread went to sleep...");
            }

            Timestamp crt = new Timestamp(System.currentTimeMillis());
            boolean allExpired = true; //verific dc toate deja sunt expirate

            for (Map.Entry<Integer, StoredObject> c : cache.entrySet()) {
                StoredObject obj = c.getValue();
                if (crt.after(obj.getExpirationTime())) {
                    c.getValue().isExpired();
                    cache.remove(c.getKey());
                    System.out.println("Expired obj: " + c.getValue());
                }else {
                    allExpired = false; // At least one object is not expired
                }
            }

            if (allExpired) {
                System.out.println("All objects are expired. Stopping the thread...");
                break; //stopam theradul
            }
        }
    }


    @Override
    public String toString() {
        return "MyCache{" +
                "cache=" + cache +
                ", counter=" + counter +
                '}';
    }
}
