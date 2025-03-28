package simple.constant;

public enum ApplicationSetting {
    API_DOCS(1),
    RESPONSE_TIME(1 << 1),
    GET_CACHE(1 << 2),
    CORS(1 << 3),
    TEMP_SETTING1(1 << 4),
    TEMP_SETTING2(1 << 5),
    TEMP_SETTING3(1 << 6);

    private final int bitMask;

    ApplicationSetting(int bitMask) {
        this.bitMask = bitMask;
    }

    public int getBit() {
        return bitMask;
    }

    public boolean isEnabled(int config){
        return (bitMask & config) == 1;
    }
}

