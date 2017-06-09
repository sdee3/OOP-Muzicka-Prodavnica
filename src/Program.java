import modeli.*;

import java.util.Scanner;

public class Program {

    private static Osoba osoba;

    public static void main(String[] args) {

        System.out.println("\nDobrodošli u Diskord!\n\nOvo je Vaša muzička prodavnica!\n\nUlogujte se " +
                "ili registrujte, ukoliko ste novi ovde. Počnite unošenjem Vašeg korisničkog imena. Ukoliko" +
                "ste novi, izaberite neko novo.\n\nKorisničko ime:");
        String username = new Scanner(System.in).nextLine();
        checkUsername(username);
        osoba.getMeni();
    }

    private static void checkUsername(String username) {
        if(username.startsWith("a"))
            osoba = Administrator.adminCheck(username);
        else if(username.startsWith("k"))
            osoba = Korisnik.korisnikCheck(username);
        else
            System.err.println("Neispravno korisničko ime! Ukoliko se registrujete, Vaše korisničko ime mora početi malim slovom 'k'.");
    }

}