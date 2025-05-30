package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import items.item;
import items.ItemTyp;
import systems.charakter;
import systems.entscheidung;
import systems.raum;
import combat.event;
import combat.kampf;

public class spiel {
    public spieler spieler;
    public raum aktuellerRaum;
    public ArrayList<raum> alleRaeume;
    public Scanner scanner;
    public Random random;
    private boolean hatSichUmgesehen; // Flag to track if player has looked around in current room

    public spiel() {
        // Initialisierung desSpielers
        this.spieler = new spieler("Hans", 1, 100, 50); // Name, Start-Level, Leben, Gold
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.alleRaeume = new ArrayList<>();
        erstelleRaeume(); // Methode zum Erstellen aller Räume und deren Inhalte
        this.aktuellerRaum = wähleStartRaum(); // Zufälliger Startraum
        this.hatSichUmgesehen = false;
    }

    // Methode zum Erstellen aller Räume und Hinzufügen von Events/Items
    private void erstelleRaeume() {
        // Raum 1: Verlassenes Dorf
        raum dorf = new raum("Verlassenes Dorf", "Ein altes, verfallenes Dorf, das von einer unheimlichen Stille erfüllt ist.");
        dorf.addItem(new item("Rostiges Schwert", "Ein altes, rostiges Schwert. Nicht sehr effektiv.", 5, ItemTyp.WAFFE, 3));
        dorf.addItem(new item("Heiltrank", "Stellt 20 Lebenspunkte wieder her.", 15, ItemTyp.TRANK, 20));
        dorf.addEvent(new event("Dorfbrunnen", "Du entdeckst einen alten Brunnen.", "Fund", "Du findest einige Münzen im Brunnen."));
        dorf.addEvent(new event("Verdächtiges Geräusch", "Ein leises Knurren kommt aus einem verfallenen Haus.", "Entscheidung", Arrays.asList(
            new entscheidung("Das Haus betreten", "Kampf:Goblin;Erfahrung:20", "Gefährlich", new String[]{"Kämpfen", "Weglaufen"}),
            new entscheidung("Vorsichtig vorbeischleichen", "Erfahrung:5", "Neutral", new String[]{"Schleichen", "Ignorieren"})
        )));
        dorf.addEvent(new event("Wandernder Händler", "Ein alter Händler sitzt am Wegesrand.", "Begegnung", "Der Händler bietet dir Waren an."));
        alleRaeume.add(dorf);

        // Raum 2: Dunkler Wald
        raum wald = new raum("Dunkler Wald", "Hohe Bäume versperren das Sonnenlicht, der Pfad ist kaum zu erkennen.");
        wald.addItem(new item("Waldbeeren", "Essbare Beeren, stellen ein wenig Leben wieder her.", 3, ItemTyp.ESSEN, 5));
        // Event-Erstellung korrigieren - "Kampf" statt "kampf" verwenden
        wald.addEvent(new event("Wolfsgeheul", "Ein hungriger Wolf greift dich an!", "Kampf",
            new charakter("Wilder Wolf", "Ein hungriger Wolf.", 2, 30, 15, 12, 3)));
        wald.addEvent(new event("Verlorener Rucksack", "Du findest einen Rucksack, der aussieht, als wäre er kürzlich verloren worden.", "Entscheidung", Arrays.asList(
            new entscheidung("Den Rucksack öffnen", "GoldGewinn:30;Item:Amulett", "Glück", new String[]{"Öffnen", "Stehenlassen"}),
            new entscheidung("Den Rucksack ignorieren", "Erfahrung:2", "Neutral", new String[]{"Ignorieren", "Melden"})
        )));
        alleRaeume.add(wald);

        // Raum 3: Verfallene Ruinen
        raum ruinen = new raum("Verfallene Ruinen", "Die Überreste einer alten Festung, gezeichnet von den Schlachten vergangener Zeiten.");
        ruinen.addItem(new item("Antike Münze", "Eine wertvolle alte Münze.", 25, ItemTyp.SONSTIGES, 0));
        ruinen.addEvent(new event("Skelett Krieger", "Ein knochiger Krieger erhebt sich aus dem Staub.", "Kampf",
            new charakter("Skelett Krieger", "Ein untoter Wächter der Ruinen.", 3, 40, 20, 15, 5)));
        ruinen.addEvent(new event("Geheimtür", "Du entdeckst eine lose Steinplatte in der Wand.", "Entscheidung", Arrays.asList(
            new entscheidung("Steinplatte verschieben", "GoldGewinn:50;Erfahrung:15", "Erfolg", new String[]{"Verschieben", "Ignorieren"}),
            new entscheidung("Lieber nicht anfassen", "Neutral", "Neutral", new String[]{})
        )));
        alleRaeume.add(ruinen);

        // Raum 4: Verlassene Mine
        raum mine = new raum("Verlassene Mine", "Eine dunkle, verlassene Mine, in der einst wertvolle Erze abgebaut wurden.");
        mine.addItem(new item("Eisenerz", "Ein Stück rohes Eisenerz.", 10, ItemTyp.SONSTIGES, 0));
        mine.addEvent(new event("Einsturzgefahr", "Die Mine wirkt instabil und gefährlich.", "Entscheidung", Arrays.asList(
            new entscheidung("Vorsichtig weitergehen", "Erfahrung:10", "Vorsicht", new String[]{"Weitergehen", "Zurückziehen"}),
            new entscheidung("Schnell durchrennen", "Kampf:Steinmonster;Erfahrung:25", "Gefahr", new String[]{"Kämpfen", "Fliehen"})
        )));
        alleRaeume.add(mine);

        // Raum 5: Mystischer See
        raum see = new raum("Mystischer See", "Ein ruhiger See, dessen Wasser in der Sonne glitzert.");
        see.addItem(new item("Magischer Fisch", "Ein Fisch mit schimmernden Schuppen.", 20, ItemTyp.ESSEN, 10));
        see.addEvent(new event("Fischer", "Ein alter Fischer sitzt am Ufer und erzählt Geschichten.", "Begegnung", "Der Fischer bietet dir einen magischen Trank an."));
        see.addEvent(new event("Wassergeist", "Ein geheimnisvoller Wassergeist erscheint.", "Entscheidung", Arrays.asList(
            new entscheidung("Dem Geist helfen", "Erfahrung:30", "Freundlich", new String[]{"Helfen", "Ignorieren"}),
            new entscheidung("Den Geist vertreiben", "Kampf:Wassergeist;Erfahrung:40", "Feindlich", new String[]{"Kämpfen", "Fliehen"})
        )));
        alleRaeume.add(see);

        // Raum 6: Alte Bibliothek
        raum bibliothek = new raum("Alte Bibliothek", "Ein Raum voller verstaubter Bücher und Geheimnisse.");
        bibliothek.addItem(new item("Altes Buch", "Ein Buch mit mysteriösen Symbolen.", 15, ItemTyp.SONSTIGES, 0));
        bibliothek.addEvent(new event("Geheimnisvolle Schriftrolle", "Du findest eine alte Schriftrolle.", "Fund", "Die Schriftrolle enthält einen Zauberspruch."));
        bibliothek.addEvent(new event("Wächter", "Ein magischer Wächter bewacht die Bibliothek.", "Kampf", new charakter("Magischer Wächter", "Ein mächtiger Beschützer der Bibliothek.", 4, 50, 25, 20, 10)));
        alleRaeume.add(bibliothek);

        // Neue Räume mit viel mehr Inhalt

        // Raum 7: Verfluchter Friedhof
        raum friedhof = new raum("Verfluchter Friedhof", "Ein düsterer Friedhof, auf dem die Schatten der Vergangenheit wandeln.");
        friedhof.addItem(new item("Verrostetes Amulett", "Ein Amulett, das von dunkler Magie durchdrungen ist.", 30, ItemTyp.SONSTIGES, 0));
        friedhof.addEvent(new event("Ruhestörer", "Ein untoter Geist erhebt sich aus seinem Grab.", "Kampf", new charakter("Untoter Geist", "Ein ruheloser Geist, der den Friedhof heimsucht.", 4, 45, 30, 18, 8)));
        friedhof.addEvent(new event("Geisterhafte Erscheinung", "Eine geisterhafte Gestalt erscheint und bietet dir einen Handel an.", "Entscheidung", Arrays.asList(
            new entscheidung("Handel annehmen", "GoldGewinn:50;Erfahrung:20", "Positiv", new String[]{"Annehmen", "Ablehnen"}),
            new entscheidung("Handel ablehnen", "Erfahrung:5", "Neutral", new String[]{"Ablehnen"})
        )));
        alleRaeume.add(friedhof);

        // Raum 8: Verlassene Hütte
        raum huette = new raum("Verlassene Hütte", "Eine kleine Hütte, die verlassen und verfallen ist.");
        huette.addItem(new item("Altes Messer", "Ein stumpfes Messer, das kaum Schaden verursacht.", 7, ItemTyp.WAFFE, 4));
        huette.addEvent(new event("Versteckter Schatz", "Du findest eine versteckte Truhe unter dem Boden.", "Fund", "Die Truhe enthält einige Goldmünzen und einen Heiltrank."));
        huette.addEvent(new event("Wilder Bär", "Ein wütender Bär blockiert den Ausgang.", "Kampf", new charakter("Wilder Bär", "Ein großer, aggressiver Bär.", 5, 60, 40, 25, 12)));
        alleRaeume.add(huette);

        // Raum 9: Verzauberter Garten
        raum garten = new raum("Verzauberter Garten", "Ein Garten voller magischer Pflanzen und geheimnisvoller Kreaturen.");
        garten.addItem(new item("Magische Blume", "Eine Blume mit heilenden Eigenschaften.", 20, ItemTyp.ESSEN, 15));
        garten.addEvent(new event("Fabelhafte Kreatur", "Eine freundliche Kreatur nähert sich dir.", "Begegnung", "Die Kreatur schenkt dir einen magischen Trank."));
        garten.addEvent(new event("Giftige Pflanze", "Du berührst eine giftige Pflanze.", "Entscheidung", Arrays.asList(
            new entscheidung("Pflanze entfernen", "Erfahrung:10", "Positiv", new String[]{"Entfernen", "Ignorieren"}),
            new entscheidung("Pflanze ignorieren", "LebenVerlust:10", "Negativ", new String[]{"Ignorieren"})
        )));
        alleRaeume.add(garten);

        // Raum 10: Verlassene Festung
        raum festung = new raum("Verlassene Festung", "Eine alte Festung, die von Räubern besetzt ist.");
        festung.addItem(new item("Gestohlenes Schwert", "Ein Schwert, das von Räubern gestohlen wurde.", 35, ItemTyp.WAFFE, 15));
        festung.addEvent(new event("Räuberbande", "Eine Gruppe von Räubern greift dich an.", "Kampf", new charakter("Räuberanführer", "Der Anführer der Räuberbande.", 6, 70, 50, 30, 15)));
        festung.addEvent(new event("Versteckter Geheimgang", "Du findest einen geheimen Durchgang.", "Entscheidung", Arrays.asList(
            new entscheidung("Durchgang betreten", "Erfahrung:25", "Positiv", new String[]{"Betreten", "Ignorieren"}),
            new entscheidung("Weitergehen", "Neutral", "Neutral", new String[]{"Weitergehen"})
        )));
        alleRaeume.add(festung);

        // Raum 11: Verzauberte Höhle
        raum hoehe = new raum("Verzauberte Höhle", "Eine Höhle, die von magischer Energie durchdrungen ist.");
        hoehe.addItem(new item("Kristall", "Ein leuchtender Kristall mit mystischen Kräften.", 40, ItemTyp.SONSTIGES, 0));
        hoehe.addEvent(new event("Magischer Wächter", "Ein magischer Wächter bewacht die Höhle.", "Kampf", new charakter("Magischer Wächter", "Ein mächtiger Beschützer der Höhle.", 7, 80, 60, 35, 20)));
        hoehe.addEvent(new event("Rätselhafte Inschrift", "Eine alte Inschrift fordert dich heraus.", "Entscheidung", Arrays.asList(
            new entscheidung("Inschrift entziffern", "Erfahrung:30", "Positiv", new String[]{"Entziffern", "Ignorieren"}),
            new entscheidung("Inschrift ignorieren", "Neutral", "Neutral", new String[]{"Ignorieren"})
        )));
        alleRaeume.add(hoehe);

        // Raum 12: Verfallener Tempel
        raum tempel = new raum("Verfallener Tempel", "Ein alter Tempel, der von der Zeit gezeichnet ist.");
        tempel.addItem(new item("Heilige Reliquie", "Eine wertvolle Reliquie mit heiliger Kraft.", 50, ItemTyp.SONSTIGES, 0));
        tempel.addEvent(new event("Tempelwächter", "Ein mächtiger Wächter schützt den Tempel.", "Kampf", new charakter("Tempelwächter", "Ein starker Beschützer des Tempels.", 8, 90, 70, 40, 25)));
        tempel.addEvent(new event("Verborgene Kammer", "Du findest eine verborgene Kammer.", "Entscheidung", Arrays.asList(
            new entscheidung("Kammer betreten", "GoldGewinn:100;Erfahrung:50", "Positiv", new String[]{"Betreten", "Ignorieren"}),
            new entscheidung("Kammer meiden", "Neutral", "Neutral", new String[]{"Meiden"})
        )));
        alleRaeume.add(tempel);

        // Raum 13: Drachen Höhle
        raum drachenHoehle = new raum("Drachen Höhle", "Eine riesige Höhle mit geschmolzenen Felsen und Goldschätzen.");
        drachenHoehle.addItem(new item("Drachenschuppe", "Eine massive Schuppe eines Drachen.", 100, ItemTyp.RUESTUNG, 25));
        drachenHoehle.addEvent(new event("Drache", "Ein gewaltiger Drache erwacht!", "Kampf", 
            new charakter("Uralter Drache", "Ein mächtiger Drache", 10, 200, 500, 50, 30)));
        alleRaeume.add(drachenHoehle);

        // Raum 14: Giftsumpf
        raum giftsumpf = new raum("Giftsumpf", "Ein tödlicher Sumpf voller giftiger Dämpfe.");
        giftsumpf.addItem(new item("Gegengift", "Ein starkes Gegengift.", 30, ItemTyp.TRANK, 40));
        giftsumpf.addEvent(new event("Giftiger Nebel", "Giftige Dämpfe steigen auf.", "Gefahr", "LebenVerlust:15"));
        giftsumpf.addEvent(new event("Sumpfmonster", "Ein Monster aus dem Sumpf greift an!", "Kampf",
            new charakter("Sumpfkreatur", "Eine giftige Kreatur", 6, 80, 40, 30, 15)));
        alleRaeume.add(giftsumpf);

        // Raum 15: Dämonische Schmiede
        raum daemonenSchmiede = new raum("Dämonische Schmiede", "Eine höllische Schmiede mit Lavaströmen und dämonischen Artefakten.");
        daemonenSchmiede.addItem(new item("Dämonenschwert", "Ein unheiliges Schwert mit enormer Macht.", 200, ItemTyp.WAFFE, 45));
        daemonenSchmiede.addEvent(new event("Dämonenschmied", "Ein gewaltiger Dämon schmiedet Waffen!", "Kampf",
            new charakter("Höllenschmied", "Ein dämonischer Schmiedemeister", 12, 250, 1000, 60, 40)));
        alleRaeume.add(daemonenSchmiede);

        // Raum 16: Geisterlabyrinth
        raum labyrinth = new raum("Geisterlabyrinth", "Ein verwirrendes Labyrinth voller böser Geister.");
        labyrinth.addItem(new item("Seelenstein", "Ein mystischer Stein der Untoten.", 150, ItemTyp.RUESTUNG, 35));
        labyrinth.addEvent(new event("Irrgänge", "Du verlierst dich im Labyrinth.", "Gefahr", "LebenVerlust:25;GoldVerlust:50"));
        labyrinth.addEvent(new event("Geistermeister", "Der Herr der Geister erscheint!", "Kampf",
            new charakter("Geistermeister", "Ein mächtiger Geist", 9, 150, 300, 45, 25)));
        alleRaeume.add(labyrinth);

        // Raum 17: Abgrund der Verdammnis
        raum abgrund = new raum("Abgrund der Verdammnis", "Ein endloser Abgrund voller dunkler Energie.");
        abgrund.addItem(new item("Chaosrüstung", "Eine Rüstung aus purer dunkler Energie.", 300, ItemTyp.RUESTUNG, 50));
        abgrund.addEvent(new event("Chaoslord", "Ein Wesen aus purem Chaos manifestiert sich!", "Kampf",
            new charakter("Chaoslord", "Herrscher des Abgrunds", 15, 400, 2000, 80, 60)));
        alleRaeume.add(abgrund);

        // Raum 18: Höllenpforte
        raum hoellenPforte = new raum("Höllenpforte", "Ein gewaltiges Tor aus brennendem Obsidian.");
        hoellenPforte.addItem(new item("Höllenfeuer-Amulett", "Ein Amulett, das vor Flammen schützt.", 250, ItemTyp.RUESTUNG, 40));
        hoellenPforte.addEvent(new event("Dämonenritual", "Ein dunkles Ritual findet statt.", "Gefahr", "LebenVerlust:40;GoldVerlust:100"));
        hoellenPforte.addEvent(new event("Höllenfürst", "Der Fürst der Hölle erscheint!", "Kampf",
            new charakter("Höllenfürst", "Herrscher der Unterwelt", 20, 500, 5000, 100, 70)));
        alleRaeume.add(hoellenPforte);

        // Raum 19: Dunkler Thron
        raum thron = new raum("Dunkler Thron", "Ein gewaltiger Thronsaal aus schwarzem Marmor.");
        thron.addItem(new item("Schattenkrone", "Die Krone des dunklen Herrschers.", 1000, ItemTyp.RUESTUNG, 100));
        thron.addEvent(new event("Schattenkönig", "Der unsterbliche Schattenkönig erhebt sich!", "Kampf",
            new charakter("Schattenkönig", "Der ultimative Gegner", 25, 1000, 10000, 150, 100)));
        alleRaeume.add(thron);

        // Raum 20: Seelenschmiede
        raum seelenschmiede = new raum("Seelenschmiede", "Eine alptraumhafte Schmiede, wo Seelen geschmiedet werden.");
        seelenschmiede.addItem(new item("Seelenfresser", "Ein Schwert, das mit den Schreien der Verdammten durchdrungen ist.", 500, ItemTyp.WAFFE, 70));
        seelenschmiede.addEvent(new event("Seelenfolter", "Gequälte Seelen umkreisen dich.", "Gefahr", "LebenVerlust:50;GoldVerlust:200"));
        seelenschmiede.addEvent(new event("Seelenschmied", "Der Schmied verdammter Seelen!", "Kampf",
            new charakter("Seelenschmied", "Meister der Seelenfolter", 18, 600, 3000, 90, 50)));
        alleRaeume.add(seelenschmiede);

        // Raum 21: Dimensionsriss
        raum dimensionsriss = new raum("Dimensionsriss", "Ein Riss in der Realität, wo Albträume real werden.");
        dimensionsriss.addItem(new item("Realitätsbrecher", "Eine Waffe, die die Realität selbst spaltet.", 800, ItemTyp.WAFFE, 90));
        dimensionsriss.addEvent(new event("Realitätsverfall", "Die Wirklichkeit zerfällt um dich herum.", "Gefahr", "LebenVerlust:75"));
        dimensionsriss.addEvent(new event("Dimensionswächter", "Ein Wesen jenseits der Realität!", "Kampf",
            new charakter("Dimensionswächter", "Hüter zwischen den Welten", 22, 800, 8000, 120, 80)));
        alleRaeume.add(dimensionsriss);

        // Raum 22: Schattendimension
        raum schattenDimension = new raum("Schattendimension", "Eine Welt aus lebendigen Schatten und Finsternis.");
        schattenDimension.addItem(new item("Schattenkristall", "Ein Kristall aus reiner Dunkelheit.", 1500, ItemTyp.WAFFE, 120));
        schattenDimension.addEvent(new event("Ewige Finsternis", "Die Dunkelheit verschlingt dich.", "Gefahr", "LebenVerlust:100;GoldVerlust:500"));
        schattenDimension.addEvent(new event("Schattendrache", "Der Herrscher der Schattendimension!", "Kampf",
            new charakter("Schattendrache", "Der mächtigste aller Drachen", 30, 2000, 15000, 200, 150)));
        alleRaeume.add(schattenDimension);

        // Raum 23: Urgötter-Tempel
        raum urgoetterTempel = new raum("Urgötter-Tempel", "Ein uralter Tempel der ersten Götter.");
        urgoetterTempel.addItem(new item("Göttliche Klinge", "Das legendäre Schwert der Urgötter.", 2000, ItemTyp.WAFFE, 180));
        urgoetterTempel.addEvent(new event("Göttliche Prüfung", "Die Urgötter stellen dich auf die Probe.", "Gefahr", "LebenVerlust:150;GoldVerlust:1000"));
        urgoetterTempel.addEvent(new event("Göttlicher Wächter", "Der Avatar der Urgötter!", "Kampf",
            new charakter("Göttlicher Avatar", "Verkörperung göttlicher Macht", 35, 3000, 20000, 250, 200)));
        alleRaeume.add(urgoetterTempel);

        // Raum 24: Weltenzerstörer
        raum weltenzerstoerer = new raum("Weltenzerstörer", "Das Ende aller Realitäten.");
        weltenzerstoerer.addItem(new item("Weltenbrecher-Rüstung", "Die ultimative Rüstung.", 5000, ItemTyp.RUESTUNG, 250));
        weltenzerstoerer.addEvent(new event("Realitätsauflösung", "Die Welt zerfällt um dich herum.", "Gefahr", "LebenVerlust:200;GoldVerlust:2000"));
        weltenzerstoerer.addEvent(new event("Weltenzerstörer", "Der Vernichter aller Welten!", "Kampf",
            new charakter("Weltenzerstörer", "Der finale Endgegner", 40, 5000, 50000, 500, 300)));
        alleRaeume.add(weltenzerstoerer);

        // Raum 25: Chaos-Nexus
        raum chaosNexus = new raum("Chaos-Nexus", "Der Ursprung allen Chaos und der Verderbnis.");
        chaosNexus.addItem(new item("Chaosherz", "Das pulsierende Herz des Chaos selbst.", 8000, ItemTyp.RUESTUNG, 400));
        chaosNexus.addEvent(new event("Chaosflut", "Pure Chaosenergie durchströmt dich!", "Gefahr", "LebenVerlust:300;GoldVerlust:3000"));
        chaosNexus.addEvent(new event("Chaosfürst", "Der Herrscher des Chaos!", "Kampf",
            new charakter("Chaosfürst", "Verkörperung des Chaos", 45, 8000, 100000, 800, 400)));
        alleRaeume.add(chaosNexus);

        // Raum 26: Ewiger Abgrund
        raum ewigerAbgrund = new raum("Ewiger Abgrund", "Der tiefste Punkt aller Existenz.");
        ewigerAbgrund.addItem(new item("Klinge der Unendlichkeit", "Eine Waffe jenseits der Vorstellungskraft.", 10000, ItemTyp.WAFFE, 500));
        ewigerAbgrund.addEvent(new event("Absolute Leere", "Die Leere zerreißt deine Seele.", "Gefahr", "LebenVerlust:500;GoldVerlust:5000"));
        ewigerAbgrund.addEvent(new event("Ewiger", "Das ultimative Wesen!", "Kampf",
            new charakter("Der Ewige", "Das mächtigste Wesen aller Realitäten", 50, 10000, 200000, 1000, 500)));
        alleRaeume.add(ewigerAbgrund);
    }

