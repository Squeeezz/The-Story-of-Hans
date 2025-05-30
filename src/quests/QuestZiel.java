package quests;
public class QuestZiel {
    private QuestTyp typ;
    private String zielName;
    private int benoetigteAnzahl;
    private int aktuelleAnzahl;

    public QuestZiel(QuestTyp typ, String zielName, int benoetigteAnzahl) {
        this.typ = typ;
        this.zielName = zielName;
        this.benoetigteAnzahl = benoetigteAnzahl;
        this.aktuelleAnzahl = 0;
    }

    public void erhoeheAnzahl() {
        aktuelleAnzahl++;
    }

    public boolean istErreicht() {
        return aktuelleAnzahl >= benoetigteAnzahl;
    }

    public void setAktuelleAnzahl(int anzahl) {
        this.aktuelleAnzahl = anzahl;
    }
}
