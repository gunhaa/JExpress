package simple.middleware;

import simple.database.H2Connection;

public class H2Database implements Middleware{

    private static volatile H2Database INSTANCE;

    private H2Database() {
    }

    /**
     * lazy Loading, Double-Checked Locking
     */
    public static H2Database getInstance() {
        if (INSTANCE == null) {
            synchronized (H2Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = new H2Database();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        H2Connection h2 = H2Connection.getInstance();
        h2.getConnection();
        h2.getTestDataset();
    }
}
