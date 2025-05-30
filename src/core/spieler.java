package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import combat.event;
import items.inventar;
import items.item;
import items.ItemTyp;
import quests.Quest;
import systems.Crafting;
import systems.level;
import systems.entscheidung;
import systems.charakter;

public class spieler extends charakter {
    public String spielerName;
    public level spielerlevel; // Changed to Level object
    public int spielerleben;
    public int spielerschaden; // Added for combat
    public int spielerschutz;  // Added for combat
    public inventar spielerInventar; // Changed to Inventar object

    private item ausgeruesteteWaffe;
    private item ausgeruesteteRuestung;

    private ArrayList<Quest> aktiveQuests = new ArrayList<>();

    private Crafting craftingSystem;

    private Set<event> triggeredEvents = new HashSet<>();

    private ArrayList<systems.entscheidung> entscheidungsHistorie;
    private List<entscheidung> entscheidungHistory;

    // Konstruktor für die Klasse spieler
    // Initialisiert die Attribute des Spielers
    public spieler(String name, int level, int leben, int gold) {
        super(name, "Ein mutiger Abenteurer", level, leben, gold, 10, 5);
        this.spielerName = name;
        this.spielerlevel = new level(level, 0, 100); // Initial level, 0 exp, 100 max exp
        this.spielerleben = leben;
        this.spielerschaden = 10; // Default player damage
        this.spielerschutz = 5;  // Default player protection
        this.spielerInventar = new inventar(gold); // Initialize inventar with gold
        this.craftingSystem = new Crafting();
        this.entscheidungsHistorie = new ArrayList<>();
        this.entscheidungHistory = new ArrayList<>();
    }

    public boolean craftItem(java.util.List<String> inputItemNames) {
        String craftedItemName = craftingSystem.craft(inputItemNames);
        if (craftedItemName != null) {
            // Create a new item with the crafted name and add to inventory
            item craftedItem = new item(craftedItemName, "Crafted item: " + craftedItemName, 10, null, 0);
            this.spielerInventar.addItem(craftedItem);
            System.out.println("Du hast erfolgreich " + craftedItemName + " hergestellt!");
            // Remove used items from inventory
            for (String inputName : inputItemNames) {
                item toRemove = null;
                for (item i : this.spielerInventar.getItems()) {
                    if (i.getName().equalsIgnoreCase(inputName)) {
                        toRemove = i;
                        break;
                    }
                }
                if (toRemove != null) {
                    this.spielerInventar.removeItem(toRemove);
                }
            }
            return true;
        } else {
            System.out.println("Keine passende Rezeptur für die angegebenen Gegenstände gefunden.");
            return false;
        }
    }

    // levelUp() - This method is now handled by the Level class itself
    // public void levelUp() { ... }

    // addItem() - fügt ein item zum inventory hinzu
    public void addItem(item item) {
        this.spielerInventar.addItem(item);
    }

    // removeItem() - entfernt ein item aus dem inventory
    public void removeItem(item item) {
        this.spielerInventar.removeItem(item);
    }

    // getName() - gibt den Namen des Spielers zurück
    public String getName() {
        return spielerName;
    }

    // getLevel() - gibt den Level des Spielers zurück (delegiert an Level-Objekt)
    @Override
    public int getLevel() {
        return spielerlevel.getLevel();
    }

    // getLeben() - gibt die Leben des Spielers zurück
    public int getLeben() {
        return spielerleben;
    }

    // getGold() - gibt das Gold des Spielers zurück (delegiert an Inventar-Objekt)
    public int getGold() {
        return spielerInventar.getGold();
    }

    // getInventar() - gibt das Inventar des Spielers zurück
    public inventar getInventar() {
        return spielerInventar;
    }

    // getSchaden() - gibt den Schaden des Spielers zurück
    public int getSchaden() {
        // Hier könnte man auch den Schaden basierend auf ausgerüsteten Items berechnen
        return spielerschaden + (spielerlevel.getLevel() * 2); // Schaden steigt mit Level
    }

    // getSchutz() - gibt den Schutz des Spielers zurück
    public int getSchutz() {
        // Hier könnte man auch den Schutz basierend auf ausgerüsteten Items berechnen
        return spielerschutz + (spielerlevel.getLevel()); // Schutz steigt mit Level
    }

    // setLevel() - setzt den Level des Spielers (delegiert an Level-Objekt)
    public void setLevel(int level) {
        this.spielerlevel.setLevel(level);
    }

    // setLeben() - setzt die Leben des Spielers
    public void setLeben(int leben) {
        this.spielerleben = leben;
    }

