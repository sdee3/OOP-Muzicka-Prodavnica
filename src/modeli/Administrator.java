package modeli;

import baza.BazaPodataka;
import izuzeci.NepostojecaPesmaException;
import izuzeci.NepostojeciAlbumException;
import izuzeci.NepostojeciIzvodjacException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator extends Osoba {

    private static Scanner unos = new Scanner(System.in);

    private Administrator(String username, String password) {
        super(username, password);
    }

    public String getMeni() {
        return "\n1. Unos pesme\n2. Unos izvođača\n3. Unos albuma\n4. Ažuriranje pesme\n5. Ažuriranje izvođača" +
                "\n6. Ažuriranje albuma\n7. Brisanje pesme\n8. Brisanje izvođača\n9. Brisanje albuma\n10. Odjava\n";
    }

    public static Administrator adminPassCheck(String username) {
        String praviPassword = "", tmpPassword;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit("SELECT password FROM osoba WHERE admin = 1 AND username = '" + username + "'");
            praviPassword = odgovorBaze.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (getBrojac() < 3) {
            System.out.print("Lozinka: ");
            tmpPassword = new Scanner(System.in).nextLine();
            if (proveraUneteIPraveLozinke(praviPassword, tmpPassword))
                break;
            else inkrementirajBrojac();
        }
        return proveraBrojacaIKreiranjeAdmina(username, praviPassword);
    }

    private static Administrator proveraBrojacaIKreiranjeAdmina(String username, String password) {
        if (getBrojac() == 3) {
            System.err.println("Neispravno uneti podaci. Izlazak iz aplikacije...");
            System.exit(1);
            return null;
        } else return new Administrator(username, password);
    }

    public static void unosPesamaUAlbum(Albumi noviAlbum) {
        System.out.println("Unos pesama u album mozete prekinuti tako sto cete pritisnuti Enter kod naziva pesme.");
        String naziv, trajanje;
        String upit = "", drugiDeoUpita = "";

        while (true) {
            System.out.print("Naziv: ");
            naziv = unos.nextLine();
            if (naziv.equals(""))
                break;
            System.out.print("Trajanje: ");
            trajanje = unos.nextLine();
            drugiDeoUpita += "(" + Pesme.dohvatiNoviId() + ", '" + naziv + "', " + noviAlbum.getIzvodjac().getIzvodjacId()
                    + ", " + noviAlbum.getAlbumId() + ", '" + trajanje + "'), ";
        }

        if (!upit.equals(""))
            upit += "insert into pesme values ";

        try {
            if (BazaPodataka.getInstanca().iudUpit(upit+drugiDeoUpita) > 0)
                System.out.println("Uspesan unos svih pesama!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void brisanjeAlbuma() {
        System.out.print("Unesite ID albuma za brisanje:");
        int tmpId = unos.nextInt();
        unos.nextLine();
        try {
            if (Albumi.dohvatiAlbumPoId(tmpId) != null) {
                System.out.println("Da li ste sigurni da želite obrisati album? " +
                        "OVO ĆE IZBRISATI I IZVOĐAČA I SVE PESME IZDATE POD OVIM ALBUMOM!\nUnesite DA ili NE");
                if (unos.nextLine().equals("DA")) {
                    Albumi.deleteAlbum(tmpId);
                } else {
                    System.out.println("Povratak na meni...");
                }
            } else throw new NepostojeciAlbumException();
        } catch (NepostojeciAlbumException e) {
            e.printStackTrace();
        }
    }

    public static void brisanjeIzvodjaca() {
        System.out.print("Unesite ID izvođača za brisanje:");
        int tmpId = unos.nextInt();
        unos.nextLine();
        try {
            if (Izvodjaci.dohvatiIzvodjacaPoId(tmpId) != null) {
                System.out.println("Da li ste sigurni da želite obrisati izvođača? Unesite DA ili NE");
                if (unos.nextLine().equals("DA")) {
                    Izvodjaci.deleteIzvodjac(tmpId);
                } else {
                    System.out.println("Povratak na meni...");
                }
            } else throw new NepostojeciIzvodjacException();
        } catch (NepostojeciIzvodjacException e) {
            e.printStackTrace();
        }
    }

    public static void brisanjePesme() {
        System.out.print("Unesite ID pesme za brisanje:");
        int tmpId = unos.nextInt();
        unos.nextLine();
        try {
            if (Pesme.filtrirajListuPesamaPoId(tmpId) != null) {
                System.out.println("Da li ste sigurni da želite obrisati pesmu? Unesite DA ili NE");
                if (unos.nextLine().equals("DA")) {
                    Pesme.deletePesme(tmpId);
                } else {
                    System.out.println("Povratak na meni...");
                }
            } else throw new NepostojecaPesmaException();
        } catch (NepostojecaPesmaException e) {
            e.printStackTrace();
        }
    }

    public static void azuriranjeAlbuma() {
        String tmpNaziv, tmpZanr;
        int tmpGodina, tmpIDIzvodjaca;
        System.out.print("Unesite ID albuma: ");
        int tmpId = unos.nextInt();
        unos.nextLine();
        Albumi tmpAlbum = Albumi.dohvatiAlbumPoId(tmpId);
        if (tmpAlbum == null) {
            try {
                throw new NepostojeciAlbumException();
            } catch (NepostojeciAlbumException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\nUkoliko ne zelite promeniti neki od podataka, pritisnite Enter.\n" +
                    "Unesite novi naziv albuma: ");
            tmpNaziv = unos.nextLine();
            System.out.print("\nUnesite novu godinu izdanja albuma: ");
            tmpGodina = unos.nextInt();
            unos.nextLine();
            System.out.print("Unesite novi ID izvođača: ");
            tmpIDIzvodjaca = unos.nextInt();
            unos.nextLine();
            System.out.print("Unesite novi žanr: ");
            tmpZanr = unos.nextLine();
            tmpAlbum.updateAlbum(tmpNaziv, tmpGodina, tmpIDIzvodjaca, tmpZanr);
        }

    }

    public static void azuriranjeIzvodjaca() {
        String tmpImePrezime, tmpTip, tmpBiografija;
        int tmpGodinaFormiranja, tmpGodRaspada;
        System.out.print("Unesite ID izvođača: ");
        int tmpId = unos.nextInt();
        unos.nextLine();
        Izvodjaci tmpIzvodjac = Izvodjaci.vratiIzvodjacaPoId(tmpId);
        if (tmpIzvodjac == null) {
            try {
                throw new NepostojeciIzvodjacException();
            } catch (NepostojeciIzvodjacException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\nUkoliko ne zelite promeniti neki od podataka, pritisnite Enter.\n" +
                    "Unesite novo ime i prezime: ");
            tmpImePrezime = unos.nextLine();
            System.out.print("\nUnesite novi tip (SOLO, DUO, BEND): ");
            tmpTip = unos.nextLine();
            System.out.print("Unesite novu god. formiranja: ");
            tmpGodinaFormiranja = unos.nextInt();
            unos.nextLine();
            System.out.print("Unesite novu god. raspada: ");
            tmpGodRaspada = unos.nextInt();
            unos.nextLine();
            System.out.print("Unesite novu biografiju (do 500 karaktera): ");
            tmpBiografija = unos.nextLine();
            tmpIzvodjac.updateIzvodjac(tmpImePrezime, tmpTip, tmpGodinaFormiranja, tmpGodRaspada, tmpBiografija);
        }

    }

    public static void azuriranjePesme() {
        String tmpNaziv, tmpTrajanje;
        int tmpIDIzvodjaca, tmpIDAlbuma;
        System.out.print("Unesite ID pesme: ");
        int tmpId = unos.nextInt();
        unos.nextLine();
        Pesme tmpPesma = Pesme.filtrirajListuPesamaPoId(tmpId);
        if (tmpPesma == null) {
            try {
                throw new NepostojecaPesmaException();
            } catch (NepostojecaPesmaException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\nUkoliko ne zelite promeniti neki od podataka, pritisnite Enter.\n" +
                    "Unesite novi naziv pesme: ");
            tmpNaziv = unos.nextLine();
            System.out.print("\nUnesite novi ID izvođača: ");
            tmpIDIzvodjaca = unos.nextInt();
            unos.nextLine();
            System.out.print("Unesite novi ID albuma (može ostati prazno ukoliko je singl): ");
            tmpIDAlbuma = unos.nextInt();
            unos.nextLine();
            System.out.print("Unesite novo trajanje (HH:MM:SS): ");
            tmpTrajanje = unos.nextLine();
            tmpPesma.updatePesme(tmpNaziv, tmpIDIzvodjaca, tmpIDAlbuma, tmpTrajanje);
        }
    }

}