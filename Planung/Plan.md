# Klassenstruktur mit Attributen und Methoden:

klasse 1: spiel.java - dort passiert das ganze game
methoden: 


klasse 2: spieler.java - dort wird der spieler erstellt mit allen attributen
attribute: name, level, leben, gold, inventory
methoden: 

Erledigt:
- levelUp() - aufleveln
---------------------------------------
Noch zu machen:

- addItem() - fügt ein item zum inventory hinzu
- removeItem() - entfernt ein item aus dem inventory

- getName() - gibt den Namen des Spielers zurück
- getLevel() - gibt den Level des Spielers zurück
- getLeben() - gibt die Leben des Spielers zurück
- getGold() - gibt das Gold des Spielers zurück

- setLevel() - setzt den Level des Spielers
- setLeben() - setzt die Leben des Spielers
- setGold() - setzt das Gold des Spielers

- treffeEntscheidung() - hier wird eine Entscheidung getroffen, die den Spielverlauf beeinflusst
- betreteNaechstenRaum() - hier wird der nächste Raum betreten


klasse 3: raeume.java - dort werden alle raeume erstellt sowie die events zugewiesen
attribute: name, beschreibung, events, items
methoden:
Erledigt:

---------------------------------------
Noch zu machen:

- addEvent() - fügt ein Event zum Raum hinzu
- addItem() - fügt ein Item zum Raum hinzu
- getName() - gibt den Namen des Raumes zurück
- getBeschreibung() - gibt die Beschreibung des Raumes zurück


klasse 4: charakter.java - dort werden alle charaktere gespeichert mit deren attributen usw.
attribute: name, beschreibung, level, leben, gold, inventory
methoden:
Erledigt:

---------------------------------------
Noch zu machen:

- getName() - gibt den Namen des Charakters zurück
- getBeschreibung() - gibt die Beschreibung des Charakters zurück
- getLevel() - gibt den Level des Charakters zurück
- getLeben() - gibt die Leben des Charakters zurück
- getGold() - gibt das Gold des Charakters zurück

- setLevel() - setzt den Level des Charakters
- setLeben() - setzt die Leben des Charakters
- setGold() - setzt das Gold des Charakters

klasse 5: item.java - dort werden alle items gespeichert mit deren attributen usw.
attribute: name, beschreibung, wert
methoden:
Erledigt:

- setName() - setzt den Namen des Items
- setBeschreibung() - setzt die Beschreibung des Items
- setWert() - setzt den Wert des Items
---------------------------------------
Noch zu machen:

- getName() - gibt den Namen des Items zurück
- getBeschreibung() - gibt die Beschreibung des Items zurück
- getWert() - gibt den Wert des Items zurück



klasse 6: event.java - dort werden alle events gespeichert mit deren attributen usw.
attribute: name, beschreibung, typ, auswirkungen
methoden:
Erledigt:

---------------------------------------
Noch zu machen:
- getName() - gibt den Namen des Events zurück
- getBeschreibung() - gibt die Beschreibung des Events zurück
- getTyp() - gibt den Typ des Events zurück
- getAuswirkungen() - gibt die Auswirkungen des Events zurück#

- setName() - setzt den Namen des Events
- setBeschreibung() - setzt die Beschreibung des Events
- setTyp() - setzt den Typ des Events
- setAuswirkungen() - setzt die Auswirkungen des Events

- addOption() - fügt eine Option zum Event hinzu
- getOptionen() - gibt die Optionen des Events zurück
- ausfuehren() - führt das Event aus und wendet die Auswirkungen auf den Spieler an
- auswahlTreffen() - trifft eine Auswahl basierend auf den Optionen des Events

klasse 7: inventar.java - dort wird das inventar des spielers verwaltet
attribute: items, gold
Erledigt:

---------------------------------------
Noch zu machen:
- addItem() - fügt ein Item zum Inventar hinzu
- removeItem() - entfernt ein Item aus dem Inventar
- getGold() - gibt das Gold im Inventar zurück
- setGold() - setzt das Gold im Inventar
- getItems() - gibt die Items im Inventar zurück

klasse 8: level.java - dort wird das level-system verwaltet
attribute: level, erfahrung, maxErfahrung
Erledigt:
- levelUp() - erhöht das Level des Spielers
- getLevel() - gibt das aktuelle Level des Spielers zurück
- getErfahrung() - gibt die aktuelle Erfahrung des Spielers zurück
- getMaxErfahrung() - gibt die maximale Erfahrung für das Level-Up zurück
- setLevel() - setzt das Level des Spielers
- setErfahrung() - setzt die Erfahrung des Spielers
---------------------------------------
Noch zu machen:
- addErfahrung() - fügt Erfahrung zum aktuellen Level hinzu

klasse 9: kampf.java - dort wird der kampf-mechanismus verwaltet
attribute: lebenspunkte, schaden, schutz
Erledigt:

- berechneSchaden() - berechnet den Schaden, der im Kampf verursacht wird
---------------------------------------
Noch zu machen:
- startKampf() - startet einen Kampf zwischen dem Spieler und einem Gegner

- berechneSchutz() - berechnet den Schutz des Spielers im Kampf
- checkKampfEnde() - prüft, ob der Kampf zu Ende ist (z.B. ob der Spieler oder der Gegner besiegt wurde)
- gewinneKampf() - führt die Aktionen aus, wenn der Spieler den Kampf gewinnt (z.B. Erfahrung, Items)
- verliereKampf() - führt die Aktionen aus, wenn der Spieler den Kampf verliert (z.B. Verlust von Leben, Items)


klasse 10: entscheidung.java - dort werden die entscheidungen des spielers gespeichert und ausgewertet
attribute: beschreibung, auswirkungen, typ, optionen
Erledigt:

---------------------------------------
Noch zu machen:
- addEntscheidung() - fügt eine Entscheidung zum Spieler hinzu
- getEntscheidungen() - gibt alle Entscheidungen des Spielers zurück
- auswerten() - wertet die Entscheidungen des Spielers aus und wendet die Auswirkungen an
- getTyp() - gibt den Typ der Entscheidung zurück
- setTyp() - setzt den Typ der Entscheidung
- getBeschreibung() - gibt die Beschreibung der Entscheidung zurück
- setBeschreibung() - setzt die Beschreibung der Entscheidung
- getAuswirkungen() - gibt die Auswirkungen der Entscheidung zurück
- setAuswirkungen() - setzt die Auswirkungen der Entscheidung

klasse 11: hilfe.java - dort werden die hilfefunktionen gespeichert
attribute: hilfeText
Erledigt:

---------------------------------------
Noch zu machen:
- getHilfeText() - gibt den Hilfetext zurück
- setHilfeText() - setzt den Hilfetext
- zeigeHilfe() - zeigt den Hilfetext an

