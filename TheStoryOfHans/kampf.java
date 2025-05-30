public class kampf {
    //attribute: lebenspunkte, schaden, schutz
    public int lebenspunkte;
    public int schaden;
    public int schutz;


    // Konstruktor
    public void kampfLeben(int lebenspunkte, int schutz){

        this.lebenspunkte = lebenspunkte;
        this.schutz = schutz;
}


    // schaden system einrichten
    public void schaden(int schaden){
        this.schaden = schaden;
        
        //solange das schutz da ist wird schaden hinzugefügt
            while(schutz > 0 && schaden > 0){
                schaden = schaden - 1;
                schutz = schutz - 1;


            }
        //falls das schutz nicht vorhanden ist wird vom leben abgezogen    
        if(schutz == 0){
            while(lebenspunkte > 0 && schaden > 0){
                schaden = schaden - 1;  
                schutz = lebenspunkte - 1;


            }
        }
    }

}
