package izuzeci;

public class NeispravanLoginException extends Exception{

    public NeispravanLoginException() {
        System.err.println("Neispravni podaci!");
    }
    public void printStackTrace(){
        System.err.println("Neki od podataka koji ste uneli je neispravan.");
    }

}