    private raum wähleStartRaum() {
        // Verbesserte Startlogik - Nur sichere Startbereiche
        ArrayList<raum> sichereRaeume = new ArrayList<>();
        for (raum r : alleRaeume) {
            if (istSichererStartRaum(r)) {
                sichereRaeume.add(r);
            }
        }
        return sichereRaeume.get(random.nextInt(sichereRaeume.size()));
    }

    private boolean istSichererStartRaum(raum r) {
        String name = r.getName();
        return name.equals("Verlassenes Dorf") ||
               name.equals("Dunkler Wald") ||
               name.equals("Mystischer See") ||
               name.equals("Alte Bibliothek") ||
               name.equals("Verzauberter Garten");
    }

    // Spielstart-Methode
    public void starteSpiel() {
        System.out.println("Willkommen zu 'The Story of Hans'!");
        System.out.println("Du bist Hans, ein junger Abenteurer, der sich auf eine Reise durch eine gefährliche Welt begibt.");
        System.out.println("Dein Abenteuer beginnt in " + aktuellerRaum.getName() + ".");
        System.out.println(aktuellerRaum.getBeschreibung());

        spielSchleife();
    }

    // Die Hauptspielschleife
    private void spielSchleife() {
        while (true) {
            System.out.println("\nWas möchtest du tun?");
            System.out.println("1. Gehen");          // Wichtigste Aktion zuerst
            System.out.println("2. Umsehen");        // Zweithäufigste Aktion
            System.out.println("3. Suchen");         // Dritthäufigste Aktion
            System.out.println("4. Status anzeigen");
            System.out.println("5. Inventar anzeigen");
            System.out.println("6. Item benutzen");
            System.out.println("7. Item ausrüsten");
            System.out.println("8. Hilfe");
            System.out.println("9. Spiel beenden");

            int choice = getPlayerChoice(1, 9);
            
            switch (choice) {
                case 1: // Gehen
                    betreteNaechstenRaum();
                    break;
                case 2: // Umsehen
                    if (!hatSichUmgesehen) {
                        System.out.println("\nDu befindest dich in " + aktuellerRaum.getName() + ".");
                        System.out.println(aktuellerRaum.getBeschreibung());
                        aktuellerRaum.triggerRandomEvent(spieler);
                        hatSichUmgesehen = true;
                    } else {
                        System.out.println("\nEs gibt nichts Neues zu sehen.");
                    }
                    break;
                case 3: // Suchen
                    aktuellerRaum.searchRoomForItems(spieler);
                    break;
                case 4: // Status
                    zeigeSpielerStatus();
                    break;
                case 5: // Inventar
                    zeigeInventar();
                    break;
                case 6: // Item benutzen
                    zeigeUndBenutzeItems();
                    break;
                case 7: // Item ausrüsten
                    zeigeUndRuesteAus();
                    break;
                case 8: // Hilfe
                    zeigeHilfe();
                    break;
                case 9: // Beenden
                    System.out.println("Spiel wird beendet. Auf Wiedersehen!");
                    return;
            }

            if (spieler.getLeben() <= 0) {
                System.out.println("GAME OVER - Du bist gestorben!");
                return;
            }
        }
    }

