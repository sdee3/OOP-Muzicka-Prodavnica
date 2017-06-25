package izuzeci;

public class DuplikatPesmeException extends Exception{

    public DuplikatPesmeException() {
        System.err.println("Pesma je vec uneta!");
    }
    public void printStackTrace(){
        System.err.println("Pesma koju ste pokusali uneti je vec u Vasoj biblioteci.");
    }

}