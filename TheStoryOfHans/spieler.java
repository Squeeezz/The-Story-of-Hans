import java.util.ArrayList;

public class spieler {
    public String spielerName;
    public int spielerlevel;
    public int spielerleben;
    public int spielergold;
    public ArrayList<String> inventar = new ArrayList<String>();

    // Konstruktor für die Klasse spieler
    // Initialisiert die Attribute des Spielers
    public spieler(String name, int level, int leben, int gold) {
        this.spielerName = name;
        this.spielerlevel = level;
        this.spielerleben = leben;
        this.spielergold = gold;
    }

    public void levelUp() {
        this.spielerlevel++;
        //this.spielerleben += 10; // (Beispiel: Erhöhung des Lebens bei Levelaufstieg)
        System.out.println(spielerName + " hat Level " + spielerlevel + " erreicht!");
    }


}
