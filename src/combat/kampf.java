package combat;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import core.spieler;
import items.item;
import systems.charakter;
import systems.entscheidung;  // Neuer Import

public class kampf {
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    // Neue Attribute für Ressourcen und Status
    private int playerStamina;
    private int enemyStamina;

    private boolean playerDefending;
    private boolean enemyDefending;

    // Status Effekte
    private boolean playerStunned;
    private boolean enemyStunned;

    // Start des überarbeiteten Kampfsystems
    public boolean startKampf(spieler player, charakter enemy) {
        if (enemy == null || player == null) {
            System.out.println("Fehler: Spieler oder Gegner ist null.");
            return false;
        }
        if (enemy.characterName == null) enemy.characterName = "Unbekannter Gegner";
        if (enemy.characterInventory == null) enemy.characterInventory = new java.util.ArrayList<>();
        if (enemy.characterLeben <= 0) {
            System.out.println("Der Gegner ist bereits besiegt.");
            return true;
        }
        if (player.spielerleben <= 0) {
            System.out.println("Der Spieler ist bereits besiegt.");
            return false;
        }

        playerStamina = 100;
        enemyStamina = 100;
        playerDefending = false;
        enemyDefending = false;
        playerStunned = false;
        enemyStunned = false;

        System.out.println("\n--- KAMPF GEGEN " + enemy.characterName.toUpperCase() + " STARTET ---");
        System.out.println(player.spielerName + " (Leben: " + player.spielerleben + ", Ausdauer: " + playerStamina + ") vs. " +
                           enemy.characterName + " (Leben: " + enemy.characterLeben + ", Ausdauer: " + enemyStamina + ")");

        // Gegner werden stärker mit Spielerlevel
        if (player.getLevel() > 5) {
            enemy.setCharacterSchaden(enemy.getCharacterSchaden() + (player.getLevel() - 5) * 3);
            enemy.setCharacterSchutz(enemy.getCharacterSchutz() + (player.getLevel() - 5) * 2);
            enemy.characterLeben += (player.getLevel() - 5) * 20;
        }

        // Apply decision-based combat modifiers
        applyDecisionModifiers(player, enemy);

        // Chance auf Überraschungsangriff
        if (random.nextDouble() < 0.2) { // 20% Chance
            System.out.println(enemy.getName() + " überrascht dich mit einem Hinterhalt!");
            int ueberraschungsSchaden = enemy.getSchaden() * 2;
            player.setLeben(player.getLeben() - ueberraschungsSchaden);
            System.out.println("Du erleidest " + ueberraschungsSchaden + " Überraschungsschaden!");
            if (player.getLeben() <= 0) {
                verliereKampf(player);
                return false;
            }
        } // Fehlende schließende Klammer hier

        while (player.spielerleben > 0 && enemy.characterLeben > 0) {
            playerDefending = false; // Reset Verteidigung am Anfang des Zuges
            enemyDefending = false;

            if (playerStunned) {
                System.out.println("Du bist betäubt und kannst diesen Zug nicht handeln!");
                playerStunned = false;
            } else {
                System.out.println("\nDein Zug! Wähle eine Aktion:");
                System.out.println("1. Normaler Angriff");
                System.out.println("2. Kraftvoller Angriff (-20 Ausdauer, +50% Schaden)");
                System.out.println("3. Verteidigen (+30% Schutz für eine Runde)");
                System.out.println("4. Spezialfähigkeit");
                System.out.println("5. Item benutzen");
                System.out.println("6. Fluchtversuch");

                int choice = getPlayerChoice(1, 6);
                System.out.print("\033[H\033[2J");  
                System.out.flush();  
                boolean fluchtVersuch = false;

                switch (choice) {
                    case 1:
                        normalerAngriff(player, enemy);
                        break;
                    case 2:
                        if (playerStamina >= 20) {
                            kraftvollerAngriff(player, enemy);
                            playerStamina -= 20;
                        } else {
                            System.out.println("Nicht genug Ausdauer!");
                            continue;
                        }
                        break;
                    case 3:
                        playerDefend();
                        break;
                    case 4:
                        playerSpecial(player, enemy);
                        break;
                    case 5:
                        if (!player.getInventar().getItems().isEmpty()) {
                            useItem(player);
                        } else {
                            System.out.println("Keine Items verfügbar!");
                            continue;
                        }
                        break;
                    case 6:
                        fluchtVersuch = versucheFlucht(player, enemy);
                        if (fluchtVersuch) return false;
                        break;
                }
            }

            if (enemy.characterLeben <= 0) {
                gewinneKampf(player, enemy);
                return true;
            }

            if (enemyStunned) {
                System.out.println(enemy.characterName + " ist betäubt und kann diesen Zug nicht handeln!");
                enemyStunned = false;
            } else {
                gegnerZug(player, enemy);
            }

            if (player.spielerleben <= 0) {
                verliereKampf(player);
                return false;
            }

            // Regeneration und Statusanzeige
            playerStamina = Math.min(100, playerStamina + 15);
            enemyStamina = Math.min(100, enemyStamina + 15);

            zeigeKampfStatus(player, enemy);
        }
        return false;
    }

