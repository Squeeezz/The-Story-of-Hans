package items;
// inventar.java
import java.util.ArrayList;

public class inventar {
    private ArrayList<item> items;
    private int gold;
    private final int GEWICHT_LIMIT = 100;
    private int aktuellesGewicht = 0;

    // Konstruktor
    public inventar(ArrayList<item> inventarItems, int inventarGold) {
        this.items = inventarItems;
        this.gold = inventarGold;
        this.aktuellesGewicht = berechneGesamtgewicht(inventarItems);
    }

    // Constructor ohne initiale Items
    public inventar(int startGold) {
        this.items = new ArrayList<>();
        this.gold = startGold;
    }

    // addItem() - fügt ein item zum Inventar hinzu
    public boolean addItem(item item) {
        if (aktuellesGewicht + item.getGewicht() > GEWICHT_LIMIT) {
            System.out.println("Zu schwer! Du kannst nicht mehr tragen.");
            return false;
        }
        items.add(item);
        aktuellesGewicht += item.getGewicht();
        System.out.println(item.itemName + " wurde zum Inventar hinzugefügt.");
        return true;
    }

    // removeItem() - entfernt ein item aus dem Inventar
    public void removeItem(item item) {
        if (items.remove(item)) {
            aktuellesGewicht -= item.getGewicht();
            System.out.println(item.itemName + " wurde aus dem Inventar entfernt.");
        } else {
            System.out.println(item.itemName + " ist nicht im Inventar.");
        }
    }

    public int getAktuellesGewicht() {
        return aktuellesGewicht;
    }

    public int getGewichtLimit() {
        return GEWICHT_LIMIT;
    }

    // getGold() - gibt das Gold im Inventar zurück
    public int getGold() {
        return gold;
    }

    // setGold() - setzt das Gold im Inventar
    public void setGold(int inventarGold) {
        this.gold = inventarGold;
    }

    // getItems() - gibt die Items im Inventar zurück
    public ArrayList<item> getItems() {
        return items;
    }

    // berechneGesamtgewicht() - berechnet das Gesamtgewicht der Items im Inventar
    private int berechneGesamtgewicht(ArrayList<item> inventarItems) {
        int gesamtgewicht = 0;
        for (item i : inventarItems) {
            gesamtgewicht += i.getGewicht();
        }
        return gesamtgewicht;
    }
}