package simple.constant;

public enum ApplicationSetting {
    API_DOCS(1),                // 00000001
    RESPONSE_TIME(1 << 1),      // 00000010
    GET_CACHE(1 << 2),          // 00000100
    CORS(1 << 3),               // 00001000
    TEMP_SETTING1(1 << 4),      // 00010000
    TEMP_SETTING2(1 << 5),      // 00100000
    TEMP_SETTING3(1 << 6);      // 01000000

    private final int bitMask;

    ApplicationSetting(int bitMask) {
        this.bitMask = bitMask;
    }

    public int getBit() {
        return bitMask;
    }

    public static ApplicationSetting fromBit(int bit) {
        for (ApplicationSetting setting : values()) {
            if (setting.getBit() == bit) {
                return setting;
            }
        }
        return null;
    }
}

