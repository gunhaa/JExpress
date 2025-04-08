package simple.middleware;

public class MySQLDatabase implements IMiddleWare{
    private static volatile MySQLDatabase INSTANCE;

    private MySQLDatabase() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static MySQLDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (MySQLDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MySQLDatabase();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
    }
}
