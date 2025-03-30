package simple.apiDocs;

public class LastSentObjectHolder {
    private static Class<?> lastSentType;

    public static void setLastSentType(Class<?> type) {
        lastSentType = type;
    }

    public static Class<?> getLastSentType() {
        return lastSentType;
    }
}
