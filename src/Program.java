import helper.Log;
import modeli.*;

import java.util.Scanner;

public class Program {

    private static Osoba osoba;

    public static void main(String[] args) {

        System.out.print("\nDobrodošli u Čoko-PC-kord!\n\nOvo je Vaša muzička prodavnica!\n\nVaše korisničko ime: ");
        String username = new Scanner(System.in).nextLine();
        checkUsername(username);
        //Log.insert(username + " ulogovan/a " + );
        System.out.println(osoba.getMeni());

    }

    private static void checkUsername(String username) {
        if(username.startsWith("a")) osoba = Administrator.adminPassCheck(username);
        else if(username.startsWith("k")) osoba = Korisnik.korisnikPassCheck(username);
        else System.err.println("Neispravno korisničko ime! Korisničko ime mora početi malim slovom 'k' ili 'a'.");
    }

}