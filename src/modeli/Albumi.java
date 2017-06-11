package modeli;

import baza.BazaPodataka;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Albumi {

    private int id, godina_izdanja, id_izvodjaca;
    private String naziv, zanr;

    public Albumi(int godina_izdanja, int id_izvodjaca, String naziv, String zanr) {
        this.id = dohvatiNoviId();
        this.godina_izdanja = godina_izdanja;
        this.id_izvodjaca = id_izvodjaca;
        this.naziv = naziv;
        this.zanr = zanr;
    }

    private Albumi(int id, String naziv, int godina_izdanja, int id_izvodjaca, String zanr) {
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

    protected int getIdAlbuma() {
        return id;
    }

    public int getGodinaAlbuma() {
        return godina_izdanja;
    }

    public int getIdIzvodjacaAlbuma() {
        return id_izvodjaca;
    }

    public String getNazivAlbuma() {
        return naziv;
    }

    public String getZanrAlbuma() {
        return zanr;
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

}