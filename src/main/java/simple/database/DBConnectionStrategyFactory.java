package simple.database;

import simple.constant.ServerSettingChecker;

import static simple.constant.ApplicationSettingFlags.DB_MYSQL;

public class DBConnectionStrategyFactory {
    public static IDBConnection create() {
        if (ServerSettingChecker.isServerEnabled(DB_MYSQL)) {
            return IDBConnection.getMySQLConnection();
        }
        return IDBConnection.getH2Connection();
    }
}
