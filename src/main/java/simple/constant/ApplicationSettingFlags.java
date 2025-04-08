package simple.constant;

public enum ApplicationSettingFlags {
    API_DOCS(1),
    RESPONSE_TIME(1 << 1),
    BLOCK_SAME_IP_PER_SEC(1 << 2),
    CORS(1 << 3),
    DB_H2(1 << 4),
    DB_MYSQL(1 << 5),
    LOGGER_REQUEST(1 << 6);

    private final int bitMask;

    ApplicationSettingFlags(int bitMask) {
        this.bitMask = bitMask;
    }

    public int getBit() {
        return bitMask;
    }

    public boolean isCors(){
        return (bitMask & CORS.bitMask) != 0;
    }

    public static boolean isH2AndMySQLEnabled(int config){
        return (config & DB_H2.bitMask) != 0 && (config & DB_MYSQL.bitMask) !=0 ;
    }

    public boolean isSettingEnabled(int config){
        return (bitMask & config) != 0;
    }

}

