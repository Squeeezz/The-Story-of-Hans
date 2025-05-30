package combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import core.spieler;
import items.item;
import systems.charakter;  // Wichtig: Dieser Import muss korrekt sein
import systems.entscheidung;

public class event {
    // name, beschreibung, typ, auswirkungen
    public String eventName;
    public String eventBeschreibung;
    public String eventTyp; // e.g., "Kampf", "Begegnung", "Entscheidung", "Fund"
    public String eventAuswirkungen; // Simple string for description of outcome

    // For decision-based events
    public List<entscheidung> eventEntscheidungen = new ArrayList<>();

    // For combat events
    public charakter eventGegner; // The enemy for combat events

    // Konstruktor
    public event(String eventName, String eventBeschreibung, String eventTyp, String eventAuswirkungen) {
        this.eventName = eventName;
        this.eventBeschreibung = eventBeschreibung;
        this.eventTyp = eventTyp;
        this.eventAuswirkungen = eventAuswirkungen;
    }

    // Konstruktor für Kampf-Events korrigieren
    public event(String eventName, String eventBeschreibung, String eventTyp, charakter gegner) {
        if ("kampf".equalsIgnoreCase(eventTyp)) {
            if (gegner == null) {
                throw new IllegalArgumentException("Kampf-Events benötigen einen Gegner!");
            }
            this.eventGegner = gegner;
            this.eventAuswirkungen = "Ein Kampf gegen " + gegner.getName();
        } else {
            this.eventGegner = null;
            this.eventAuswirkungen = eventBeschreibung;
        }
        this.eventName = eventName;
        this.eventBeschreibung = eventBeschreibung;
        this.eventTyp = eventTyp.toLowerCase(); // Typ immer in Kleinbuchstaben speichern
    }

    // Constructor for decision events
    public event(String eventName, String eventBeschreibung, String eventTyp, List<entscheidung> entscheidungen) {
        this.eventName = eventName;
        this.eventBeschreibung = eventBeschreibung;
        this.eventTyp = eventTyp;
        this.eventEntscheidungen = entscheidungen;
        this.eventAuswirkungen = "Eine wichtige Entscheidung";
    }

    // getName() - gibt den Namen des Events zurück
    public String getName() {
        return eventName;
    }

    // getBeschreibung() - gibt die Beschreibung des Events zurück
    public String getBeschreibung() {
        return eventBeschreibung;
    }

    // getTyp() - gibt den Typ des Events zurück
    public String getTyp() {
        return eventTyp;
    }

    // getAuswirkungen() - gibt die Auswirkungen des Events zurück
    public String getAuswirkungen() {
        if ("kampf".equalsIgnoreCase(this.eventTyp) && this.eventGegner != null) {
            return "Ein Kampf gegen " + this.eventGegner.getName();
        }
        return eventAuswirkungen;
    }

    // setName() - setzt den Namen des Events
    public void setName(String eventName) {
        this.eventName = eventName;
    }

    // setBeschreibung() - setzt die Beschreibung des Events
    public void setBeschreibung(String eventBeschreibung) {
        this.eventBeschreibung = eventBeschreibung;
    }

    // setTyp() - setzt den Typ des Events
    public void setTyp(String eventTyp) {
        this.eventTyp = eventTyp;
    }

    // setAuswirkungen() - setzt die Auswirkungen des Events
    public void setAuswirkungen(String eventAuswirkungen) {
        this.eventAuswirkungen = eventAuswirkungen;
    }

    // addOption() - fügt eine Option zum Event hinzu (if it's a decision event)
    public void addEntscheidung(entscheidung entscheidung) {
        if (this.eventTyp.equals("Entscheidung")) {
            this.eventEntscheidungen.add(entscheidung);
        } else {
            System.out.println("Dieses Event ist kein Entscheidungs-Event.");
        }
    }

    // ausfuehren() - führt das Event aus und wendet die Auswirkungen auf den Spieler an
    public void ausfuehren(spieler player) {
        // Die Beschreibung wird nur einmal am Anfang ausgegeben
        System.out.println(this.eventBeschreibung);
        
        switch (this.eventTyp.toLowerCase()) {
            case "kampf":
                if (this.eventGegner == null) {
                    System.out.println("FEHLER: Ungültiges Kampf-Event!");
                    return;
                }
                System.out.println("\n=== KAMPF BEGINNT ===");
                kampf kampf = new kampf();
                kampf.startKampf(player, this.eventGegner);
                break;

            case "gefahr":
                System.out.println("\n=== GEFÄHRLICHE SITUATION ===");
                Random rand = new Random();
                if (rand.nextDouble() < 0.4) { // 40% Chance auf Schaden
                    int schaden = rand.nextInt(20) + 10;
                    player.setLeben(player.getLeben() - schaden);
                    System.out.println("Du erleidest " + schaden + " Schaden!");
                    if (player.getLeben() <= 0) {
                        System.out.println("Die Gefahr war zu groß - du stirbst!");
                        System.exit(0);
                    }
                }
                break;
            case "fund":
                System.out.println(this.eventAuswirkungen);
                // Beispiel: Spieler findet Gold oder ein Item
                Random random = new Random();
                int goldFound = random.nextInt(50) + 10; // 10-59 Gold
                player.setGold(player.getGold() + goldFound);
                System.out.println("Du findest " + goldFound + " Goldstücke!");
                // Chance auf ein Item
                if (random.nextDouble() < 0.3) { // 30% Chance
                    item foundItem = new item("Glänzender Stein", "Ein kleiner, glänzender Stein.", 5, null, goldFound);
                    player.addItem(foundItem);
                    System.out.println("Du findest auch einen " + foundItem.getName() + "!");
                }
                break;
            case "entscheidung":
                if (this.eventEntscheidungen.isEmpty()) {
                    System.out.println("Für dieses Ereignis gibt es keine Entscheidungen.");
                    break;
                }
                for (int i = 0; i < eventEntscheidungen.size(); i++) {
                    System.out.println((i + 1) + ". " + eventEntscheidungen.get(i).entscheigungBeschreibung);
                }
                int choice = -1;
                Scanner scanner = new Scanner(System.in);
                while (choice < 1 || choice > eventEntscheidungen.size()) {
                    System.out.print("Wähle eine Option: ");
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                        System.out.print("\033[H\033[2J");  
                        System.out.flush();  
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Eingabe. Bitte gib eine Zahl ein.");
                    }
                }
                entscheidung selectedDecision = eventEntscheidungen.get(choice - 1);
                System.out.println("Du hast gewählt: " + selectedDecision.entscheigungBeschreibung);
                // Apply decision effects
                selectedDecision.auswerten(player);
                break;
            case "begegnung":
                System.out.println(this.eventAuswirkungen);
                // Beispiel: Treffen auf einen freundlichen NPC
                random = new Random();
                if (random.nextDouble() < 0.5) { // 50% Chance für einen positiven Ausgang
                    System.out.println("Der Reisende teilt eine Geschichte mit dir und gibt dir 5 Erfahrungspunkte.");
                    player.addErfahrung(5);
                } else {
                    System.out.println("Der Reisende eilt vorbei, ohne dich zu beachten.");
                }
                break;
            default:
                System.out.println("Es geschieht etwas Ungewohntes: " + this.eventAuswirkungen);
                break;
        }
    }
}
