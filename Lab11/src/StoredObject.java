import java.sql.Timestamp;

public class StoredObject {
    private Object myInfo;
    private boolean expired; // default false, cand expira e true
    private Timestamp expirationTime;

    public StoredObject(Object myInfo, boolean expired, Timestamp expirationTime) {
        this.myInfo = myInfo;
        this.expired = expired;
        this.expirationTime = expirationTime;
    }

    public StoredObject(Object myInfo, long expirationTime) {
        this.myInfo = myInfo;
        this.expirationTime = new Timestamp(expirationTime);
    }

    public Object getMyInfo() {
        return myInfo;
    }

    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isExpired() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.after(expirationTime)) {
            expired = true;
        }
        return expired;
    }

    @Override
    public String toString() {
        return "StoredObject{" +
                "myInfo=" + myInfo +
                ", expired=" + expired +
                ", expirationTime=" + expirationTime +
                '}';
    }
}