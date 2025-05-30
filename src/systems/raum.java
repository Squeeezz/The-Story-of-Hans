package systems;

import java.util.ArrayList;
import java.util.Random;

import combat.event;
import core.spieler;
import items.item;

public class raum {
    public String raumName;
    public String raumBeschreibung;
    public ArrayList<event> raumEvents = new ArrayList<event>(); // Changed to ArrayList of event objects
    public ArrayList<item> raumItems = new ArrayList<item>();   // Changed to ArrayList of item objects

    // Konstruktor
    public raum(String raumName, String raumBeschreibung) {
        this.raumName = raumName;
        this.raumBeschreibung = raumBeschreibung;
    }

    // addEvent() - fügt ein Event zum Raum hinzu
    public void addEvent(event event) {
        if (event == null) {
            System.out.println("FEHLER: Versuch, null-Event hinzuzufügen!");
            return;
        }

        if ("kampf".equalsIgnoreCase(event.getTyp())) {
            if (event.eventGegner == null) {
                return;
            }
        }
        
        this.raumEvents.add(event);
    }

    // addItem() - fügt ein Item zum Raum hinzu
    public void addItem(item item) {
        this.raumItems.add(item);
    }

    // getName() - gibt den Namen des Raumes zurück
    public String getName() {
        return raumName;
    }

    // getBeschreibung() - gibt die Beschreibung des Raumes zurück
    public String getBeschreibung() {
        return raumBeschreibung;
    }

    // getEvents() - gibt die Events des Raumes zurück
    public ArrayList<event> getEvents() {
        return raumEvents;
    }

    // getItems() - gibt die Items des Raumes zurück
    public ArrayList<item> getItems() {
        return raumItems;
    }

    // Methode zum Auslösen eines zufälligen Events im Raum
    public void triggerRandomEvent(spieler player) {
        if (raumEvents.isEmpty()) return;

        // Kopiere und filtere Events
        ArrayList<event> gueltigeEvents = new ArrayList<>();
        
        for (event e : new ArrayList<>(raumEvents)) {
            if (e == null) continue;
            
            // Skip events already triggered by player
            if (player.hasTriggeredEvent(e)) {
                continue;
            }
            
            if ("kampf".equalsIgnoreCase(e.getTyp())) {
                if (e.eventGegner == null) {
                    continue;
                }
                if (e.eventGegner.getLeben() <= 0) {
                    continue;
                }
            }
            
            gueltigeEvents.add(e);
        }

        if (!gueltigeEvents.isEmpty()) {
            Random rand = new Random();
            event randomEvent = gueltigeEvents.get(rand.nextInt(gueltigeEvents.size()));
            System.out.println("\n--- Ein neues Ereignis geschieht in " + raumName + " ---");
            randomEvent.ausfuehren(player);
            player.addTriggeredEvent(randomEvent);
        }
    }
    
    // Methode zum Finden von Items im Raum
    public void searchRoomForItems(spieler player) {
        if (!raumItems.isEmpty()) {
            System.out.println("\nDu suchst im Raum und findest:");
            for (item foundItem : new ArrayList<>(raumItems)) { // Iterate over a copy to avoid ConcurrentModificationException
                System.out.println("- " + foundItem.getName() + " (" + foundItem.getBeschreibung() + ")");
                player.getInventar().addItem(foundItem);
                raumItems.remove(foundItem); // Remove item from room after picking it up
            }
        } else {
            System.out.println("\nDu suchst den Raum ab, findest aber nichts Besonderes.");
        }
    }
}
