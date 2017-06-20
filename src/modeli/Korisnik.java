package modeli;

import baza.BazaPodataka;
import helper.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static helper.DatumVreme.vratiDatumIVreme;

public class Korisnik extends Osoba {

    private static Scanner unos = new Scanner(System.in);

    private Korisnik(String username, String password) {
        super(username, password);

        String upit = "create table if not exists " + username +
                "(id_albuma INT, id_pesme INT)";
        try {
            BazaPodataka.getInstanca().createTable(upit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public static void prikaziBiblioteku(Osoba osoba) {
        String upit = "select * from " + osoba.getUsername();

        ArrayList<Pesme> pesme = new ArrayList<>();
        ArrayList<Albumi> albumi = new ArrayList<>();

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                pesme.add(Pesme.dohvatiPesmuPoId(odgovorBaze.getInt("id_pesme")));
                albumi.add(Albumi.dohvatiAlbumPoId(odgovorBaze.getInt("id_albuma")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Individualne pesme koje se nalaze u Vašoj biblioteci:");
        for (Pesme p : pesme){
            if(p.getAlbum_id() == 0)
                System.out.println(p);
        }
        System.out.println("Albumi sa pesmama:");
        for (Albumi a : albumi) System.out.println(a); //TODO: Štampaj ovo kako TREBA!

    }

    private static void ubaciPesmuUKorBazu(String username, int id) {
        String upit = "insert into " + username + "(id_pesme) values (" + id + ")";
        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspešno dodavanje pesme u korisničku biblioteku!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void ubaciAlbumUKorBazu(String username, int id) {
        String upit = "insert into " + username + "(id_albuma) values (" + id + ")";
        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspešno dodavanje albuma u korisničku biblioteku!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unosPesmeUBiblioteku(Osoba osoba) {
        System.out.print("Unesite ID pesme radi njenog unosa u biblioteku: ");
        Korisnik.ubaciPesmuUKorBazu(osoba.getUsername(), unos.nextInt());
        unos.nextLine();
        Log.unesiTekst("Korisnik je uneo pesmu u svoju biblioteku" + " - " + vratiDatumIVreme());
    }

    public static void unosAlbumaUBiblioteku(Osoba osoba) {
        System.out.print("Unesite ID albuma radi njegovog unosa u biblioteku: ");
        Korisnik.ubaciAlbumUKorBazu(osoba.getUsername(), unos.nextInt());
        unos.nextLine();
        Log.unesiTekst("Korisnik je uneo album u svoju biblioteku" + " - " + vratiDatumIVreme());
    }
}