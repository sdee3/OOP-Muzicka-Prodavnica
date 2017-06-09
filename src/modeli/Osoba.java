package modeli;

public abstract class Osoba {

    private String username, password;
    private static int brojac;

    public Osoba(String username, String password){
        this.username = username;
        this.password = password;
        brojac = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static int getBrojac() {
        return brojac;
    }

    public static void inkrementirajBrojac(){ brojac++; }

    public abstract String getMeni();

}