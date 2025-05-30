package systems;

import java.util.ArrayList;
import core.AbstractCharacter;

public class charakter extends AbstractCharacter {
    //name, beschreibung, level, leben, gold, inventory
    public String characterName;
    public String characterbeschreibung;
    public int characterLevel;
    public int characterLeben;
    protected int characterSchaden; // Added for combat
    protected int characterSchutz;  // Added for combat
    public int characterGold;
    public ArrayList<String> characterInventory = new ArrayList<String>(); // Simple string list for drops

    // Konstruktor
    public charakter(String characterName, String characterbeschreibung, int characterLevel, int characterLeben, int characterGold) {
        super(characterName, characterLevel, characterLeben, characterGold, 
              5 + (characterLevel * 2),  // Basis-Schaden
              2 + characterLevel);       // Basis-Schutz
        this.characterName = characterName;
        this.characterbeschreibung = characterbeschreibung;
        this.characterInventory = new ArrayList<>();
        this.characterLevel = characterLevel;
        this.characterLeben = characterLeben;
        this.characterGold = characterGold;
        this.characterSchaden = 5 + (characterLevel * 2);
        this.characterSchutz = 2 + characterLevel;
    }

    // Overloaded constructor for enemies with specific damage/protection
    public charakter(String characterName, String characterbeschreibung, int characterLevel, int characterLeben, int characterGold, int schaden, int schutz) {
        super(characterName, characterLevel, characterLeben, characterGold, schaden, schutz);
        this.characterName = characterName;
        this.characterbeschreibung = characterbeschreibung;
        this.characterInventory = new ArrayList<>();
        this.characterLevel = characterLevel;
        this.characterLeben = characterLeben;
        this.characterGold = characterGold;
        this.characterSchaden = schaden;
        this.characterSchutz = schutz;
    }

    // getName() - gibt den Namen des Charakters zurück
    public String getName() {
        return characterName;
    }

    // getBeschreibung() - gibt die Beschreibung des Charakters zurück
    public String getBeschreibung() {
        return characterbeschreibung;
    }

    // getLevel() - gibt den Level des Charakters zurück
    public int getLevel() {
        return characterLevel;
    }

    // getLeben() - gibt die Leben des Charakters zurück
    public int getLeben() {
        return characterLeben;
    }

    // getGold() - gibt das Gold des Charakters zurück
    public int getGold() {
        return characterGold;
    }

    // getSchaden() - gibt den Schaden des Charakters zurück
    public int getSchaden() {
        return characterSchaden;
    }

    // getSchutz() - gibt den Schutz des Charakters zurück
    public int getSchutz() {
        return characterSchutz;
    }

    // setLevel() - setzt den Level des Charakters
    public void setLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    // setLeben() - setzt die Leben des Charakters
    public void setLeben(int characterLeben) {
        this.characterLeben = characterLeben;
    }

    // setGold() - setzt das Gold des Charakters
    public void setGold(int characterGold) {
        this.characterGold = characterGold;
    }

    public void setAngriff(double newValue) {
        this.characterSchaden = (int) newValue;
    }

    public void setVerteidigung(double newValue) {
        this.characterSchutz = (int) newValue;
    }
    
    public int getAngriff() {
        return this.characterSchaden;
    }
    
    public int getVerteidigung() {
        return this.characterSchutz;
    }

    public void setSchaden(int schaden) {
        this.characterSchaden = schaden;
    }
    
    public void setSchutz(int schutz) {
        this.characterSchutz = schutz;
    }

    public int getCharacterSchaden() {
        return this.characterSchaden;
    }

    public void setCharacterSchaden(int schaden) {
        this.characterSchaden = schaden;
    }

    public int getCharacterSchutz() {
        return this.characterSchutz;
    }

    public void setCharacterSchutz(int schutz) {
        this.characterSchutz = schutz;
    }

    @Override
    public int berechneSchaden() {
        return characterSchaden;
    }

    @Override
    public int berechneSchutz() {
        return characterSchutz;
    }
}