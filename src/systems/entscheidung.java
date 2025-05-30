package systems;

import java.util.Random;

import combat.kampf;
import core.spieler;
import items.ItemTyp;
import items.item;

public class entscheidung {
    private static int idCounter = 0;
    private int entscheidungId;
    //beschreibung, auswirkungen, typ, optionen
    public String entscheigungBeschreibung;
    public String entscheidungAuswirkungen; // e.g., "GoldGewinn:50", "LebenVerlust:10", "Item:Schwert"
    public String entscheidungTyp; // e.g., "Positiv", "Negativ", "Neutral"
    public String[] entscheidungOptionen; // Array of strings for now, consider a list if dynamic options are needed

    // Konstruktor
    public entscheidung(String beschreibung, String auswirkungen, String typ, String[] optionen) {
        this.entscheidungId = ++idCounter;
        this.entscheigungBeschreibung = beschreibung;
        this.entscheidungAuswirkungen = auswirkungen;
        this.entscheidungTyp = typ;
        this.entscheidungOptionen = optionen;
    }

    // Constructor without options array (if a decision directly applies effects)
    public entscheidung(String beschreibung, String auswirkungen, String typ) {
        this.entscheidungId = ++idCounter;
        this.entscheigungBeschreibung = beschreibung;
        this.entscheidungAuswirkungen = auswirkungen;
        this.entscheidungTyp = typ;
        this.entscheidungOptionen = new String[]{};
    }

    // getBeschreibung() - gibt die Beschreibung der Entscheidung zurück
    public String getBeschreibung() {
        return entscheigungBeschreibung;
    }

    // setBeschreibung() - setzt die Beschreibung der Entscheidung
    public void setBeschreibung(String entscheigungBeschreibung) {
        this.entscheigungBeschreibung = entscheigungBeschreibung;
    }

    // getAuswirkungen() - gibt die Auswirkungen der Entscheidung zurück
    public String getAuswirkungen() {
        return entscheidungAuswirkungen;
    }

    // setAuswirkungen() - setzt die Auswirkungen der Entscheidung
    public void setAuswirkungen(String entscheidungAuswirkungen) {
        this.entscheidungAuswirkungen = entscheidungAuswirkungen;
    }

    // getTyp() - gibt den Typ der Entscheidung zurück
    public String getTyp() {
        return entscheidungTyp;
    }

    // setTyp() - setzt den Typ der Entscheidung
    public void setTyp(String entscheidungTyp) {
        this.entscheidungTyp = entscheidungTyp;
    }

    // getOptionen() - gibt die Optionen der Entscheidung zurück
    public String[] getOptionen() {
        return entscheidungOptionen;
    }

    // setOptionen() - setzt die Optionen der Entscheidung
    public void setOptionen(String[] entscheidungOptionen) {
        this.entscheidungOptionen = entscheidungOptionen;
    }

    // auswerten() - wertet die Entscheidungen des Spielers aus und wendet die Auswirkungen an
    public void auswerten(spieler player) {
        System.out.println("Auswirkungen der Entscheidung: " + entscheidungAuswirkungen);
        String[] effects = entscheidungAuswirkungen.split(";");
        Random random = new Random();

        // Log decision in player's history
        player.logEntscheidung(this);

        for (String effect : effects) {
            effect = effect.trim();
            if (effect.startsWith("Kampf:")) {
                String gegnerName = effect.substring("Kampf:".length());
                charakter gegner = erstelleGegner(gegnerName, player.getLevel());
                if (gegner != null) {
                    kampf kampf = new kampf();
                    kampf.startKampf(player, gegner);
                }
            } else if (effect.startsWith("GoldGewinn:")) {
                try {
                    int gold = Integer.parseInt(effect.substring("GoldGewinn:".length()));
                    player.setGold(player.getGold() + gold);
                    System.out.println("Du hast " + gold + " Gold erhalten. Neues Gold: " + player.getGold());
                } catch (NumberFormatException e) {
                    System.err.println("Fehler beim Parsen von GoldGewinn: " + effect);
                }
            } else if (effect.startsWith("GoldVerlust:")) {
                try {
                    int gold = Integer.parseInt(effect.substring("GoldVerlust:".length()));
                    player.setGold(player.getGold() - gold);
                    System.out.println("Du hast " + gold + " Gold verloren. Neues Gold: " + player.getGold());
                } catch (NumberFormatException e) {
                    System.err.println("Fehler beim Parsen von GoldVerlust: " + effect);
                }
            } else if (effect.startsWith("LebenGewinn:")) {
                try {
                    int leben = Integer.parseInt(effect.substring("LebenGewinn:".length()));
                    player.setLeben(player.getLeben() + leben);
                    System.out.println("Du hast " + leben + " Leben regeneriert. Neues Leben: " + player.getLeben());
                } catch (NumberFormatException e) {
                    System.err.println("Fehler beim Parsen von LebenGewinn: " + effect);
                }
            } else if (effect.startsWith("LebenVerlust:")) {
                try {
                    int leben = Integer.parseInt(effect.substring("LebenVerlust:".length()));
                    player.setLeben(player.getLeben() - leben);
                    System.out.println("Du hast " + leben + " Leben verloren. Neues Leben: " + player.getLeben());
                    if (player.getLeben() <= 0) {
                        System.out.println("Deine Lebenspunkte sind auf 0 gesunken. Game Over!");
                        System.exit(0); // Game Over
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Fehler beim Parsen von LebenVerlust: " + effect);
                }
            } else if (effect.startsWith("Item:")) {
                String itemName = effect.substring("Item:".length());
                item newItem = new item(itemName, "Ein nützlicher Gegenstand.", 
                    random.nextInt(20) + 1, ItemTyp.SONSTIGES, 0);
                player.addItem(newItem);
                System.out.println("Du hast das Item '" + newItem.getName() + "' erhalten.");
            } else if (effect.startsWith("Erfahrung:")) {
                try {
                    int exp = Integer.parseInt(effect.substring("Erfahrung:".length()));
                    player.addErfahrung(exp);
                    System.out.println("Du hast " + exp + " Erfahrungspunkte erhalten.");
                } catch (NumberFormatException e) {
                    System.err.println("Fehler beim Parsen von Erfahrung: " + effect);
                }
            } else {
                System.out.println("Unbekannte Auswirkung: " + effect);
            }
        }
    }

    private charakter erstelleGegner(String name, int playerLevel) {
        String nameLower = name.toLowerCase();
        Random r = new Random();
        
        switch(nameLower) {
            case "goblin":
                return new charakter("Goblin", "Ein hinterhältiger Goblin", 
                    Math.max(1, playerLevel-1), 30 + r.nextInt(10), 15, 8+playerLevel, 3+playerLevel);
            case "drache":
                return new charakter("Drache", "Ein furchterregender Drache", 
                    playerLevel+2, 100 + r.nextInt(20), 50, 25+playerLevel, 15+playerLevel);
            case "untoter":
                return new charakter("Untoter", "Ein ruheloser Untoter", 
                    playerLevel, 45 + r.nextInt(15), 20, 12+playerLevel, 6+playerLevel);
            case "assassine":
                return new charakter("Elite-Assassine", "Ein tödlicher Meuchelmörder",
                    playerLevel+1, 60 + r.nextInt(20), 40, 20+playerLevel, 10+playerLevel);
            default:
                System.out.println("Warnung: Unbekannter Gegnertyp '" + name + "', erstelle Standardgegner");
                return new charakter("Unbekannter Gegner", "Ein mysteriöser Feind",
                    playerLevel, 50 + r.nextInt(20), 25, 15+playerLevel, 8+playerLevel);
        }
    }
}