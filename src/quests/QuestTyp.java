package quests;
public enum QuestTyp {
    TOETE_MONSTER("Besiege Monster"),
    SAMMLE_ITEM("Sammle Items"),
    ERREICHE_ORT("Erreiche Ort"),
    FINDE_PERSON("Finde Person");

    private final String beschreibung;

    QuestTyp(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
