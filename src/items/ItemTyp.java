package items;
public enum ItemTyp {
    WAFFE("Waffe"),
    RUESTUNG("Rüstung"),
    TRANK("Trank"),
    ESSEN("Essen"),
    QUEST("Quest-Gegenstand"),
    SONSTIGES("Sonstiges");

    private final String name;

    ItemTyp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