    private void zeigeKampfStatus(spieler player, charakter enemy) {
        System.out.println("\n--- AKTUELLER KAMPFSTATUS ---");
        System.out.println(player.spielerName + " - Leben: " + player.spielerleben + ", Ausdauer: " + playerStamina + (playerDefending ? " (Verteidigt)" : ""));
        System.out.println(enemy.characterName + " - Leben: " + enemy.characterLeben + ", Ausdauer: " + enemyStamina + (enemyDefending ? " (Verteidigt)" : ""));
        System.out.println("------------------------------");
    }

    private boolean versucheFlucht(spieler player, charakter enemy) {
        double fluchtChance = 0.3 + (player.getLevel() - enemy.getLevel()) * 0.1;
        if (random.nextDouble() < fluchtChance) {
            System.out.println("Flucht erfolgreich!");
            return true;
        }
        System.out.println("Fluchtversuch gescheitert!");
        return false;
    }

    private void normalerAngriff(spieler player, charakter enemy) {
        if (random.nextDouble() < 0.15) { // 15% Ausweichenchance
            System.out.println(enemy.getName() + " weicht aus!");
            return;
        }
        int baseDamage = player.getSchaden();
        boolean critical = random.nextDouble() < 0.15;
        int damage = baseDamage;
        if (critical) {
            damage *= 2;
            System.out.println("Kritischer Treffer!");
        }
        if (enemyDefending) {
            damage = (int)(damage * 0.7); // 30% weniger Schaden bei Verteidigung
        }
        damage = Math.max(0, damage - enemy.getCharacterSchutz());
        enemy.characterLeben -= damage;
        System.out.println("Du greifst an und verursachst " + damage + " Schaden.");
    }

    private void kraftvollerAngriff(spieler player, charakter enemy) {
        if (random.nextDouble() < 0.10) { // 10% Ausweichenchance
            System.out.println(enemy.getName() + " weicht deinem kraftvollen Angriff aus!");
            return;
        }
        int baseDamage = (int) Math.round(player.getSchaden() * 1.5);
        boolean critical = random.nextDouble() < 0.20; // etwas höhere Krit-Chance
        int damage = baseDamage;
        if (critical) {
            damage *= 2;
            System.out.println("Kritischer Treffer mit dem kraftvollen Angriff!");
        }
        if (enemyDefending) {
            damage = (int)(damage * 0.7);
        }
        damage = Math.max(0, damage - enemy.getCharacterSchutz());
        enemy.characterLeben -= damage;
        System.out.println("Du führst einen kraftvollen Angriff aus und verursachst " + damage + " Schaden.");
    }

    private void playerDefend() {
        System.out.println("Du verteidigst dich und reduzierst den nächsten Schaden.");
        playerDefending = true;
    }

    private void playerSpecial(spieler player, charakter enemy) {
        System.out.println("Du setzt deine Spezialfähigkeit ein!");
        // Beispiel: Spezialfähigkeit betäubt Gegner mit 30% Chance
        if (random.nextDouble() < 0.3) {
            enemyStunned = true;
            System.out.println(enemy.characterName + " ist betäubt und kann den nächsten Zug nicht handeln!");
        }
        int damage = player.getSchaden() * 3 / 2;
        if (enemyDefending) {
            damage = (int)(damage * 0.7);
        }
        damage = Math.max(0, damage - enemy.getCharacterSchutz());
        enemy.characterLeben -= damage;
        System.out.println("Spezialangriff verursacht " + damage + " Schaden.");
    }

    private void useItem(spieler player) {
        System.out.println("Wähle ein Item zum Benutzen:");
        for (int i = 0; i < player.getInventar().getItems().size(); i++) {
            item it = player.getInventar().getItems().get(i);
            System.out.println((i + 1) + ". " + it.getName() + " - " + it.getBeschreibung());
        }
        int choice = getPlayerChoice(1, player.getInventar().getItems().size());
        item selected = player.getInventar().getItems().get(choice - 1);
        player.benutzeItem(selected);
        System.out.println("Du hast " + selected.getName() + " benutzt.");
    }

