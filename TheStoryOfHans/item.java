public class item {
    // name, beschreibung, wert
    public String itemName;
    public String itemBeschreibung;
    public int itemWert;


    // TEST ITEM
    public item rostmesser = new item("kleiner roestiger Messer" ," kann schneiden, ist leicht zerbrechbar, ist nicht sehr effektiv im kampf", 2);

    
    // Konstruktor
    public item(String itemName, String itemBeschreibung, int itemWert) {
        this.itemName = itemName;
        this.itemBeschreibung = itemBeschreibung;
        this.itemWert = itemWert;
    }





}
