import helper.Log;
import izuzeci.NepostojecaPesmaException;
import izuzeci.NepostojeciAlbumException;
import izuzeci.NepostojeciIzvodjacException;
import modeli.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static helper.DatumVreme.vratiDatumIVreme;

public class Program {

    private static Osoba osoba;
    private static Scanner unos = new Scanner(System.in);

    public static void main(String[] args) {
        login();
    }

    private static void prikaziMeni() {
        System.out.println(osoba.getMeni());

        int opcija;
        do {
            System.out.print("\nIzaberite neku od opcija unosom broja: ");
            while (!unos.hasNextInt()) {
                System.err.println("Nekorektan unos! Morate uneti neku od ponuđenih opcija unosom broja!\n" +
                        "Pokušajte opet: ");
                unos.next();
            }
            opcija = unos.nextInt();
            unos.nextLine();
        } while (opcija < 1);

        prikazOpcije(opcija);
    }

    private static void login() { //TODO offline pamcenje lozinke pri proveri tacnosti
        Log.init();

        System.out.print("\nDobrodošli u Čoko-PC-kord!\n\nOvo je Vaša muzička prodavnica!\n\nVaše korisničko ime: ");

        do {
            String username = unos.nextLine();
            if (username.startsWith("a")) {
                osoba = Administrator.adminPassCheck(username);
                Log.unesiTekst("Administrator " + osoba.getUsername() + " ulogovan/a na sistem: " + vratiDatumIVreme());
                prikaziMeni();
                break;
            } else if (username.startsWith("k")) {
                osoba = Korisnik.korisnikPassCheck(username);
                Log.unesiTekst("Korisnik " + osoba.getUsername() + " ulogovan/a na sistem: " + vratiDatumIVreme());
                prikaziMeni();
                break;
            } else if (username.equals("exit") || username.equals("quit"))
                System.exit(0);
            else
                System.err.println("Neispravno korisničko ime! Korisničko ime mora početi malim slovom 'k' ili 'a'. Pokušajte ponovo.");

        } while (true);
    }

    private static void prikazOpcije(int izbor) {
        switch (izbor) {
            case 1:
                if (osoba instanceof Administrator) {
                    Pesme.rucniUnosNovePesme(unos);
                } else if (osoba instanceof Korisnik) {
                    Korisnik.prikaziBiblioteku(osoba);
                    prikaziMeni();
                }
                break;
            case 2:
                if (osoba instanceof Administrator) {
                    Izvodjaci.rucniUnosNovogIzvodjaca(unos);
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    korisnikovaPretragaIzvodjaca();
                    prikaziMeni();
                }
                break;
            case 3:
                if (osoba instanceof Administrator) {
                    Albumi noviAlbum = Albumi.rucniUnosNovogAlbuma(unos);
                    Administrator.unosPesamaUAlbum(noviAlbum); //TODO unos liste pesama za album
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    Korisnik.unosPesmeUBiblioteku(osoba);
                    prikaziMeni();
                }
                break;
            case 4:
                if (osoba instanceof Administrator) {
                    Administrator.azuriranjePesme();
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    Korisnik.unosAlbumaUBiblioteku(osoba);
                    prikaziMeni();
                }
                break;
            case 5:
                if (osoba instanceof Administrator) {
                    Administrator.azuriranjeIzvodjaca();
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    odjava();
                }
                break;
            case 6:
                if (osoba instanceof Administrator) {
                    Administrator.azuriranjeAlbuma();
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 7:
                if (osoba instanceof Administrator) {
                    Administrator.brisanjePesme();
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 8:
                if (osoba instanceof Administrator) {
                    Administrator.brisanjeIzvodjaca();
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 9:
                if (osoba instanceof Administrator) {
                    Administrator.brisanjeAlbuma();
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 10:
                if (osoba instanceof Administrator) {
                    odjava();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                }
                break;
            default:
                System.err.println("Pogrešan unos!");
                prikaziMeni();
                break;
        }
    }

    private static void korisnikovaPretragaIzvodjaca() {
        Izvodjaci tmpIzvodjac;
        System.out.print("Unesite ime i prezime izvođača radi pretrage: ");
        String ime_prezime = unos.nextLine();
        try {
            tmpIzvodjac = Izvodjaci.dohvatiIzvodjacaPoImenu(ime_prezime);
            System.out.println(tmpIzvodjac);
            opcija2Korisnik(tmpIzvodjac);
        } catch (NepostojeciIzvodjacException e) {
            e.printStackTrace();
            System.err.println("Povratak na meni...");
            prikaziMeni();
        }
    }



    private static void opcija2Korisnik(Izvodjaci tmpIzvodjac) {
        System.out.print("\n1. Prikaz pesama i albuma\n2. Dodavanje pesme izvođača u biblioteku" +
                "\n3. Dodavanje albuma u biblioteku\n4. Nazad\n\nIzaberite neku od dodatnih opcija: ");
        int tmpIzbor = unos.nextInt();
        unos.nextLine();

        switch (tmpIzbor) {
            case 1:
                pesmeIAlbumiIzvodjaca(tmpIzvodjac);
                prikaziMeni();
                break;
            case 2:
                Korisnik.unosPesmeUBiblioteku(osoba);
                prikaziMeni();
                break;
            case 3:
                Korisnik.unosAlbumaUBiblioteku(osoba);
                prikaziMeni();
                break;
            case 4:
                prikaziMeni();
                break;
            default:
                System.err.println("Neispravan unos! Pokušajte opet.");
                opcija2Korisnik(tmpIzvodjac);
                break;
        }
    }

    private static void pesmeIAlbumiIzvodjaca(Izvodjaci tmpIzvodjac) {
        System.out.println("Pesme izvodjača " + tmpIzvodjac.getImePrezime() + ":");
        for (Pesme p : Izvodjaci.dohvatiPesmeIzvodjaca(tmpIzvodjac.getImePrezime()))
            System.out.println(p);
        System.out.println("Albumi:");
        for (Albumi a : Izvodjaci.dohvatiAlbumeIzvodjaca(tmpIzvodjac.getImePrezime())) {
            System.out.println(a);
            System.out.println("Ukupno trajanje albuma: " + Albumi.dohvatiUkupnoTrajanje(a)); //TODO popravi trajanja
        }
    }

    private static void odjava() {
        if (osoba instanceof Korisnik)
            Log.unesiTekst("Korisnik odjavljen - " + vratiDatumIVreme());
        else if (osoba instanceof Administrator)
            Log.unesiTekst("Administrator odjavljen - " + vratiDatumIVreme());

        Log.zatvori();
        osoba = null;
        login();
    }

}