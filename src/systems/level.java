package systems;
// level.java
import java.lang.Math; // Math was already imported, but good to be explicit

public class level {
    //level, erfahrung, maxErfahrung
    public int level;
    public int levelErfahrung;
    public int levelMaxErfahrung;

    // Konstruktor
    public level(int level, int levelErfahrung, int levelMaxErfahrung) {
        this.level = level;
        this.levelErfahrung = levelErfahrung;
        this.levelMaxErfahrung = levelMaxErfahrung; // Wert für maximale Erfahrung
    }

    // Wenn man 100 Erfahrungspunkte erreicht steigt man 1 Level auf
    // und die Erfahrung wird auf 0 gesetzt
    // Erfahrungspunkte bekommt man durch Interkationen, Quests, Kämpfe etc.
    public void levelUp() {
        if (levelErfahrung >= levelMaxErfahrung) {
            level++;
            levelErfahrung = 0;
            // Die maximale Erfahrung für das nächste Level wird erhöht
            levelMaxErfahrung *= 2; // Increase max experience for next level
            System.out.println("GLÜCKWUNSCH! Du bist Level " + level + " erreicht!");
        }
    }

    // Getter für Level
    public int getLevel() {
        return level;
    }
    // Getter für Erfahrung
    public int getLevelErfahrung() {
        return levelErfahrung;
    }
    // Getter für maximale Erfahrung
    public int getLevelMaxErfahrung() {
        return levelMaxErfahrung;
    }
    // Setter für Erfahrung
    public void setLevelErfahrung(int levelErfahrung) {
        this.levelErfahrung = levelErfahrung;
        levelUp(); // Überprüfen, ob ein Levelaufstieg nötig ist
    }
    // Setter für Level
    public void setLevel(int level) {
        this.level = level;
        this.levelErfahrung = 0;
        this.levelMaxErfahrung = 100 * (int) Math.pow(2, level - 1); // Recalculate max exp based on new level
    }

    // addErfahrung() - fügt Erfahrung zum aktuellen Level hinzu
    public void addErfahrung(int erfahrung) {
        this.levelErfahrung += erfahrung;
        System.out.println("Du hast " + erfahrung + " Erfahrungspunkte erhalten. Aktuelle Erfahrung: " + levelErfahrung + "/" + levelMaxErfahrung);
        levelUp(); // Check for level up after adding experience
    }
}