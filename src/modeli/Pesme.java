package modeli;

import baza.BazaPodataka;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pesme {

    private String naziv, trajanje, izvodjac, album;
    private int id, izvodjac_id, album_id, godina_albuma;

    private Pesme(int id, String naziv, int izvodjac_id, int album_id, String trajanje) {
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.id = id;
        this.izvodjac_id = izvodjac_id;
        this.album_id = album_id;
    }

    private Pesme(int id, String naziv, String trajanje, String izvodjac, String album, int godina_albuma){
        this.id = id;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.izvodjac = izvodjac;
        this.album = album;
        this.godina_albuma = godina_albuma;
    }

    public Pesme(String naziv, String trajanje, int izvodjac_id, int album_id){
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.id = dohvatiNoviId();
        this.izvodjac_id = izvodjac_id;
        this.album_id = album_id;
    }

    private static int dohvatiNoviId() {
        String upit = "select id from pesme order by id desc limit 1";
        int noviId = 0;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ++noviId;
    }

    public void unosPesme(String naziv, int id_izvodjaca, String trajanje) {
        unosPesme(naziv, trajanje, id_izvodjaca,0);
    }

    public void unosPesme(String naziv, String trajanje, int id_izvodjaca, int id_albuma){
        String upit = (id_albuma != 0) ? "unesiTekst into pesme('naziv', 'id_izvodjaca', 'id_albuma', 'trajanje') values ('" + naziv
                + "', " + id_izvodjaca + ", " + id_albuma + ", '" + trajanje + "')"
                : "unesiTekst into pesme('naziv', 'id_izvodjaca', 'trajanje') values ('" + naziv
                + "', " + id_izvodjaca + ", " + trajanje + "')";
        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) != 0)
                System.out.println("Pesma '" + naziv + "' uspešno dodata!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Pesme dohvatiPesmuPoId(int id){
        Pesme rezultat = null;
        String upit = "select * from pesme where id = " + id;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            rezultat = new Pesme(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                    odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                    odgovorBaze.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static ArrayList<Pesme> dohvatiSvePesme(){
        ArrayList<Pesme> rezultat = new ArrayList<>();
        String upit = "select p.id, p.naziv, p.trajanje, i.ime_prezime, a.naziv, a.godina_izdanja from pesme p, izvodjaci i, albumi a" +
                " where p.id_izvodjaca = i.id and p.id_albuma = a.id and a.id_izvodjaca = i.id";

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);

            while (odgovorBaze.next()){
                rezultat.add(new Pesme(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getString(3), odgovorBaze.getString(4),
                        odgovorBaze.getString(5), odgovorBaze.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static ArrayList<Pesme> dohvatiSvePesmeAdmin(){
        ArrayList<Pesme> rezultat = new ArrayList<>();
        String upit = "select * from pesme";

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

    @Override
    public String toString() {
        return id + ". " + naziv + " (" + trajanje + ")" +
                "\nIzvodi " + izvodjac +
                ", album: " + album + " iz " + godina_albuma + ". godine." +
                '\n';
    }

}