    private void gegnerZug(spieler player, charakter enemy) {
        System.out.println("\nZug des Gegners: " + enemy.characterName);
        enemyDefending = false;
        if (enemyStamina >= 20 && random.nextDouble() < 0.3) {
            enemySpecial(player, enemy);
            enemyStamina -= 20;
        } else if (random.nextDouble() < 0.2) {
            enemyDefend(enemy);
        } else {
            enemyAttack(player, enemy);
        }
    }

    private void enemyAttack(spieler player, charakter enemy) {
        if (random.nextDouble() < 0.1) {
            System.out.println(enemy.characterName + " verfehlt den Angriff!");
            return;
        }
        int baseDamage = enemy.getSchaden();
        boolean critical = random.nextDouble() < 0.15;
        int damage = baseDamage;
        if (critical) {
            damage *= 2;
            System.out.println("Kritischer Treffer vom Gegner!");
        }
        if (playerDefending) {
            damage = (int)(damage * 0.7);
        }
        damage = Math.max(0, damage - player.getSchutz());
        player.spielerleben -= damage;
        System.out.println(enemy.characterName + " greift an und verursacht " + damage + " Schaden.");
    }

    private void enemyDefend(charakter enemy) {
        System.out.println(enemy.characterName + " verteidigt sich und reduziert den nächsten Schaden.");
        enemyDefending = true;
    }

    private void enemySpecial(spieler player, charakter enemy) {
        System.out.println(enemy.characterName + " setzt eine Spezialfähigkeit ein!");
        // Beispiel: Spezialfähigkeit betäubt Spieler mit 30% Chance
        if (random.nextDouble() < 0.3) {
            playerStunned = true;
            System.out.println(player.spielerName + " ist betäubt und kann den nächsten Zug nicht handeln!");
        }
        int damage = enemy.getSchaden() * 3 / 2;
        if (playerDefending) {
            damage = (int)(damage * 0.7);
        }
        damage = Math.max(0, damage - player.getSchutz());
        player.spielerleben -= damage;
        System.out.println("Spezialangriff verursacht " + damage + " Schaden.");
    }

    public void gewinneKampf(spieler player, charakter enemy) {
        System.out.println("\n" + player.spielerName + " hat den Kampf gegen " + enemy.characterName + " gewonnen!");
        int goldEarned = enemy.characterGold;
        player.setGold(player.getGold() + goldEarned);
        System.out.println("Du hast " + goldEarned + " Gold erhalten.");

        int expEarned = 10 * enemy.characterLevel;
        player.addErfahrung(expEarned);
        System.out.println("Du hast " + expEarned + " Erfahrungspunkte erhalten.");

        if (enemy.characterInventory != null && !enemy.characterInventory.isEmpty()) {
            int index = random.nextInt(enemy.characterInventory.size());
            String droppedItemName = enemy.characterInventory.get(index);
            if (droppedItemName != null && !droppedItemName.isEmpty()) {
                System.out.println(enemy.characterName + " hat '" + droppedItemName + "' fallen gelassen.");
                player.getInventar().addItem(new item(droppedItemName, "Ein Gegenstand vom Besiegten.", 10, null, expEarned));
            }
        }
    }

    public void verliereKampf(spieler player) {
        System.out.println("\n" + player.spielerName + " wurde besiegt!");
        System.out.println("Game Over!");
        System.exit(0);
    }

    private int getPlayerChoice(int min, int max) {
        int choice = -1;
        while (true) {
            System.out.print("Deine Wahl (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    scanner.nextLine(); // Zeilenumbruch entfernen
                    break;
                } else {
                    System.out.println("Bitte eine Zahl zwischen " + min + " und " + max + " eingeben.");
                }
            } else {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                scanner.nextLine(); // Ungültige Eingabe entfernen
            }
        }
        return choice;
    }

    private void applyDecisionModifiers(spieler player, charakter enemy) {
        List<entscheidung> recentDecisions = player.getRecentEntscheidungen(3);
        
        for (entscheidung e : recentDecisions) {
            if (e.getTyp().equals("Positiv")) {
                player.setSchaden((int)(player.getSchaden() * 1.1));
                player.setSchutz((int)(player.getSchutz() * 1.1));
            } else if (e.getTyp().equals("Negativ")) {
                enemy.setCharacterSchaden((int)(enemy.getCharacterSchaden() * 1.1));
                enemy.setCharacterSchutz((int)(enemy.getCharacterSchutz() * 1.1));
            }
        }
    }
}