    // Verbesserte Fehlerbehandlung für Items
    private void zeigeUndBenutzeItems() {
        ArrayList<item> items = spieler.getInventar().getItems();
        if (items.isEmpty()) {
            System.out.println("Keine Items zum Benutzen vorhanden.");
            return;
        }

        try {
            System.out.println("\nWelches Item möchtest du benutzen?");
            for (int i = 0; i < items.size(); i++) {
                item currentItem = items.get(i);
                String itemTyp = currentItem.getItemTyp() != null ? " [" + currentItem.getItemTyp() + "]" : "";
                System.out.println((i+1) + ". " + currentItem.getName() + itemTyp);
            }
            
            int choice = getPlayerChoice(1, items.size());
            item selectedItem = items.get(choice-1);
            
            // Prüfe ob Item benutzbar
            if (selectedItem.getItemTyp() == ItemTyp.TRANK || selectedItem.getItemTyp() == ItemTyp.ESSEN) {
                spieler.benutzeItem(selectedItem);
            } else {
                System.out.println("Dieses Item kann nicht benutzt werden!");
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Benutzen des Items: " + e.getMessage());
        }
    }

    private void zeigeUndRuesteAus() {
        ArrayList<item> items = spieler.getInventar().getItems();
        if (items.isEmpty()) {
            System.out.println("Keine Items zum Ausrüsten vorhanden.");
            return;
        }

        System.out.println("\nWelches Item möchtest du ausrüsten?");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ". " + items.get(i).getName());
        }
        
