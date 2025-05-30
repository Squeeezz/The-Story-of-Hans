package items;
// item.java
public class item {
    public String itemName;
    public String itemBeschreibung;
    public int itemWert;
    public ItemTyp itemTyp;
    public int itemEffekt;
    private int gewicht; // Neue Property

    // Konstruktor
    public item(String name, String beschreibung, int wert, ItemTyp typ, int effekt) {
        this.itemName = name;
        this.itemBeschreibung = beschreibung;
        this.itemWert = wert;
        this.itemTyp = typ;
        this.itemEffekt = effekt;
        this.gewicht = 1; // Standard-Gewicht von 1
    }

    // getName() - gibt den Namen des Items zurück
    public String getName() {
        return itemName;
    }

    // getBeschreibung() - gibt die Beschreibung des Items zurück
    public String getBeschreibung() {
        return itemBeschreibung;
    }

    // getWert() - gibt den Wert des Items zurück
    public int getWert() {
        return itemWert;
    }

    public ItemTyp getItemTyp() {
        return itemTyp;
    }

    public int getEffekt() {
        return itemEffekt;
    }

    public boolean istNutzbar() {
        return itemTyp == ItemTyp.TRANK || itemTyp == ItemTyp.ESSEN;
    }

    public boolean istAusruestbar() {
        return itemTyp == ItemTyp.WAFFE || itemTyp == ItemTyp.RUESTUNG;
    }

    // setName() - setzt den Namen des Items
    public void setName(String itemName) {
        this.itemName = itemName;
    }

    // setBeschreibung() - setzt die Beschreibung des Items
    public void setBeschreibung(String itemBeschreibung) {
        this.itemBeschreibung = itemBeschreibung;
    }

    // setWert() - setzt den Wert des Items
    public void setWert(int itemWert) {
        this.itemWert = itemWert;
    }

    public int getGewicht() {
        return this.gewicht;
    }

    // Optional: Setter für Gewicht
    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }
}