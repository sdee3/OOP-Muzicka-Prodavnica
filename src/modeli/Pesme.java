package modeli;

import baza.BazaPodataka;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pesme {

    private String naziv, trajanje;
    private int id;
    private Izvodjaci izvodjac;
    private Albumi album;

    protected Pesme(int id, String naziv, int izvodjac_id, int album_id, String trajanje) {
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.id = id;
        izvodjac = Izvodjaci.dohvatiIzvodjacaPoId(izvodjac_id);
        album = Albumi.dohvatiAlbumPoId(album_id);
    }

    public Pesme(String naziv, String trajanje, int izvodjac_id, int album_id){
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.id = dohvatiNoviId();
        izvodjac = Izvodjaci.dohvatiIzvodjacaPoId(izvodjac_id);
        album = Albumi.dohvatiAlbumPoId(album_id);

        String upit = "insert into pesme values (" + this.id + ", '" + this.naziv + "', " + izvodjac.getIzvodjacId() +
                ", " + album.getAlbumId() + ", '" + this.trajanje + "')";

        try {
            int brPromena = BazaPodataka.getInstanca().iudUpit(upit);
            if(brPromena > 0) System.out.println("Uspešno dodavanje pesme u bazu!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static int dohvatiNoviId() {
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

    public static void unosPesme(String naziv, String trajanje, int id_izvodjaca, int id_albuma){
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

    public static Pesme filtrirajListuPesamaPoId(int id){
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
        return id + ". " + naziv + " (" + trajanje + ")";
    }

    public String ispisKompletnePesme(){
        String rezultat = "";
        rezultat += "ID: " + id + ".\n" + naziv + "(" + trajanje + ")" + "\nIzvođač: " + izvodjac;
        if(album!=null)
            rezultat += "\nAlbum: " + album;
        return rezultat;
    }

    public static void rucniUnosNovePesme(Scanner unos){
        System.out.println("Kako biste uneli pesmu, unesite REDOM:" +
                " naziv pesme, ID izvođača, ID albuma i trajanje pesme.");

        int id_izvodjaca, id_albuma;
        String naziv, trajanje;

        System.out.print("Naziv pesme: "); naziv = unos.nextLine();
        System.out.print("ID izvođača: "); id_izvodjaca = unos.nextInt(); unos.nextLine();
        System.out.print("ID albuma (Pritisnite Enter ako nema): "); id_albuma = (unos.nextInt() > 0) ? unos.nextInt() : 0 ; unos.nextLine();
        System.out.print("Trajanje pesme (Format: HH:MM:SS): "); trajanje = unos.nextLine();

        unosPesme(naziv, trajanje, id_izvodjaca, id_albuma);
    }

    public void updatePesme(String tmpNaziv, int tmpIDIzvodjaca, int tmpIDAlbuma, String tmpTrajanje) {
        String upit = "update pesme set ";
        if(tmpNaziv.length()>=1) upit += "naziv = '" + tmpNaziv + "', ";
        if(tmpIDIzvodjaca!=0) upit += "id_izvodjaca = " + tmpIDIzvodjaca + ", ";
        if(tmpIDAlbuma!=0) upit += "id_albuma = " + tmpIDAlbuma + ", ";
        if(tmpTrajanje.length()>=1) upit += "trajanje = '" + tmpTrajanje + "'";
        upit += " where id = " + this.id;

        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspešno ažuriranje pesme!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePesme(int tmpId) {
        String upit = "delete * from pesme where id = " + tmpId;
        try {
            if(BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspešno izbrisana pesma!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Albumi getAlbumPesme() {
        return album;
    }

    public static Pesme filtrirajListuPesamaPoId(ArrayList<Pesme> pesme, int id) {
            Pesme rezultat = null;
            for(Pesme p : pesme){
                if(p.id == id) rezultat = p;
            }
            return rezultat;
    }
}