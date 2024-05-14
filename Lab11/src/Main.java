
public class Main {
    public static void main(String[] args) {
        MyCache cache = new MyCache();

        StoredObject obj1 = new StoredObject("obj 1", System.currentTimeMillis() + 4000);
        StoredObject obj2 = new StoredObject("obj 2", System.currentTimeMillis() + 400);

        cache.addObject(obj1);
        cache.addObject(obj2);
        cache.start();
        /////add 100 obj
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            StoredObject obj = new StoredObject("Obj " + i, System.currentTimeMillis() + 4000);
            cache.addObject(obj);
            System.out.println("Obj: " + obj);
        }
    }

}