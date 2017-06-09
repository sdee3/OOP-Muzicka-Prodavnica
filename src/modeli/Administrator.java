package modeli;

import baza.BazaPodataka;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator extends Osoba{

    private Administrator(String username, String password) {
        super(username, password);
    }

    public String getMeni(){
        return "\n1. Unos pesme\n2. Unos izvođača\n3. Unos albuma\n4. Ažuriranje pesme\n5. Ažuriranje izvođača" +
                "\n6. Ažuriranje albuma\7. Brisanje pesme\n8. Brisanje izvođača\n9. Brisanje albuma\n10. Odjava\n";
    }

    public static Administrator adminCheck(String username) {
        String password = "";
        while (getBrojac() < 3) {
            System.out.println("Lozinka:");
            password = new Scanner(System.in).nextLine();
            if(proveraBazeLogin(username, password))
                break;
            else inkrementirajBrojac();
        }
        return proveraBrojaca(username, password);
    }

    private static Administrator proveraBrojaca(String username, String password) {
        if(getBrojac() == 3) {
            System.err.println("Neispravno uneti podaci. Izlazak iz aplikacije...");
            System.exit(1);
            return null;
        }else return new Administrator(username, password);
    }

    private static boolean proveraBazeLogin(String username, String password){
        ResultSet odgovorBaze;
        boolean rezultat = false;
        try {
            odgovorBaze = BazaPodataka.getInstanca().selectUpit("SELECT username FROM osoba WHERE admin = 1 AND password = '" + password + "'");
            if(odgovorBaze.getString("username").equals(username)) {
                System.out.println("Dobrodošli nazad!");
                rezultat = true;
            }else
                rezultat = false;
        } catch (SQLException e) { e.printStackTrace(); }
        return rezultat;
    }

}