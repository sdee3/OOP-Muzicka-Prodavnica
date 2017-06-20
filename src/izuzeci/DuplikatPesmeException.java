package izuzeci;

public class DuplikatPesmeException extends Exception{

    public DuplikatPesmeException() {
        System.err.println("Pesma je već uneta!");
    }
    public void printStackTrace(){
        System.err.println("Pesma koju ste pokušali uneti je već u Vašoj biblioteci.");
    }

}