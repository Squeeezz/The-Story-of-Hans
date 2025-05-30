package quests;

import core.spieler;
import items.item;

public class QuestBelohnung {
    private int gold;
    private int erfahrung;
    private item[] items;
    private spieler spieler;

    public QuestBelohnung(spieler spieler, int gold, int erfahrung, item... items) {
        this.spieler = spieler;
        this.gold = gold;
        this.erfahrung = erfahrung;
        this.items = items;
    }

    public void aushaendigen() {
        if (gold > 0) {
            spieler.setGold(spieler.getGold() + gold);
            System.out.println("Du erhältst " + gold + " Gold als Belohnung!");
        }
        
        if (erfahrung > 0) {
            spieler.addErfahrung(erfahrung);
            System.out.println("Du erhältst " + erfahrung + " Erfahrungspunkte als Belohnung!");
        }

        if (items != null) {
            for (item item : items) {
                spieler.addItem(item);
                System.out.println("Du erhältst " + item.getName() + " als Belohnung!");
            }
        }
    }
}
