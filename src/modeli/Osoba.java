package modeli;

import baza.BazaPodataka;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Osoba {

    private String username, password;
    private static int brojac = 0;

    public Osoba(String username, String password){
        this.username = username;
        this.password = password;
        brojac = 0;
    }

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    protected static int getBrojac() {return brojac;}

    protected static void inkrementirajBrojac(){ brojac++; }

    public abstract String getMeni();

    protected static boolean proveraBazeLogin(String username, String upit){
        boolean rezultat = false;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.getString(1).equals(username)) {
                System.out.println("Dobrodošli nazad!");
                rezultat = true;
            }else
                rezultat = false;
        } catch (SQLException e) { e.printStackTrace(); }
        return rezultat;
    }

}