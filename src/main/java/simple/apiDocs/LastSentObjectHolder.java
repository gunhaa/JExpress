package simple.apiDocs;

public class LastSentObjectHolder {
    private static Class<?> lastSentType;

    private LastSentObjectHolder(){}

    public static void setLastSentType(Class<?> type) {
        lastSentType = type;
    }

    public static Class<?> getLastSentmType() {
        return lastSentType;
    }
}
