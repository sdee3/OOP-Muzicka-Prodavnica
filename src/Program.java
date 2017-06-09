import helper.Log;
import modeli.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program {

    private static Osoba osoba;

    public static void main(String[] args) {

        System.out.print("\nDobrodošli u Čoko-PC-kord!\n\nOvo je Vaša muzička prodavnica!\n\nVaše korisničko ime: ");
        unesiUsername();
        Log.insert(osoba.getUsername() + " ulogovan/a na sistem: " + datumVreme());
        System.out.println(osoba.getMeni());

    }

    private static String datumVreme() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    private static void unesiUsername() {
        do{
            String username = new Scanner(System.in).nextLine();
            if(username.startsWith("a")){
                osoba = Administrator.adminPassCheck(username);
                break;
            }
            else if(username.startsWith("k")){
                osoba = Korisnik.korisnikPassCheck(username);
                break;
            }
            else System.err.println("Neispravno korisničko ime! Korisničko ime mora početi malim slovom 'k' ili 'a'. Pokušajte ponovo.");

        }while(true);
    }

}