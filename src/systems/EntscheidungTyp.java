package systems;
public enum EntscheidungTyp {
    POSITIV("Positiv"),
    NEGATIV("Negativ"),
    NEUTRAL("Neutral"),
    GEFAHR("Gefährlich"),
    GLUECK("Glück");

    private final String name;

    EntscheidungTyp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EntscheidungTyp fromString(String text) {
        for (EntscheidungTyp typ : EntscheidungTyp.values()) {
            if (typ.name.equalsIgnoreCase(text)) {
                return typ;
            }
        }
        return NEUTRAL;
    }
}
