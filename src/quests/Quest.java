package quests;
public class Quest {
    private String name;
    private String beschreibung;
    private QuestZiel ziel;
    private boolean abgeschlossen;
    private QuestBelohnung belohnung;

    public Quest(String name, String beschreibung, QuestZiel ziel, QuestBelohnung belohnung) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.ziel = ziel;
        this.belohnung = belohnung;
        this.abgeschlossen = false;
    }

    public void pruefeZiel() {
        if (!abgeschlossen && ziel.istErreicht()) {
            belohnung.aushaendigen();
            abgeschlossen = true;
            System.out.println("Quest '" + name + "' abgeschlossen!");
        }
    }

    public String getName() { return name; }
    public String getBeschreibung() { return beschreibung; }
    public boolean istAbgeschlossen() { return abgeschlossen; }
}
