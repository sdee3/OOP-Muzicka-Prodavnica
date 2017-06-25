package modeli;

import baza.BazaPodataka;
import izuzeci.NepostojeciIzvodjacException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Izvodjaci {

    private int id, god_formiranja, god_raspada;
    private String ime_prezime, tip, biografija;

    public Izvodjaci(String ime_prezime, String tip, int god_formiranja, int god_raspada, String biografija) {
        this.id = dohvatiNoviId();
        this.god_formiranja = god_formiranja;
        this.god_raspada = god_raspada;
        this.ime_prezime = ime_prezime;
        this.tip = tip;
        this.biografija = biografija;

        String upit = "insert into izvodjaci values (" + this.id + ", '" + this.ime_prezime + "', '" + this.tip +
                "', " + this.god_formiranja + ", " + this.god_raspada + ", '" + this.biografija + "')";

        try {
            int brPromena = BazaPodataka.getInstanca().iudUpit(upit);
            if(brPromena > 0) System.out.println("Uspesno dodavanje izvodjaca u bazu!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Izvodjaci(int id, String ime_prezime, String tip, int god_formiranja, int god_raspada, String biografija) {
        this.id = id;
        this.god_formiranja = god_formiranja;
        this.god_raspada = god_raspada;
        this.ime_prezime = ime_prezime;
        this.tip = tip;
        this.biografija = biografija;
    }

    private static int dohvatiNoviId() {
        String upit = "select id from izvodjaci order by id desc limit 1";
        int noviId = 0;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ++noviId;

    }

    public static Izvodjaci vratiIzvodjacaPoId(int izvodjac_id) {

        String upit = "select * from izvodjaci where id = " + izvodjac_id;
        Izvodjaci rezultat = null;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            rezultat = new Izvodjaci(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                    odgovorBaze.getString(3), odgovorBaze.getInt(4), odgovorBaze.getInt(5),
                    odgovorBaze.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static ArrayList<Izvodjaci> dohvatiSveIzvodjace(){
        String upit = "select * from izvodjaci";
        ArrayList<Izvodjaci> rezultat = new ArrayList<>();

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                rezultat.add(new Izvodjaci(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getString(3), odgovorBaze.getInt(4), odgovorBaze.getInt(5),
                        odgovorBaze.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static Izvodjaci dohvatiIzvodjacaPoImenu(String ime_prezime) throws NepostojeciIzvodjacException{
        String upit = "select * from izvodjaci where ime_prezime = '" + ime_prezime + "'";
        Izvodjaci rezultat = null;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next())
                rezultat = new Izvodjaci(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getString(3), odgovorBaze.getInt(4), odgovorBaze.getInt(5),
                        odgovorBaze.getString(6));
            else
                throw new NepostojeciIzvodjacException(ime_prezime);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static ArrayList<Pesme> dohvatiPesmeIzvodjaca(String ime_prezime){
        ArrayList<Pesme> rezultat = new ArrayList<>();
        String upit = "select * from pesme where id_izvodjaca = (select id from izvodjaci where ime_prezime = '" +
                ime_prezime + "')";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                rezultat.add(new Pesme(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                        odgovorBaze.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static ArrayList<Albumi> dohvatiAlbumeIzvodjaca(String ime_prezime){
        ArrayList<Albumi> rezultat = new ArrayList<>();
        String upit = "select * from albumi where id_izvodjaca = (select id from izvodjaci where ime_prezime = '"
                + ime_prezime + "')";

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                rezultat.add(new Albumi(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                        odgovorBaze.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    @Override
    public String toString() {
        return tip + " izvodjac " + ime_prezime + "\nAktivan od " + god_formiranja + "." +
                ((god_raspada > 0) ? " do " + god_raspada : "") + "\nDetaljnije o izvodjacu:\n" + biografija;
    }

    public String getImePrezime() {
        return ime_prezime;
    }

    public static void rucniUnosNovogIzvodjaca(Scanner unos) {
        String naziv, tip, biografija, god_formiranja_string, god_raspada_string;
        int god_formiranja = 0, god_raspada = 0;

        System.out.println("Kako biste uneli izvodjaca, unesite REDOM: " +
                "naziv benda ili ime i prezime, tip (SOLO, DUO, BEND), godinu formiranja, godinu raspada " +
                "(moze ostati prazno ukoliko je izvodjac aktivan) i biografiju ne duzu od 8 recenica.");

        naziv = unos.nextLine();
        tip = unos.nextLine();

        god_formiranja_string = unos.nextLine();
        if(!god_formiranja_string.equals(""))
            god_formiranja = Integer.parseInt(god_formiranja_string);

        god_raspada_string = unos.nextLine();
        if(!god_raspada_string.equals(""))
            god_raspada = Integer.parseInt(god_raspada_string);

        biografija = unos.nextLine();

        String upit = "insert into izvodjaci values (" + dohvatiNoviId() + ", '" + naziv + "', '"
                + tip.toUpperCase() + "', " + god_formiranja;
        if(god_raspada!=0)
            upit += ", " + god_raspada + ", '" + biografija + "')";
        else
            upit += ", null, '" + biografija + "')";

        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspesan unos novog izvodjaca!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIzvodjac(String tmpImePrezime, String tmpTip, int tmpGodinaFormiranja, int tmpGodRaspada, String tmpBiografija) {
        String upit = "";

        if(tmpImePrezime.length() > 1 || tmpTip.length() > 1 || tmpGodinaFormiranja != 0 || tmpGodRaspada != 0 || tmpBiografija.length() > 1)
            upit = "update izvodjaci set ";

        if(tmpImePrezime.length()>1) upit += "ime_prezime = '" + tmpImePrezime;

        if(tmpTip.length()>=1)
            if(tmpImePrezime.length() > 1){
            upit += ", tip = '" + tmpTip.toUpperCase();
            }else{
                upit += "tip = '" + tmpTip.toUpperCase();
            }

        if(tmpGodinaFormiranja!=0)
            if(tmpImePrezime.length() > 1 || tmpTip.length() > 1){
                upit += ", god_formiranja = " + tmpGodinaFormiranja;
            }else{
                upit += "god_formiranja = " + tmpGodinaFormiranja;
            }

        if(tmpGodRaspada!=0)
            if(tmpImePrezime.length() > 1 || tmpTip.length() > 1 || tmpGodinaFormiranja != 0) {
                upit += ", god_raspada = " + tmpGodRaspada;
            }else{
                upit += "god_raspada = " + tmpGodRaspada;
            }

        if(tmpBiografija.length()>1)
            if(tmpImePrezime.length() > 1 || tmpTip.length() > 1 || tmpGodinaFormiranja != 0 || tmpGodRaspada != 0) {
                upit += ", biografija = '" + tmpBiografija.trim();
            }else{
                upit += "biografija = '" + tmpBiografija.trim();
            }

        if(tmpImePrezime.length() > 1 || tmpTip.length() > 1 || tmpGodinaFormiranja != 0 || tmpGodRaspada != 0 || tmpBiografija.length() > 1) {
            upit += " where id = " + this.id;
            try {
                if (BazaPodataka.getInstanca().iudUpit(upit) > 0)
                    System.out.println("Uspesno azuriranje izvodjaca!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Izvodjaci dohvatiIzvodjacaPoId(int id_izvodjaca) {
        Izvodjaci rez = null;
        String upit = "select * from izvodjaci where id = " + id_izvodjaca;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            rez = new Izvodjaci(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                    odgovorBaze.getString(3), odgovorBaze.getInt(4), odgovorBaze.getInt(5),
                    odgovorBaze.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rez;
    }

    public static void deleteIzvodjac(int tmpId) {
        String upit = "delete from izvodjaci where id = " + tmpId;
        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspesno izbrisan izvodjac!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIzvodjacId() {
        return id;
    }
}