# Projektstruktur:
Das Projekt verwendet ein objektorientiertes Design mit folgenden implementierten Klassen:

## Kern-System:
1. **AbstractCharacter.java** - Basischarakterklasse
   - Abstrakte Basisklasse für alle Charaktere
   - Definiert grundlegende Attribute und Methoden
   - Implementiert Schadens- und Schutzberechnungen

2. **spiel.java** - Hauptspielklasse
   - Steuert den Spielablauf
   - Verwaltet 26 implementierte Räume
   - Koordiniert Spielerinteraktionen

3. **spieler.java** - Spielerklasse
   - Erbt von AbstractCharacter
   - Verwaltet Spielerattribute und Inventar
   - Implementiert Spieleraktionen

## Kampf- und Event-System:
4. **kampf.java** - Kampfsystem
   - Rundenbasiertes Kampfsystem mit Ausdauer
   - Implementiert verschiedene Kampfaktionen
   - Verwaltet Kampfausgänge

5. **event.java** - Ereignissystem
   - Verwaltet verschiedene Ereignistypen
   - Implementiert Event-Logik
   - Steuert Event-Auslöser

6. **EventTyp.java** - Event-Kategorisierung
   - Definiert verschiedene Ereignisarten
   - Strukturiert Event-System

## Item-System:
7. **item.java** - Itemverwaltung
   - Definiert Item-Eigenschaften
   - Implementiert Item-Funktionen

8. **ItemTyp.java** - Item-Kategorisierung
   - Definiert WAFFE, RÜSTUNG, etc.
   - Steuert Item-Verhalten

9. **inventar.java** - Inventarsystem
   - Verwaltet Spielerbesitz
   - Handhabt Items und Gold

## Quest-System:
10. **Quest.java** - Quest-Verwaltung
    - Implementiert Quest-Logik
    - Verwaltet Quest-Fortschritt

11. **QuestTyp.java** - Quest-Kategorisierung
    - Definiert Quest-Arten
    - Strukturiert Quest-System

12. **QuestZiel.java** - Quest-Ziele
    - Verwaltet Quest-Fortschrittsverfolgung
    - Prüft Zielerfüllung

13. **QuestBelohnung.java** - Belohnungssystem
    - Verwaltet Quest-Belohnungen
    - Implementiert Belohnungsverteilung

## Weitere Systeme:
14. **Crafting.java** - Crafting-System
    - Ermöglicht Item-Herstellung
    - Verwaltet Crafting-Rezepte

15. **charakter.java** - NPC-System
    - Erbt von AbstractCharacter
    - Implementiert NPC-Verhalten

16. **entscheidung.java** - Entscheidungssystem
    - Verwaltet Spielerentscheidungen
    - Implementiert Entscheidungskonsequenzen

17. **EntscheidungTyp.java** - Entscheidungs-Kategorisierung
    - Definiert Entscheidungsarten
    - Strukturiert Entscheidungssystem

18. **hilfe.java** - Hilfesystem
    - Bietet Spielhilfe
    - Erklärt Spielmechaniken

19. **level.java** - Levelsystem
    - Verwaltet Erfahrungspunkte
    - Implementiert Level-Ups

20. **raum.java** - Raumverwaltung
    - Definiert Spielbereiche
    - Verwaltet Raumereignisse

## Projektstruktur-Diagramm:
```
TheStoryOfHans/
├── Kern/
│   ├── AbstractCharacter.java
│   ├── spiel.java
│   └── spieler.java
├── Kampf-Event/
│   ├── kampf.java
│   ├── event.java
│   └── EventTyp.java
├── Items/
│   ├── item.java
│   ├── ItemTyp.java
│   └── inventar.java
├── Quests/
│   ├── Quest.java
│   ├── QuestTyp.java
│   ├── QuestZiel.java
│   └── QuestBelohnung.java
└── Systeme/
    ├── Crafting.java
    ├── charakter.java
    ├── entscheidung.java
    ├── EntscheidungTyp.java
    ├── hilfe.java
    ├── level.java
    └── raum.java
```
