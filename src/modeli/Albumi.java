package modeli;

import baza.BazaPodataka;
import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Albumi {

    private int id, godina_izdanja, id_izvodjaca;
    private String naziv, zanr;

    public Albumi(int godina_izdanja, int id_izvodjaca, String naziv, String zanr) {
        this.id = dohvatiNoviId();
        this.godina_izdanja = godina_izdanja;
        this.id_izvodjaca = id_izvodjaca;
        this.naziv = naziv;
        this.zanr = zanr;

        String upit = "insert into albumi values (" + this.id + ", '" + this.naziv + "', " + this.godina_izdanja
                + ", " + this.id_izvodjaca + ", '" + this.zanr + "')";

        try {
            int brPromena = BazaPodataka.getInstanca().iudUpit(upit);
            if(brPromena > 0) System.out.println("Uspešno dodavanje albuma u bazu!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Albumi(int id, String naziv, int godina_izdanja, int id_izvodjaca, String zanr) {
        this.id = id;
        this.godina_izdanja = godina_izdanja;
        this.id_izvodjaca = id_izvodjaca;
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

    @Override
    public String toString() {
        return id + ". " + naziv + "\nŽanr: " + zanr + "\nIzdat " + godina_izdanja + ".";
    }

    public static String dohvatiUkupnoTrajanje(Albumi albumi) {
        Trajanje tmpTrajanje, zbirno = new Trajanje(0,0,0);
        String rezultat = null, upit = "select p.trajanje from pesme p, albumi a where p.id_izvodjaca = "+ albumi.id_izvodjaca + " and p.id_albuma = a.id";

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()) {
                tmpTrajanje = Trajanje.parsirajVreme(odgovorBaze.getString(1));
                rezultat = zbirno.saberiVreme(tmpTrajanje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static Albumi rucniUnosNovogAlbuma(Scanner unos) {
        System.out.println("Kako biste uneli novi album, unesite REDOM: " +
                "naziv albuma, godinu izdanja, ID izvođača albuma, žanr.\nNakon ovoga sledi unos pesama.");

        System.out.print("Naziv: ");
        String naziv = unos.nextLine();
        System.out.print("Godina izdanja: ");
        int godina_izdanja = unos.nextInt();
        unos.nextLine();
        System.out.print("ID izvodjaca: ");
        int id_izvodjaca = unos.nextInt();
        unos.nextLine();
        System.out.print("Žanr albuma: ");
        String zanr = unos.nextLine();

        return new Albumi(godina_izdanja, id_izvodjaca, naziv, zanr);
    }
}