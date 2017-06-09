import modeli.Administrator;
import modeli.Korisnik;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {



    }

    private static Administrator adminCheck(String username) {
        if(username.startsWith("a")) {
            System.out.println("Lozinka: ");
            String tmpPassword = new Scanner(System.in).nextLine();
            return new Administrator(username, tmpPassword);
        }else return null;
    }



    private static Korisnik korisnikCheck(String username) {
        if(username.startsWith("k")) {
            System.out.println("Lozinka: ");
            String tmpPassword = new Scanner(System.in).nextLine();
            return new Korisnik(username, tmpPassword);
        }else return null;

    }

}
