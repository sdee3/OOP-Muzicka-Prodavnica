package modeli;

import java.util.Scanner;

public class Administrator extends Osoba {

    private Administrator(String username, String password) {super(username, password);}

    public String getMeni() {
        return "\n1. Unos pesme\n2. Unos izvođača\n3. Unos albuma\n4. Ažuriranje pesme\n5. Ažuriranje izvođača" +
                "\n6. Ažuriranje albuma\7. Brisanje pesme\n8. Brisanje izvođača\n9. Brisanje albuma\n10. Odjava\n";
    }

    public static Administrator adminPassCheck(String username) {
        String password = "", upit = "SELECT username FROM osoba WHERE admin = 1 AND password = '" + password + "'";
        while (getBrojac() < 3) {
            System.out.print("Lozinka: ");
            password = new Scanner(System.in).nextLine();
            if (proveraBazeLogin(username, upit))
                break;
            else inkrementirajBrojac();
        }
        return proveraBrojaca(username, password);
    }

    private static Administrator proveraBrojaca(String username, String password) {
        if (getBrojac() == 3) {
            System.err.println("Neispravno uneti podaci. Izlazak iz aplikacije...");
            System.exit(1);
            return null;
        } else return new Administrator(username, password);
    }

}