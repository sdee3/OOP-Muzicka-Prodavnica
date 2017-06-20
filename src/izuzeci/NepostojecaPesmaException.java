package izuzeci;

public class NepostojecaPesmaException extends Exception{

    public NepostojecaPesmaException() {
        System.err.println("Pesma ciji ste ID uneli ne postoji!");
    }
    public void printStackTrace(){
        System.err.println("Pesma ne postoji u bazi podataka.");
    }

}