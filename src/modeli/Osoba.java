package modeli;

import java.util.Scanner;

public abstract class Osoba {

    private String username, password;

    public Osoba(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String unosLozinke(Scanner unos);
}

