package modeli;

import baza.BazaPodataka;
import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Albumi {

    private int id, godina_izdanja;
    private String naziv, zanr;
    private Izvodjaci izvodjac;

    public Albumi(int godina_izdanja, int id_izvodjaca, String naziv, String zanr) {
        this.id = dohvatiNoviId();
        this.godina_izdanja = godina_izdanja;
        izvodjac = Izvodjaci.dohvatiIzvodjacaPoId(id_izvodjaca);
        this.naziv = naziv;
        this.zanr = zanr;

        String upit = "insert into albumi values (" + this.id + ", '" + this.naziv + "', " + this.godina_izdanja
                + ", " + izvodjac.getIzvodjacId() + ", '" + this.zanr + "')";

        try {
            int brPromena = BazaPodataka.getInstanca().iudUpit(upit);
            if(brPromena > 0) System.out.println("Uspesno dodavanje albuma u bazu!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Albumi(int id, String naziv, int godina_izdanja, int id_izvodjaca, String zanr) {
        this.id = id;
        this.godina_izdanja = godina_izdanja;
        izvodjac = Izvodjaci.dohvatiIzvodjacaPoId(id_izvodjaca);
        this.naziv = naziv;
        this.zanr = zanr;
    }

    private static int dohvatiNoviId() {
        String upit = "select id from albumi order by id desc limit 1";
        int noviId = 0;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ++noviId;
    }

    public static String vratiNazivAlbumaPoId(int album_id) {
        String upit = "select naziv, godina_izdanja from albumi where id = " + album_id;
        String rezultat = "";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            rezultat = odgovorBaze.getString(1) + " iz " + odgovorBaze.getString(2) + ". godine.";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static Albumi dohvatiAlbumPoId(int id){
        String upit = "select * from albumi where id = " + id;
        Albumi rez = null;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next())
                rez = new Albumi(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                        odgovorBaze.getString(5));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rez;
    }

    @Override
    public String toString() {
        return id + ". " + naziv + "\nŽanr: " + zanr + "\nIzdat " + godina_izdanja + "."
                + "\nIzvodjac:\n" + izvodjac + "\nUkupno trajanje albuma: " + dohvatiUkupnoTrajanje(this)
                + "\nPesme:\n" + Pesme.dohvatiPesmeAlbuma(this);
    }

    public static String dohvatiUkupnoTrajanje(Albumi albumi) {
        ArrayList<Trajanje> tmpListaTrajanje = new ArrayList<>();
        String rezultat = null, upit = "select p.trajanje from pesme p, albumi a where p.id_izvodjaca = "+ albumi.izvodjac.getIzvodjacId() + " and p.id_albuma = a.id";

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()) {
                tmpListaTrajanje.add(Trajanje.parsirajVreme(odgovorBaze.getString(1)));
                rezultat = Trajanje.saberiIVratiVreme(tmpListaTrajanje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static Albumi rucniUnosNovogAlbuma(Scanner unos) {
        System.out.println("Kako biste uneli novi album, unesite REDOM: " +
                "naziv albuma, godinu izdanja, ID izvođača albuma, zanr.\nNakon ovoga sledi unos pesama.");

        System.out.print("Naziv: ");
        String naziv = unos.nextLine();
        System.out.print("Godina izdanja: ");
        int godina_izdanja = unos.nextInt();
        unos.nextLine();
        System.out.print("ID izvodjaca: ");
        int id_izvodjaca = unos.nextInt();
        unos.nextLine();
        System.out.print("Zanr albuma: ");
        String zanr = unos.nextLine();

        String upit = "insert into albumi(id, naziv, godina_izdanja, id_izvodjaca, zanr) values (" + dohvatiNoviId() + ", '" + naziv
                + "', " + godina_izdanja + ", " + id_izvodjaca + ", '" + zanr + "')";

        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0 )
                System.out.println("Uspesan unos novog albuma!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Albumi(godina_izdanja, id_izvodjaca, naziv, zanr);
    }

    public void updateAlbum(String tmpNaziv, int tmpGodina, int tmpIDIzvodjaca, String tmpZanr) {
        String upit = "";

        if(tmpNaziv.length() > 1 || tmpGodina!=0 || tmpIDIzvodjaca!=0 || tmpZanr.length() > 1)
            upit = "update albumi set ";

        if(tmpNaziv.length()>1) upit += "naziv = '" + tmpNaziv;

        if(tmpGodina!=0)
            if(tmpNaziv.length() > 1) {
                upit += ", godina_izdanja = " + tmpGodina;
            }else{
                upit += "godina_izdanja = " + tmpGodina;
            }

        if(tmpIDIzvodjaca!=0)
            if(tmpNaziv.length() > 1 || tmpGodina!=0) {
                upit += ", id_izvodjaca = " + tmpIDIzvodjaca;
            }else{
                upit += "id_izvodjaca = " + tmpIDIzvodjaca;
            }

        if(tmpZanr.length()>1)
            if(tmpNaziv.length() > 1 || tmpGodina!=0 || tmpIDIzvodjaca!=0){
                upit += ", zanr = '" + tmpZanr + "'";
            }
            else{
                upit += "zanr = '" + tmpZanr + "'";
            }
        if(tmpNaziv.length() > 1 || tmpGodina!=0 || tmpIDIzvodjaca!=0 || tmpZanr.length() > 1) {
            upit += " where id = " + this.id;

            try {
                if (BazaPodataka.getInstanca().iudUpit(upit) > 0)
                    System.out.println("Uspesno ažuriranje albuma!");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void deleteAlbum(int tmpId) {
        String upit = "delete from albumi where id = " + tmpId;
        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspesno izbrisan album!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getAlbumId() {
        return id;
    }

    public Izvodjaci getIzvodjac() {
        return izvodjac;
    }

    public static ArrayList<Albumi> dohvatiSveAlbume() {
        String upit = "select * from albumi";
        ArrayList<Albumi> rez = new ArrayList<>();

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                rez.add(new Albumi(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                        odgovorBaze.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rez;
    }

    public static Albumi filtrirajListuAlbumaPoId(ArrayList<Albumi> albumi, int id) {
            Albumi rezultat = null;
            for(Albumi a : albumi){
                if(a.id == id) rezultat = a;
            }
            return rezultat;
    }
}