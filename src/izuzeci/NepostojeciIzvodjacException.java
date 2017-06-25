package izuzeci;

public class NepostojeciIzvodjacException extends Exception {

    public NepostojeciIzvodjacException(){}

    public NepostojeciIzvodjacException(String ime_prezime) {
        System.err.println("Izvodjac " + ime_prezime + " ne postoji!");
    }

    public void printStackTrace(){
        System.err.println("Izvodjac ne postoji u bazi podataka.");
    }
}