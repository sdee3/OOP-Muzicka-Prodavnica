package modeli;

public class Korisnik extends Osoba{

    public Korisnik(String username, String password) {
        super(username, password);
    }

    public String getMeni(){
        return "\n1. Prikaz biblioteke kupljenih albuma i pesama\n2. Pretraga izvođača u muzičkoj aplikaciji" +
                "\n3. Dodavanje pesme u biblioteku\n4. Dodavanje albuma u biblioteku\n5. Odjava\n";
    }

}