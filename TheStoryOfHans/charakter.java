import java.util.ArrayList;

public class charakter {
    //name, beschreibung, level, leben, gold, inventory
    public String characterName;
    public String characterbeschreibung;
    public int characterLevel;
    public int characterLeben;
    public int characterGold;
    public ArrayList<String> characterInventory = new ArrayList<String>();


    // Konstruktor
    public charakter(String characterName, String characterbeschreibung, int characterLevel, int characterLeben, int characterGold) {
        this.characterName = characterName;
        this.characterbeschreibung = characterbeschreibung;
        this.characterLevel = characterLevel;
        this.characterLeben = characterLeben;
        this.characterGold = characterGold;
    }

}
