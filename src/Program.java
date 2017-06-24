import helper.Log;
import izuzeci.NepostojeciIzvodjacException;
import modeli.*;

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
                System.err.println("Nekorektan unos! Morate uneti neku od ponudjenih opcija unosom broja!\n" +
                        "Pokusajte opet: ");
                unos.next();
            }
            opcija = unos.nextInt();
            unos.nextLine();
        } while (opcija < 1);

        prikazOpcije(opcija);
    }

    private static void login() {
        Log.init();

        System.out.print("\nDobrodosli u coko-PC-kord!\n\nOvo je Vasa muzicka prodavnica!\n\nVase korisnicko ime: ");

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
                System.err.println("Neispravno korisnicko ime! Korisnicko ime mora poceti malim slovom 'k' ili 'a'. Pokusajte ponovo.");

        } while (true);
    }

    private static void prikazOpcije(int izbor) {
        switch (izbor) {
            case 1:
                if (osoba instanceof Administrator) {
                    Pesme.rucniUnosNovePesme(unos);
                    Log.unesiTekst("Administrator je uneo novu pesmu - " + vratiDatumIVreme());
                } else if (osoba instanceof Korisnik) {
                    Korisnik.prikaziBiblioteku(osoba);
                    Log.unesiTekst("Korisnik je prikazao sopstvenu biblioteku - " + vratiDatumIVreme());
                    prikaziMeni();
                }
                break;
            case 2:
                if (osoba instanceof Administrator) {
                    Izvodjaci.rucniUnosNovogIzvodjaca(unos);
                    Log.unesiTekst("Administrator je uneo novog izvodjaca u bazu - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    korisnikovaPretragaIzvodjaca();
                    Log.unesiTekst("Korisnik je izvrsio pretragu izvodjaca - " + vratiDatumIVreme());
                    prikaziMeni();
                }
                break;
            case 3:
                if (osoba instanceof Administrator) {
                    Albumi noviAlbum = Albumi.rucniUnosNovogAlbuma(unos);
                    Administrator.unosPesamaUAlbum(noviAlbum);
                    Log.unesiTekst("Administrator je uneo pesme u album - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    Korisnik.unosPesmeUBiblioteku(osoba);
                    Log.unesiTekst("Administrator je uneo pesme u svoju biblioteku - " + vratiDatumIVreme());
                    prikaziMeni();
                }
                break;
            case 4:
                if (osoba instanceof Administrator) {
                    Administrator.azuriranjePesme();
                    Log.unesiTekst("Administrator je izvrsio azuriranje pesme - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    Korisnik.unosAlbumaUBiblioteku(osoba);
                    Log.unesiTekst("Administrator je uneo album u svoju biblioteku - " + vratiDatumIVreme());
                    prikaziMeni();
                }
                break;
            case 5:
                if (osoba instanceof Administrator) {
                    Administrator.azuriranjeIzvodjaca();
                    Log.unesiTekst("Administrator je izvrsio azuriranje izvodjaca - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    odjava();
                }
                break;
            case 6:
                if (osoba instanceof Administrator) {
                    Administrator.azuriranjeAlbuma();
                    Log.unesiTekst("Administrator je izvrsio azuriranje albuma - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 7:
                if (osoba instanceof Administrator) {
                    Administrator.brisanjePesme();
                    Log.unesiTekst("Administrator je obrisao pesmu - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 8:
                if (osoba instanceof Administrator) {
                    Administrator.brisanjeIzvodjaca();
                    Log.unesiTekst("Administrator je obrisao izvodjaca - " + vratiDatumIVreme());
                    prikaziMeni();
                } else if (osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 9:
                if (osoba instanceof Administrator) {
                    Administrator.brisanjeAlbuma();
                    Log.unesiTekst("Administrator je obrisao album - " + vratiDatumIVreme());
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
                System.err.println("Pogresan unos!");
                prikaziMeni();
                break;
        }
    }

    private static void korisnikovaPretragaIzvodjaca() {
        Izvodjaci tmpIzvodjac;
        System.out.print("Unesite ime i prezime izvodjaca radi pretrage: ");
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
        System.out.print("\n1. Prikaz pesama i albuma\n2. Dodavanje pesme izvodjaca u biblioteku" +
                "\n3. Dodavanje albuma u biblioteku\n4. Nazad\n\nIzaberite neku od dodatnih opcija: ");
        int tmpIzbor = unos.nextInt();
        unos.nextLine();

        switch (tmpIzbor) {
            case 1:
                pesmeIAlbumiIzvodjaca(tmpIzvodjac);
                Log.unesiTekst("Korisnik je izvrsio pretragu albuma i pesama izvodjaca - " + vratiDatumIVreme());
                prikaziMeni();
                break;
            case 2:
                Korisnik.unosPesmeUBiblioteku(osoba);
                Log.unesiTekst("Korisnik je uneo pesmu u svoju biblioteku - " + vratiDatumIVreme());
                prikaziMeni();
                break;
            case 3:
                Korisnik.unosAlbumaUBiblioteku(osoba);
                Log.unesiTekst("Korisnik je uneo album u svoju biblioteku - " + vratiDatumIVreme());
                prikaziMeni();
                break;
            case 4:
                Log.unesiTekst("Korisnik se vratio na glavni meni - " + vratiDatumIVreme());
                prikaziMeni();
                break;
            default:
                System.err.println("Neispravan unos! Pokusajte opet.");
                opcija2Korisnik(tmpIzvodjac);
                break;
        }
    }

    private static void pesmeIAlbumiIzvodjaca(Izvodjaci tmpIzvodjac) {
        System.out.println("Pesme izvodjaca " + tmpIzvodjac.getImePrezime() + ":");
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