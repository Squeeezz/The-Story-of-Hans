package core;

public abstract class AbstractCharacter {
    protected String name;
    protected int level;
    protected int leben;
    protected int schaden;
    protected int schutz;
    protected int gold;

    public AbstractCharacter(String name, int level, int leben, int gold, int schaden, int schutz) {
        this.name = name;
        this.level = level;
        this.leben = leben;
        this.gold = gold;
        this.schaden = schaden;
        this.schutz = schutz;
    }

    public abstract int berechneSchaden();
    public abstract int berechneSchutz();

    // Common getters and setters
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getLeben() { return leben; }
    public int getGold() { return gold; }
    public void setLeben(int leben) { this.leben = leben; }
    public void setGold(int gold) { this.gold = gold; }
}