        int choice = getPlayerChoice(1, items.size());
        spieler.ausruesten(items.get(choice-1));
    }

    private int getPlayerChoice(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Wähle eine Option (" + min + "-" + max + "): ");
                int choice = Integer.parseInt(scanner.nextLine());
                System.out.print("\033[H\033[2J");  
                System.out.flush();  
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Bitte eine Zahl zwischen " + min + " und " + max + " eingeben.");
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }
    }
    // betreteNaechstenRaum() - hier wird der nächste Raum betreten
    private void betreteNaechstenRaum() {
        raum alterRaum = aktuellerRaum;
        
        // Verbesserte Raumauswahl basierend auf Spielerlevel
        int spielerLevel = spieler.getLevel();
        int raumIndex;
        
        if (spielerLevel >= 20) {
            // Höchste Schwierigkeit: Zugang zu allen Räumen
            raumIndex = random.nextInt(alleRaeume.size());
        } else if (spielerLevel >= 15) {
            // Schwer: Räume 1-18
            raumIndex = random.nextInt(18);
        } else if (spielerLevel >= 10) {
            // Mittel: Räume 1-15
            raumIndex = random.nextInt(15);
        } else if (spielerLevel >= 5) {
            // Leicht-Mittel: Räume 1-10
            raumIndex = random.nextInt(10);
        } else {
            // Anfänger: Nur die ersten 5 Räume
            raumIndex = random.nextInt(5);
        }

        aktuellerRaum = alleRaeume.get(raumIndex);

        // Verhindere gleichen Raum zweimal
        while (aktuellerRaum == alterRaum) {
            raumIndex = (raumIndex + 1) % alleRaeume.size();
            aktuellerRaum = alleRaeume.get(raumIndex);
        }

        hatSichUmgesehen = false;

        // Prüfe Spielerlevel für Warnmeldung
        if (istRaumZuGefaehrlich(aktuellerRaum, spielerLevel)) {
            System.out.println("\nWARNUNG: Dieser Ort könnte für dich zu gefährlich sein!");
        }

        System.out.println("\nDu verlässt " + alterRaum.getName() + " und betrittst " + aktuellerRaum.getName() + ".");
        System.out.println(aktuellerRaum.getBeschreibung());

        aktuellerRaum.triggerRandomEvent(spieler);
        pruefeZufallsEreignis();
    }

    private boolean istRaumZuGefaehrlich(raum raum, int spielerLevel) {
        String name = raum.getName();
        if (name.equals("Weltenzerstörer")) return spielerLevel < 35;
        if (name.equals("Urgötter-Tempel")) return spielerLevel < 30;
        if (name.equals("Schattendimension")) return spielerLevel < 25;
        if (name.equals("Dunkler Thron")) return spielerLevel < 20;
        if (name.equals("Höllenpforte")) return spielerLevel < 18;
        if (name.equals("Dimensionsriss")) return spielerLevel < 15;
        if (name.equals("Drachen Höhle")) return spielerLevel < 10;
        if (name.equals("Seelenschmiede")) return spielerLevel < 16;
        if (name.equals("Chaos-Nexus")) return spielerLevel < 40;
        if (name.equals("Ewiger Abgrund")) return spielerLevel < 45;
        return false;
    }

    // Neue Methode für zufällige gefährliche Ereignisse
    private void pruefeZufallsEreignis() {
        // Erhöhte Chance auf Events bei höherem Level
        double eventChance = 0.2 + (spieler.getLevel() * 0.01); // Max 50% bei Level 30
        if (random.nextDouble() < eventChance) {
            System.out.println("\n=== PLÖTZLICHES EREIGNIS ===");
            int ereignisTyp = random.nextInt(10); // Erweitert auf 10 verschiedene Ereignisse
            switch (ereignisTyp) {
                case 0:
                    int schaden = random.nextInt(15) + 5;
                    spieler.setLeben(spieler.getLeben() - schaden);
                    System.out.println("Du stolperst und verlierst " + schaden + " Lebenspunkte!");
                    break;
                case 1:
                    charakter bandit = new charakter("Bandit", "Ein hinterhältiger Räuber", 
                        spieler.getLevel(), 40, 20, 15, 8);
                    new kampf().startKampf(spieler, bandit);
                    break;
                case 2:
                    if (spieler.getGold() > 0) {
                        int verlust = Math.min(spieler.getGold(), random.nextInt(30) + 10);
                        spieler.setGold(spieler.getGold() - verlust);
                        System.out.println("Ein Taschendieb stiehlt dir " + verlust + " Gold!");
                    }
                    break;
                case 3:
                    System.out.println("Ein mysteriöses Portal erscheint!");
                    if (random.nextDouble() < 0.5) {
                        int bonus = random.nextInt(50) + 20;
                        spieler.addErfahrung(bonus);
                        System.out.println("Du erhältst " + bonus + " Erfahrungspunkte durch mystische Energie!");
                    } else {
                        int portalSchaden = random.nextInt(30) + 20;
                        spieler.setLeben(spieler.getLeben() - portalSchaden);
                        System.out.println("Das Portal explodiert und verursacht " + portalSchaden + " Schaden!");
                    }
                    break;
                case 4:
                    System.out.println("Ein uralter Fluch manifestiert sich!");
                    int fluchSchaden = random.nextInt(20) + 10;
                    spieler.setLeben(spieler.getLeben() - fluchSchaden);
                    System.out.println("Der Fluch raubt dir " + fluchSchaden + " Lebenspunkte!");
                    if (spieler.getGold() > 0) {
                        int goldVerlust = Math.min(spieler.getGold(), random.nextInt(50) + 30);
                        spieler.setGold(spieler.getGold() - goldVerlust);
                        System.out.println("Der Fluch lässt " + goldVerlust + " Gold verschwinden!");
                    }
                    break;
                case 5:
                    System.out.println("Eine Gruppe Assassinen erscheint!");
                    charakter assassine = new charakter("Elite-Assassine", "Ein tödlicher Meuchelmörder", 
                        spieler.getLevel()+2, 60, 100, 40, 15);
                    new kampf().startKampf(spieler, assassine);
                    break;
                case 6:
                    System.out.println("Ein verdorbener Schrein pulsiert mit dunkler Energie!");
                    if (random.nextDouble() < 0.3) { // 30% Chance
                        System.out.println("Der Schrein gewährt dir Macht!");
                        spieler.addErfahrung(100);
                    } else {
                        int verderbnis = random.nextInt(50) + 30;
                        spieler.setLeben(spieler.getLeben() - verderbnis);
                        System.out.println("Dunkle Energie zerreißt deinen Körper! " + verderbnis + " Schaden!");
                    }
                    break;
                case 7:
                    System.out.println("Ein uralter Drache fliegt über dich hinweg!");
                    if (spieler.getLevel() < 10) {
                        int drachenFeuer = random.nextInt(40) + 60;
                        spieler.setLeben(spieler.getLeben() - drachenFeuer);
                        System.out.println("Drachenfeuer versengt dich! " + drachenFeuer + " Schaden!");
                    } else {
                        System.out.println("Dank deiner Erfahrung kannst du dem Drachenfeuer ausweichen!");
                    }
                    break;
                case 8:
                    System.out.println("Ein Dimensionsriss öffnet sich!");
                    int dimensionsSchaden = random.nextInt(100) + 50;
                    if (spieler.getLevel() < 15) {
                        spieler.setLeben(spieler.getLeben() - dimensionsSchaden);
                        System.out.println("Die Realität zerreißt dich! " + dimensionsSchaden + " Schaden!");
                    } else {
                        System.out.println("Du bist mächtig genug, dem Riss zu widerstehen!");
                        spieler.addErfahrung(200);
                    }
                    break;
                case 9:
                    if (spieler.getLevel() >= 20) {
                        System.out.println("Der Schattenkönig ruft nach dir...");
                        charakter schattenDiener = new charakter("Schattenprinz", "Ein Diener des Schattenkönigs",
                            spieler.getLevel() + 5, 300, 1000, 70, 40);
                        new kampf().startKampf(spieler, schattenDiener);
                    }
                    break;
            }
        }
    }

    // Neue Methode für spezielle Endgame-Events
    private void pruefeEndgameEvent() {
        if (spieler.getLevel() >= 45 && random.nextDouble() < 0.05) { // Selteneres aber mächtigeres Event
            System.out.println("\n=== ULTIMATIVES ENDGAME EVENT ===");
            System.out.println("Der Ewige fordert dich zum finalen Kampf der Existenz!");
            charakter finalBoss = new charakter("Der Ewige", "Das absolute Wesen",
                50, 20000, 500000, 2000, 1000);
            if (new kampf().startKampf(spieler, finalBoss)) {
                System.out.println("Du hast das unmögliche vollbracht! Der Ewige ist besiegt!");
                System.out.println("=== ABSOLUTER SIEG ===");
                System.out.println("Du bist zum mächtigsten Wesen aller Realitäten aufgestiegen!");
                System.out.println("Dein Name wird für alle Ewigkeit in den Sternen geschrieben stehen!");
                System.exit(0);
            }
        }
    }

    // Zeigt das Inventar des Spielers an
    private void zeigeInventar() {
        System.out.println("\n--- INVENTAR ---");
        System.out.println("Gold: " + spieler.getGold());
        ArrayList<item> items = spieler.getInventar().getItems();
        if (items.isEmpty()) {
            System.out.println("Dein Inventar ist leer.");
        } else {
            System.out.println("Gegenstände:");
            for (item i : items) {
                System.out.println("- " + i.getName() + " (" + i.getBeschreibung() + ", Wert: " + i.getWert() + " Gold)");
            }
        }
        System.out.println("--- ENDE INVENTAR ---");
    }

    // Zeigt den Status des Spielers an
    private void zeigeSpielerStatus() {
        System.out.println("\n--- SPIELER STATUS ---");
        System.out.println("Name: " + spieler.getName());
        System.out.println("Level: " + spieler.getLevel() + " (Erfahrung: " + spieler.getErfahrung() + "/" + spieler.getLevelMaxErfahrung() + ")");
        System.out.println("Leben: " + spieler.getLeben());
        System.out.println("Schaden: " + spieler.getSchaden());
        System.out.println("Schutz: " + spieler.getSchutz());
        System.out.println("Gold: " + spieler.getGold());
        System.out.println("--- ENDE STATUS ---");
    }

    // Hilfemethode für das Spiel
    private void zeigeHilfe() {
        System.out.println("\n--- HILFE ---");
        System.out.println("1. Gehen: Wechsle in einen anderen Raum.");
        System.out.println("2. Umsehen: Sieh dich im aktuellen Raum um und entdecke neue Ereignisse.");
        System.out.println("3. Suchen: Suche gezielt nach Items im aktuellen Raum.");
        System.out.println("4. Status anzeigen: Zeigt deinen aktuellen Status (Leben, Level, etc.).");
        System.out.println("5. Inventar anzeigen: Zeigt alle gesammelten Gegenstände und dein Gold.");
        System.out.println("6. Item benutzen: Benutze einen Trank oder essbares Item aus deinem Inventar.");
        System.out.println("7. Item ausrüsten: Rüste eine Waffe oder Rüstung aus deinem Inventar aus.");
        System.out.println("8. Hilfe: Zeigt diese Hilfeseite an.");
        System.out.println("9. Spiel beenden: Beendet das Spiel.");
        System.out.println("--- ENDE HILFE ---");
    }

    public static void main(String[] args) {
        spiel neuesSpiel = new spiel();
        neuesSpiel.starteSpiel();
    }
}