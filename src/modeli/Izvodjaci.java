package modeli;

import baza.BazaPodataka;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    protected int getIdIzvodjaca() {
        return id;
    }

    protected String getImePrezimeIzvodjaca() {
        return ime_prezime;
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
}