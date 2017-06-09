import modeli.Administrator;
import modeli.Korisnik;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        System.out.println("\nDobrodošli u Diskord!\n\nOvo je Vaša muzička prodavnica!\n\nUlogujte se " +
                "ili registrujte, ukoliko ste novi ovde. Počnite unošenjem Vašeg korisničkog imena. Ukoliko" +
                "ste novi, izaberite neko novo.\n\nKorisničko ime:");
        String username = new Scanner(System.in).nextLine();
        checkUsername(username);

    }

    private static void checkUsername(String username) {
        if(username.startsWith("a")) Administrator.adminCheck(username);
        else if(username.startsWith("k")) Korisnik.korisnikCheck(username);
        else
            System.err.println("Neispravno korisničko ime! Ukoliko se registrujete, Vaše korisničko ime mora početi malim slovom 'k'.");
    }

}