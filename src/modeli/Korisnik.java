package modeli;

import baza.BazaPodataka;
import helper.Log;
import izuzeci.DuplikatAlbumaException;
import izuzeci.DuplikatPesmeException;
import izuzeci.NeispravanLoginException;

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
        return "\n1. Prikaz biblioteke kupljenih albuma i pesama\n2. Pretraga izvodjaca u muzickoj aplikaciji" +
                "\n3. Dodavanje pesme u biblioteku\n4. Dodavanje albuma u biblioteku\n5. Odjava\n";
    }

    public static Korisnik korisnikPassCheck(String username) {
        String praviPassword="", tmpPassword;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit("SELECT password FROM osoba WHERE admin = 0 AND username = '" + username + "'");
            praviPassword = odgovorBaze.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (getBrojac() < 3) {
            System.out.print("Lozinka: ");
            tmpPassword = new Scanner(System.in).nextLine();
            try {
                if (proveraUneteIPraveLozinke(praviPassword, tmpPassword))
                    break;
                else inkrementirajBrojac();
            } catch (NeispravanLoginException e) {
                e.printStackTrace();
            }
        }
        return proveraBrojacaIKreiranjeKorisnika(username, praviPassword);
    }

    private static Korisnik proveraBrojacaIKreiranjeKorisnika(String username, String password) {
        if (getBrojac() == 3) {
            System.err.println("Neispravno uneti podaci. Izlazak iz aplikacije...");
            System.exit(1);
            return null;
        } else return new Korisnik(username, password);
    }

    public static void prikaziBiblioteku(Osoba osoba) {
        String upit = "select * from " + osoba.getUsername();

        ArrayList<Pesme> pesme = Pesme.dohvatiSvePesme(), korisnikovePesme = new ArrayList<>();
        ArrayList<Albumi> albumi = Albumi.dohvatiSveAlbume(), korisnikoviAlbumi = new ArrayList<>();

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                korisnikovePesme.add(Pesme.dohvatiPesmuPoId(pesme, odgovorBaze.getInt("id_pesme")));
                korisnikoviAlbumi.add(Albumi.filtrirajListuAlbumaPoId(albumi, odgovorBaze.getInt("id_albuma")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\nIndividualne pesme koje se nalaze u Vasoj biblioteci:");
        for (Pesme p : korisnikovePesme){
            if(p!=null && p.getAlbumPesme() == null)
                System.out.println(p.ispisKompletnePesme());
        }

        System.out.println("\nNeindividualne pesme:");
        for (Pesme p : korisnikovePesme)
            if(p!=null && p.getAlbumPesme() != null)
                System.out.println(p.ispisKompletnePesme());

        System.out.println("\n\nAlbumi sa pesmama:");
        for (Albumi a : korisnikoviAlbumi)
            if(a!=null) System.out.println(a);

    }

    private static void ubaciPesmuUKorBazu(String username, int id) {
        ArrayList<Pesme> svePesme = Pesme.dohvatiSvePesme();
        boolean flagSvePesme = false, flagDuplikat = false;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit("select id_pesme from " + username);
            while (odgovorBaze.next()){
                if(odgovorBaze.getInt("id_pesme") == id) {
                    try {
                        throw new DuplikatPesmeException();
                    } catch (DuplikatPesmeException e) {
                        e.printStackTrace();
                    }
                    flagDuplikat = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!flagDuplikat)
            for (Pesme p : svePesme){
                if(p.getId() == id){
                    flagSvePesme = true;
                    String upit = "insert into " + username + "(id_pesme) values (" + id + ")";
                    try {
                        if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                            System.out.println("Uspesno dodavanje pesme u korisnicku biblioteku!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        if(!flagSvePesme && !flagDuplikat) System.err.println("Pesma ne postoji u bazi!");

    }

    private static void ubaciAlbumUKorBazu(String username, int id) {
        ArrayList<Albumi> albumi = Albumi.dohvatiSveAlbume();
        boolean flagSviAlbumi = false, flagDuplikat = false;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit("select id_albuma from " + username);
            while (odgovorBaze.next()){
                if(odgovorBaze.getInt("id_albuma") == id) {
                    try {
                        throw new DuplikatAlbumaException();
                    } catch (DuplikatAlbumaException e) {
                        e.printStackTrace();
                    }
                    flagDuplikat = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!flagDuplikat)
            for (Albumi a : albumi){
                if(a.getAlbumId() == id){
                    flagSviAlbumi = true;
                    String upit = "insert into " + username + "(id_albuma) values (" + id + ")";
                    try {
                        if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                            System.out.println("Uspesno dodavanje albuma u korisnicku biblioteku!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        if(!flagSviAlbumi && !flagDuplikat) System.err.println("Album ne postoji u bazi!");

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