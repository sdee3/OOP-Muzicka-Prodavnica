package helper;

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

    public String saberiVreme(Trajanje trajanje){
        int sati = this.sati + trajanje.sati;
        int minuti = this.minuti + this.minuti;
        int sekunde = this.sekunde + trajanje.sekunde;

        if(sekunde >= 60){
            minuti++;
            sekunde = sekunde % 60;
        }

        if(minuti >= 60){
            sati++;
            minuti = minuti % 60;
        }

        return proveriSate(sati) + ":" + proveriMinute(minuti)
                + ":" + proveriSekunde(sekunde);

    }

    private String proveriSekunde(int sekunde) {
        if(sekunde == 0)
            return "00";
        else if(sekunde<10)
            return "0" + sekunde;
        else return "" + sekunde;
    }

    private String proveriMinute(int minuti) {
        if(minuti == 0)
            return "00";
        else if(minuti<10)
            return "0" + minuti;
        else return "" + minuti;
    }

    private String proveriSate(int sati) {
        if(sati == 0)
            return "00";
        else if(sati<10)
            return "0" + sati;
        else return "" + sati;
    }

}