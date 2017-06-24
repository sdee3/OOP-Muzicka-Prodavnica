package helper;

import java.util.ArrayList;

public class Trajanje {

    private int sati, minuti, sekunde;

    public Trajanje(int sati, int minuti, int sekunde){
        this.sati = sati;
        this.minuti = minuti;
        this.sekunde = sekunde;
    }

    public static Trajanje parsirajVreme(String vreme){
        String[] tmpNiz = vreme.split(":");
        return new Trajanje(Integer.parseInt(tmpNiz[0]), Integer.parseInt(tmpNiz[1]), Integer.parseInt(tmpNiz[2]));
    }

    public static String saberiIVratiVreme(ArrayList<Trajanje> trajanje){

        int sati = 0, minuti = 0, sekunde = 0;

        for(Trajanje t : trajanje){
            sati += t.sati;
            minuti += t.minuti;
            sekunde += t.sekunde;

            if(sekunde >= 60){
                minuti++;
                sekunde = sekunde % 60;
            }

            if(minuti >= 60){
                sati++;
                minuti = minuti % 60;
            }
        }

        return proveriSate(sati) + ":" + proveriMinute(minuti)
                + ":" + proveriSekunde(sekunde);

    }

    private static String proveriSekunde(int sekunde) {
        if(sekunde == 0)
            return "00";
        else if(sekunde<10)
            return "0" + sekunde;
        else return "" + sekunde;
    }

    private static String proveriMinute(int minuti) {
        if(minuti == 0)
            return "00";
        else if(minuti<10)
            return "0" + minuti;
        else return "" + minuti;
    }

    private static String proveriSate(int sati) {
        if(sati == 0)
            return "00";
        else if(sati<10)
            return "0" + sati;
        else return "" + sati;
    }

}