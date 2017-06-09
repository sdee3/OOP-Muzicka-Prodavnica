package modeli;

import java.util.Scanner;

public class Korisnik extends Osoba{

    private Korisnik(String username, String password) {
        super(username, password);
    }

    public String getMeni(){
        return "\n1. Prikaz biblioteke kupljenih albuma i pesama\n2. Pretraga izvođača u muzičkoj aplikaciji" +
                "\n3. Dodavanje pesme u biblioteku\n4. Dodavanje albuma u biblioteku\n5. Odjava\n";
    }

    public static Korisnik korisnikCheck(String username) {
        System.out.println("Lozinka: ");
        String tmpPassword = new Scanner(System.in).nextLine();
        return new Korisnik(username, tmpPassword);
    }

}