    // setGold() - setzt das Gold des Spielers (delegiert an Inventar-Objekt)
    public void setGold(int gold) {
        this.spielerInventar.setGold(gold);
    }

    @Override
    public void setSchaden(int schaden) {
        super.setSchaden(schaden);
    }

    @Override
    public void setSchutz(int schutz) {
        super.setSchutz(schutz);
    }

    // treffeEntscheidung() - diese Methode wird vom Spiel selbst aufgerufen
    // betreteNaechstenRaum() - diese Methode wird vom Spiel selbst aufgerufen

    // Neue Methoden für das Ausrüstungssystem
    public void ausruesten(item item) {
        if (!item.istAusruestbar()) {
            System.out.println(item.getName() + " kann nicht ausgerüstet werden.");
            return;
        }

        if (item.getItemTyp() == ItemTyp.WAFFE) {
            if (ausgeruesteteWaffe != null) {
                spielerInventar.addItem(ausgeruesteteWaffe);
                System.out.println(ausgeruesteteWaffe.getName() + " wurde abgelegt.");
            }
            ausgeruesteteWaffe = item;
            spielerInventar.removeItem(item);
            System.out.println(item.getName() + " wurde als Waffe ausgerüstet.");
        } else if (item.getItemTyp() == ItemTyp.RUESTUNG) {
            if (ausgeruesteteRuestung != null) {
                spielerInventar.addItem(ausgeruesteteRuestung);
                System.out.println(ausgeruesteteRuestung.getName() + " wurde abgelegt.");
            }
            ausgeruesteteRuestung = item;
            spielerInventar.removeItem(item);
            System.out.println(item.getName() + " wurde als Rüstung ausgerüstet.");
        }
    }

    public void benutzeItem(item item) {
        if (!item.istNutzbar()) {
            System.out.println(item.getName() + " kann nicht benutzt werden.");
            return;
        }

        if (item.getItemTyp() == ItemTyp.TRANK || item.getItemTyp() == ItemTyp.ESSEN) {
            int heilung = item.getEffekt();
            spielerleben = Math.min(100, spielerleben + heilung);
            spielerInventar.removeItem(item);
            System.out.println("Du hast " + item.getName() + " benutzt und " + heilung + " Leben regeneriert.");
        }
    }

    @Override
    public int berechneSchaden() {
        int basisSchaden = spielerschaden + (spielerlevel.getLevel() * 2);
        if (ausgeruesteteWaffe != null) {
            basisSchaden += ausgeruesteteWaffe.getEffekt();
        }
        return basisSchaden;
    }

    @Override
    public int berechneSchutz() {
        int basisSchutz = spielerschutz + spielerlevel.getLevel();
        if (ausgeruesteteRuestung != null) {
            basisSchutz += ausgeruesteteRuestung.getEffekt();
        }
        return basisSchutz;
    }

    public void questAnnehmen(Quest quest) {
        aktiveQuests.add(quest);
        System.out.println("Neue Quest angenommen: " + quest.getName());
        System.out.println(quest.getBeschreibung());
    }

    public void questsAnzeigen() {
        System.out.println("\n--- AKTIVE QUESTS ---");
        if (aktiveQuests.isEmpty()) {
            System.out.println("Keine aktiven Quests.");
        } else {
            for (Quest quest : aktiveQuests) {
                System.out.println("- " + quest.getName() + 
                    (quest.istAbgeschlossen() ? " (Abgeschlossen)" : ""));
            }
        }
    }

    public void addErfahrung(int erfahrung) {
        this.spielerlevel.addErfahrung(erfahrung);
    }

    // Getter für aktuelle Erfahrung
    public String getErfahrung() {
        return Integer.toString(spielerlevel.getLevelErfahrung());
    }
    
    // Getter für maximale Erfahrung des aktuellen Levels
    public int getLevelMaxErfahrung() {
        return spielerlevel.getLevelMaxErfahrung();
    }

    public boolean hasTriggeredEvent(event e) {
        return triggeredEvents.contains(e);
    }

    public void addTriggeredEvent(event e) {
        triggeredEvents.add(e);
    }

    // Entscheidungs-bezogene Methoden
    public void logEntscheidung(entscheidung e) {
        if (entscheidungHistory == null) {
            entscheidungHistory = new ArrayList<>();
        }
        entscheidungHistory.add(e);
    }

    public List<entscheidung> getRecentEntscheidungen(int count) {
        if (entscheidungHistory == null || entscheidungHistory.isEmpty()) {
            return new ArrayList<>();
        }
        int startIndex = Math.max(0, entscheidungHistory.size() - count);
        return new ArrayList<>(entscheidungHistory.subList(startIndex, entscheidungHistory.size()));
    }
}