package baza;

import java.sql.*;

public class BazaPodataka {
    private Connection conn;
    private static BazaPodataka instanca;

    private BazaPodataka() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:baza/prodavnica.db");
        } catch ( Exception e ) {
            System.err.println("Doslo je do greske pri konekciji na bazu podataka"
                    + e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static BazaPodataka getInstanca() {
        if(instanca == null)
            instanca = new BazaPodataka();
        return instanca;
    }

    public void automatskaTransakcija(boolean on_off) throws SQLException {
        conn.setAutoCommit(on_off);
    }

    public void snimiTransakciju() throws SQLException { conn.commit(); }

    //metoda za upite koji su tipa INSERT, UPDATE, DELETE
    public int iudUpit(String sql) throws SQLException {
        System.out.println(sql);
        Statement statement = conn.createStatement();
        return statement.executeUpdate(sql);
    }

    public ResultSet selectUpit(String sql) throws SQLException {
        System.out.println(sql);
        Statement statement= conn.createStatement();
        return statement.executeQuery(sql);
    }
}