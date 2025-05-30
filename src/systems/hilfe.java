package systems;
// hilfe.java
public class hilfe {
    // hilfeText
    public String hilfeText;

    public hilfe(String hilfeText) {
        this.hilfeText = hilfeText;
    }

    // getHilfeText() - gibt den Hilfetext zurück
    public String getHilfeText() {
        return hilfeText;
    }

    // setHilfeText() - setzt den Hilfetext
    public void setHilfeText(String hilfeText) {
        this.hilfeText = hilfeText;
    }

    // zeigeHilfe() - zeigt den Hilfetext an
    public void zeigeHilfe() {
        System.out.println("\n--- HILFE ---");
        System.out.println(this.hilfeText);
        System.out.println("--- ENDE HILFE ---");
    }
}