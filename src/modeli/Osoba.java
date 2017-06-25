package modeli;

import izuzeci.NeispravanLoginException;

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

    protected static boolean proveraUneteIPraveLozinke(String praviPassword, String tmpPassword) throws NeispravanLoginException {
        boolean rezultat;
            if(tmpPassword.equals(praviPassword)) {
                System.out.println("Dobrodosli nazad!");
                rezultat = true;
            }else
                throw new NeispravanLoginException();
        return rezultat;
    }

}