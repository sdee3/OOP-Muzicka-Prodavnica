import helper.Log;
import modeli.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program {

    private static Osoba osoba;
    private static Scanner izbor = new Scanner(System.in);

    public static void main(String[] args) {
        login();
    }

    private static void prikaziMeni() {
        System.out.println(osoba.getMeni());

        int opcija;
        do{
            System.out.print("\nIzaberite neku od opcija unosom broja: ");
            while (!izbor.hasNextInt()) {
                System.err.println("Nekorektan unos! Morate uneti neku od ponuđenih opcija unosom broja!\n" +
                        "Pokušajte opet: ");
                izbor.next();
            }
            opcija = izbor.nextInt();
        }while (opcija < 1);

        prikazOpcije(opcija);
    }

    private static String vratiDatumIVreme() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    private static void login() {
        Log.init();

        System.out.print("\nDobrodošli u Čoko-PC-kord!\n\nOvo je Vaša muzička prodavnica!\n\nVaše korisničko ime: ");

        do{
            String username = izbor.nextLine();
            if(username.startsWith("a")){
                osoba = Administrator.adminPassCheck(username);
                Log.unesiTekst("Administrator " + osoba.getUsername() + " ulogovan/a na sistem: " + vratiDatumIVreme());
                prikaziMeni();
                break;
            }
            else if(username.startsWith("k")){
                osoba = Korisnik.korisnikPassCheck(username);
                Log.unesiTekst("Korisnik " + osoba.getUsername() + " ulogovan/a na sistem: " + vratiDatumIVreme());
                prikaziMeni();
                break;
            } else if(username.equals("exit") || username.equals("quit"))
                System.exit(0);
            else
                System.err.println("Neispravno korisničko ime! Korisničko ime mora početi malim slovom 'k' ili 'a'. Pokušajte ponovo.");

        }while(true);
    }

    private static void prikazOpcije(int izbor) {
        switch (izbor){
            case 1: //Unos pesme / Prikaz biblioteke
                if(osoba instanceof Administrator){
                    System.out.println("Kako biste uneli pesmu, unesite REDOM:" +
                            " naziv pesme, ID izvođača, ID albuma i trajanje pesme.");

                }else if(osoba instanceof Korisnik){

                }
                break;
            case 2: //Unos izvođača / Pretraga izvođača
                if(osoba instanceof Administrator){
                    System.out.println("Kako biste uneli izvođača, unesite REDOM: " +
                            "naziv benda ili ime i prezime, tip (SOLO, DUO, BEND), godinu formiranja, godinu raspada " +
                            "(može ostati prazno ukoliko je izvođač aktivan) i biografiju ne dužu od 8 rečenica.");

                }else if(osoba instanceof Korisnik){
                    System.out.print("Unesite izvođača radi pretrage: ");

                }
                break;
            case 3: //Unos albuma / Dodavanje pesme u bibl.
                if(osoba instanceof Administrator){
                    System.out.println("Kako biste uneli novi album, unesite REDOM: " +
                            "naziv albuma, ID izvođača albuma, žanr.\nNakon ovoga sledi unos pesama.");

                }else if(osoba instanceof Korisnik){

                }
                break;
            case 4: //Ažuriranje pesme / Dodavanje albuma u bibl.
                if(osoba instanceof Administrator){
                    System.out.print("Unesite ID pesme:");

                }else if(osoba instanceof Korisnik){

                }
                break;
            case 5: //Ažuriranje izvođača / Odjava
                if(osoba instanceof Administrator){
                    System.out.print("Unesite ID izvođača:");

                }else if(osoba instanceof Korisnik){
                    odjava();
                }
                break;
            case 6: //Ažuriranje albuma
                if(osoba instanceof Administrator){
                    System.out.print("Unesite ID albuma:");

                }else if(osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 7: //Brisanje pesme
                if(osoba instanceof Administrator){
                    System.out.print("Unesite ID pesme za brisanje:");

                }else if(osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 8: //Brisanje izvođača
                if(osoba instanceof Administrator){
                    System.out.print("Unesite ID izvođača za brisanje:");

                }else if(osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 9: //Brisanje albuma
                if(osoba instanceof Administrator){
                    System.out.print("Unesite ID albuma za brisanje:");

                }else if(osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                    prikaziMeni();
                }
                break;
            case 10:
                if(osoba instanceof Administrator){
                    odjava();
                }else if(osoba instanceof Korisnik) {
                    System.err.println("Neispravna opcija!");
                }
                break;
            default:
                System.err.println("Pogrešan izbor!");
                prikaziMeni();
                break;
        }
    }

    private static void odjava() {
        if(osoba instanceof Korisnik)
            Log.unesiTekst("Korisnik odjavljen - " + vratiDatumIVreme());
        else if(osoba instanceof  Administrator)
            Log.unesiTekst("Administrator odjavljen - " + vratiDatumIVreme());

        Log.zatvori();
        osoba = null;
        login();
    }

}