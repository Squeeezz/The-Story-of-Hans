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
            levelMaxErfahrung *= 2;
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
        // Erfahrung auf 0 setzen, wenn Level geändert wird
        this.levelErfahrung = 0;
        // Maximale Erfahrung für das neue Level setzen
        this.levelMaxErfahrung = levelMaxErfahrung * (int) Math.pow(2, level - 1);
    }

}
