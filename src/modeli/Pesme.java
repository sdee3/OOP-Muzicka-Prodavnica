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

    public Pesme(String naziv, String trajanje, int izvodjac_id, int album_id) {
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.id = dohvatiNoviId();
        izvodjac = Izvodjaci.dohvatiIzvodjacaPoId(izvodjac_id);
        album = Albumi.dohvatiAlbumPoId(album_id);

        String upit = "insert into pesme values (" + this.id + ", '" + this.naziv + "', " + izvodjac.getIzvodjacId() +
                ", " + album.getAlbumId() + ", '" + this.trajanje + "')";

        try {
            int brPromena = BazaPodataka.getInstanca().iudUpit(upit);
            if (brPromena > 0) System.out.println("Uspesno dodavanje pesme u bazu!");
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

    private static void unosPesme(String naziv, String trajanje, int id_izvodjaca, int id_albuma) {
        String upit = (id_albuma != 0) ? "insert into pesme('naziv', 'id_izvodjaca', 'id_albuma', 'trajanje') values ('" + naziv
                + "', " + id_izvodjaca + ", " + id_albuma + ", '" + trajanje + "')"
                : "insert into pesme('naziv', 'id_izvodjaca', 'trajanje') values ('" + naziv
                + "', " + id_izvodjaca + ", '" + trajanje + "')";
        try {
            if (BazaPodataka.getInstanca().iudUpit(upit) != 0)
                System.out.println("Pesma '" + naziv + "' uspesno dodata!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Pesme dohvatiPesmuPoId(int id) {
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

    public static ArrayList<Pesme> dohvatiSvePesme() {
        ArrayList<Pesme> rezultat = new ArrayList<>();
        String upit = "select * from pesme";

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);

            while (odgovorBaze.next()) {
                rezultat.add(new Pesme(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                        odgovorBaze.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public static void rucniUnosNovePesme(Scanner unos) {
        System.out.println("Kako biste uneli pesmu, unesite REDOM:" +
                " naziv pesme, ID izvodjaca, ID albuma i trajanje pesme.");

        int id_izvodjaca, id_albuma = 0;
        String naziv, trajanje;

        System.out.print("Naziv pesme: ");
        naziv = unos.nextLine();
        System.out.print("ID izvodjaca: ");
        id_izvodjaca = unos.nextInt();
        unos.nextLine();
        System.out.print("ID albuma (Pritisnite Enter ako nema): ");
        String id_albuma_string = unos.nextLine();
        if (!id_albuma_string.equals(""))
            id_albuma = Integer.parseInt(id_albuma_string);
        System.out.print("Trajanje pesme (Format: HH:MM:SS): ");
        trajanje = unos.nextLine();

        unosPesme(naziv, trajanje, id_izvodjaca, id_albuma);
    }

    public void updatePesme(String tmpNaziv, int tmpIDIzvodjaca, int tmpIDAlbuma, String tmpTrajanje) {
        String upit = "";

        if (tmpNaziv.length() > 1 || tmpIDIzvodjaca != 0 || tmpIDAlbuma != 0 || tmpTrajanje.length() > 1)
            upit = "update pesme set ";

        if (tmpNaziv.length() > 1) upit += "naziv = '" + tmpNaziv + "'";

        if (tmpIDIzvodjaca != 0)
            if(tmpNaziv.length() > 1) {
                upit += ", id_izvodjaca = " + tmpIDIzvodjaca;
            }else{
                upit += "id_izvodjaca = " + tmpIDIzvodjaca;
            }

        if (tmpIDAlbuma != 0)
            if(tmpNaziv.length() > 1 || tmpIDIzvodjaca != 0) {
                upit += ", id_albuma = " + tmpIDAlbuma;
            }else{
                upit += "id_albuma = " + tmpIDAlbuma;
            }

        if (tmpTrajanje.length() > 1)
            if(tmpNaziv.length() > 1 || tmpIDIzvodjaca != 0 || tmpIDAlbuma != 0){
                upit += ", trajanje = '" + tmpTrajanje + "'";
            }else{
                upit += "trajanje = '" + tmpTrajanje + "'";
            }

        if (tmpNaziv.length() > 1 || tmpIDIzvodjaca != 0 || tmpIDAlbuma != 0 || tmpTrajanje.length() > 1) {
            upit += " where id = " + this.id;
            try {
                if (BazaPodataka.getInstanca().iudUpit(upit) > 0)
                    System.out.println("Uspesno azuriranje pesme!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void deletePesme(int tmpId) {
        String upit = "delete * from pesme where id = " + tmpId;
        try {
            if (BazaPodataka.getInstanca().iudUpit(upit) > 0)
                System.out.println("Uspesno izbrisana pesma!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pesme dohvatiPesmuPoId(ArrayList<Pesme> pesme, int id) {
        Pesme rezultat = null;
        for (Pesme p : pesme) {
            if (p.id == id) rezultat = p;
        }
        return rezultat;
    }

    public static ArrayList<Pesme> dohvatiPesmeAlbuma(Albumi album) {
        String upit = "select * from pesme where id_albuma not null and id_albuma = " + album.getAlbumId();
        ArrayList<Pesme> rezultat = new ArrayList<>();

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()) {
                rezultat.add(new Pesme(odgovorBaze.getInt(1), odgovorBaze.getString(2),
                        odgovorBaze.getInt(3), odgovorBaze.getInt(4),
                        odgovorBaze.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultat;
    }

    public Albumi getAlbumPesme() {
        return album;
    }

    @Override
    public String toString() {
        return id + ". " + naziv + " (" + trajanje + ")";
    }

    public String ispisKompletnePesme() {
        String rezultat = "";
        rezultat += "ID: " + id + ".\n" + naziv + "(" + trajanje + ")" + "\nIzvodjac: " + izvodjac;
        if (album != null)
            rezultat += "\nAlbum: " + album;
        return rezultat;
    }

    public int getId() {
        return id;
    }
}