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
        return "\n1. Unos pesme\n2. Unos izvodjaca\n3. Unos albuma\n4. Azuriranje pesme\n5. Azuriranje izvodjaca" +
                "\n6. Azuriranje albuma\n7. Brisanje pesme\n8. Brisanje izvodjaca\n9. Brisanje albuma\n10. Odjava\n";
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

    public static void unosPesamaUNoviAlbum(Albumi noviAlbum) {
        System.out.println("Unos pesama u album mozete prekinuti tako sto cete pritisnuti Enter kod naziva pesme.");
        String naziv, trajanje, upit = "", drugiDeoUpita = "";
        int brojacUnetihPesama = 0;

        while (true) {
            System.out.print("Naziv: ");
            naziv = unos.nextLine();
            if (naziv.equals(""))
                break;
            System.out.print("Trajanje: ");
            trajanje = unos.nextLine();

            drugiDeoUpita += "(" + Pesme.dohvatiNoviId() + ", '" + naziv + "', " + noviAlbum.getIzvodjac().getIzvodjacId()
                    + ", " + noviAlbum.getAlbumId() + ", '" + trajanje + "')";

            if (!upit.equals(""))
                upit += "insert into pesme values ";

            try {
                if (BazaPodataka.getInstanca().iudUpit(upit+drugiDeoUpita) > 0)
                    brojacUnetihPesama++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(brojacUnetihPesama > 0) System.out.println("Uspesan unos svih pesama!");

    }

    public static void brisanjeAlbuma() {
        System.out.print("Unesite ID albuma za brisanje:");
        int tmpId = unos.nextInt();
        unos.nextLine();
        try {
            if (Albumi.dohvatiAlbumPoId(tmpId) != null) {
                System.err.println("Da li ste sigurni da zelite obrisati album? " +
                        "OVO cE IZBRISATI I IZVOdjAcA I SVE PESME IZDATE POD OVIM ALBUMOM!\nUnesite DA ili NE");
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
        System.out.print("Unesite ID izvodjaca za brisanje:");
        int tmpId = unos.nextInt();
        unos.nextLine();
        try {
            if (Izvodjaci.dohvatiIzvodjacaPoId(tmpId) != null) {
                System.out.println("Da li ste sigurni da zelite obrisati izvodjaca? Unesite DA ili NE");
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
            if (Pesme.dohvatiPesmuPoId(tmpId) != null) {
                System.out.println("Da li ste sigurni da zelite obrisati pesmu? Unesite DA ili NE");
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
        String tmpNaziv, tmpZanr, tmpGodina_string, tmpIDIzvodjaca_string;
        int tmpGodina = 0, tmpIDIzvodjaca = 0;

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
            tmpGodina_string = unos.nextLine();
            if(tmpGodina_string.length() > 0)
                tmpGodina = Integer.parseInt(tmpGodina_string);

            System.out.print("\nUnesite novi ID izvodjaca: ");
            tmpIDIzvodjaca_string = unos.nextLine();
            if(tmpIDIzvodjaca_string.length() > 0)
                tmpIDIzvodjaca = Integer.parseInt(tmpIDIzvodjaca_string);

            System.out.print("\nUnesite novi zanr: ");
            tmpZanr = unos.nextLine();

            tmpAlbum.updateAlbum(tmpNaziv, tmpGodina, tmpIDIzvodjaca, tmpZanr);
        }

    }

    public static void azuriranjeIzvodjaca() {
        String tmpImePrezime, tmpTip, tmpBiografija, tmpGodinaFormiranja_string, tmpGodRaspada_string;
        int tmpGodinaFormiranja = 0, tmpGodRaspada = 0;
        System.out.print("Unesite ID izvodjaca: ");
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

            System.out.print("\nUnesite novu god. formiranja: ");
            tmpGodinaFormiranja_string = unos.nextLine();
            if(tmpGodinaFormiranja_string.length() > 0)
                tmpGodinaFormiranja = Integer.parseInt(tmpGodinaFormiranja_string);

            System.out.print("\nUnesite novu god. raspada: ");
            tmpGodRaspada_string = unos.nextLine();
            if(tmpGodRaspada_string.length() > 0)
                tmpGodRaspada = Integer.parseInt(tmpGodRaspada_string);

            System.out.print("Unesite novu biografiju (do 500 karaktera): ");
            tmpBiografija = unos.nextLine();

            tmpIzvodjac.updateIzvodjac(tmpImePrezime, tmpTip, tmpGodinaFormiranja, tmpGodRaspada, tmpBiografija);
        }

    }

    public static void azuriranjePesme() {
        String tmpNaziv, tmpTrajanje, tmpIDIzvodjaca_string, tmpIDAlbuma_string;
        int tmpIDIzvodjaca = 0, tmpIDAlbuma = 0;
        System.out.print("Unesite ID pesme: ");
        int tmpId = unos.nextInt();
        unos.nextLine();
        Pesme tmpPesma = Pesme.dohvatiPesmuPoId(tmpId);
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

            System.out.print("\nUnesite novi ID izvodjaca: ");
            tmpIDIzvodjaca_string = unos.nextLine();
            if(tmpIDIzvodjaca_string.length() > 0)
                tmpIDIzvodjaca = Integer.parseInt(tmpIDIzvodjaca_string);

            System.out.print("\nUnesite novi ID albuma (moze ostati prazno ukoliko je singl): ");
            tmpIDAlbuma_string = unos.nextLine();
            if(tmpIDAlbuma_string.length() > 0)
                tmpIDAlbuma = Integer.parseInt(tmpIDAlbuma_string);

            System.out.print("\nUnesite novo trajanje (HH:MM:SS): ");
            tmpTrajanje = unos.nextLine();

            tmpPesma.updatePesme(tmpNaziv, tmpIDIzvodjaca, tmpIDAlbuma, tmpTrajanje);
        }
    }

}