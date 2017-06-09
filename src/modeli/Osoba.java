package modeli;

import java.util.Scanner;

abstract class Osoba {

    private String username, password;

    Osoba(String username, String password){
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    abstract String unosLozinke(Scanner unos);
}

class Korisnik extends Osoba{

    Korisnik(String username, String password) {
        super(username, password);
    }

    @Override
    String unosLozinke(Scanner unos) {
        return unos.nextLine();
    }


    public String getMeni(){
        return "\n1. Prikaz biblioteke kupljenih albuma i pesama\n2. Pretraga izvođača u muzičkoj aplikaciji" +
                "\n3. Dodavanje pesme u biblioteku\n4. Dodavanje albuma u biblioteku\n5. Odjava\n";
    }

}

class Administrator extends Osoba{

    Administrator(String username, String password) {
        super(username, password);
    }

    @Override
    String unosLozinke(Scanner unos) {
        return unos.nextLine();
    }


    public String getMeni(){
        return "\n1. Unos pesme\n2. Unos izvođača\n3. Unos albuma\n4. Ažuriranje pesme\n5. Ažuriranje izvođača" +
                "\n6. Ažuriranje albuma\7. Brisanje pesme\n8. Brisanje izvođača\n9. Brisanje albuma\n10. Odjava\n";
    }

}