package modeli;

import java.util.Scanner;

public class Korisnik extends Osoba {

    private Korisnik(String username, String password) {super(username, password);}

    public String getMeni() {
        return "\n1. Prikaz biblioteke kupljenih albuma i pesama\n2. Pretraga izvođača u muzičkoj aplikaciji" +
                "\n3. Dodavanje pesme u biblioteku\n4. Dodavanje albuma u biblioteku\n5. Odjava\n";
    }

    public static Korisnik korisnikPassCheck(String username) {
        String password = "";
        while (getBrojac() < 3) {
            System.out.print("Lozinka: ");
            password = new Scanner(System.in).nextLine();
            if (proveraBazeLogin(username, "SELECT username FROM osoba WHERE admin = 0 AND password = '" + password + "'"))
                break;
            else inkrementirajBrojac();
        }
        return proveraBrojaca(username, password);
    }

    private static Korisnik proveraBrojaca(String username, String password) {
        if (getBrojac() == 3) {
            System.err.println("Neispravno uneti podaci. Izlazak iz aplikacije...");
            System.exit(1);
            return null;
        } else return new Korisnik(username, password);
    }

}