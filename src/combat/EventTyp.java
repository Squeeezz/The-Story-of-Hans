package combat;
public enum EventTyp {
    KAMPF("Kampf"),
    BEGEGNUNG("Begegnung"),
    ENTSCHEIDUNG("Entscheidung"),
    FUND("Fund"),
    ANDERES("Anderes");

    private final String name;

    EventTyp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EventTyp fromString(String text) {
        for (EventTyp typ : EventTyp.values()) {
            if (typ.name.equalsIgnoreCase(text)) {
                return typ;
            }
        }
        return ANDERES;
    }
}
